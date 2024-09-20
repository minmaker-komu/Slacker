package woowoong.slacker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import woowoong.slacker.domain.Booking;
import woowoong.slacker.domain.BookingStatus;
import woowoong.slacker.dto.booking.BookingResponse;
import woowoong.slacker.dto.booking.UpdateBookingStatusRequest;
import woowoong.slacker.dto.kakaopay.KakaoPayApproveResponse;
import woowoong.slacker.dto.kakaopay.KakaoPayCancelResponse;
import woowoong.slacker.dto.kakaopay.KakaoPayReadyResponse;
import woowoong.slacker.exception.BookingNotFoundException;
import woowoong.slacker.repository.BookingRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class kakaopayService {

    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final LiveService liveService;
    @Autowired
    public kakaopayService(BookingRepository bookingRepository, BookingService bookingService, LiveService liveService) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        this.liveService = liveService;
    }
    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    static final String secretKey = "SECRET_KEY DEV3D20D79213FEAF1D762486EC6D6F90E273D45";
    private KakaoPayReadyResponse kakaoReady;
    private BookingResponse bookingResponse;

    public KakaoPayReadyResponse kakaoPayReady(Long bookingId) {

        HttpEntity<Map<String, String>> requestEntity = getRequestEntity(bookingId);

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";

        // RestTemplate의 postForEntity : POST 요청을 보내고 ResponseEntity로 결과를 반환받는 메소드
        ResponseEntity<KakaoPayReadyResponse> responseEntity = restTemplate
                .postForEntity(url, requestEntity, KakaoPayReadyResponse.class);

        kakaoReady = responseEntity.getBody();

        return kakaoReady;
    }

    private HttpEntity<Map<String, String>> getRequestEntity(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));

        bookingResponse = new BookingResponse(booking);

        Long UserId = booking.getUser().getId();
        String itemName = booking.getLive().getTitle();
        int numberOfTickets = booking.getNumberOfTickets();
        int totalAmount = booking.getTotalAmount();

        // 카카오페이 요청 양식
        Map<String, String> parameters = new HashMap<>();

        parameters.put("cid", cid);
        parameters.put("partner_order_id", String.valueOf(bookingId));
        parameters.put("partner_user_id", String.valueOf(UserId));
        parameters.put("item_name", itemName);
        parameters.put("quantity", String.valueOf(numberOfTickets));
        parameters.put("total_amount", String.valueOf(totalAmount));
        parameters.put("tax_free_amount", "0");
        parameters.put("approval_url", "http://localhost:8080/payment/success"); // 성공 시 redirect url
        parameters.put("cancel_url", "http://localhost:8080/payment/cancel"); // 취소 시 redirect url
        parameters.put("fail_url", "http://localhost:8080/payment/fail"); // 실패 시 redirect url

        // HttpEntity : HTTP 요청 또는 응답에 해당하는 Http Header와 Http Body를 포함하는 클래스
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        return requestEntity;
    }

    // 결제 완료 승인
    public KakaoPayApproveResponse ApproveResponse(String pgToken) {

        Long bookingId = bookingResponse.getId();
        Long UserId = bookingResponse.getUserId();
        Long LiveId = bookingResponse.getLiveId();

        // 카카오 요청 양식
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("tid", kakaoReady.getTid());
        parameters.put("partner_order_id", String.valueOf(bookingId));
        parameters.put("partner_user_id", String.valueOf(UserId));
        parameters.put("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate restTemplate = new RestTemplate();

        // 외부에 보낼 url
        String url = "https://open-api.kakaopay.com/online/v1/payment/approve";

        KakaoPayApproveResponse approveResponse = restTemplate
                .postForObject(url, requestEntity, KakaoPayApproveResponse.class);

        bookingResponse = updateCompletedState(bookingId);
        liveService.changNumOfSeats(LiveId, BookingStatus.COMPLETED);

        return approveResponse;
    }

    private BookingResponse updateCompletedState(Long bookingId) {
        UpdateBookingStatusRequest updateBookingStatusRequest = new UpdateBookingStatusRequest();
        updateBookingStatusRequest.setBookingId(bookingId);
        updateBookingStatusRequest.setStatus(BookingStatus.COMPLETED);
        return bookingService.updateOrderStatus(updateBookingStatusRequest);
    }

    /**
     * 결제 환불
     */
    public KakaoPayCancelResponse kakaoCancel(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));

        // 카카오페이 요청
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("tid", kakaoReady.getTid());
        parameters.put("cancel_amount", String.valueOf(booking.getTotalAmount()));
        parameters.put("cancel_tax_free_amount", "0");

        // 파라미터, 헤더
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate restTemplate = new RestTemplate();

        // 외부에 보낼 url
        String url = "https://open-api.kakaopay.com/online/v1/payment/cancel";

        KakaoPayCancelResponse cancelResponse = restTemplate.postForObject(
                url,
                requestEntity,
                KakaoPayCancelResponse.class);

        updateCanceledState(bookingId);
        liveService.changNumOfSeats(booking.getLive().getId(), BookingStatus.CANCELED);

        return cancelResponse;
    }

    private BookingResponse updateCanceledState(Long bookingId) {
        UpdateBookingStatusRequest updateBookingStatusRequest = new UpdateBookingStatusRequest();
        updateBookingStatusRequest.setBookingId(bookingId);
        updateBookingStatusRequest.setStatus(BookingStatus.CANCELED);
        return bookingService.updateOrderStatus(updateBookingStatusRequest);
    }

    // 카카오페이 측에 요청 시 헤더부에 필요한 값
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", secretKey);
        headers.set("Content-type", "application/json");
        return headers;
    }
}

