package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowoong.slacker.dto.kakaopay.KakaoPayApproveResponse;
import woowoong.slacker.dto.kakaopay.KakaoPayCancelResponse;
import woowoong.slacker.dto.kakaopay.KakaoPayReadyResponse;
import woowoong.slacker.dto.kakaopay.PayRequest;
import woowoong.slacker.service.kakaopayService;

@RestController
@RequestMapping("/payment")
public class KakaoPayController {
    private final kakaopayService kakaoPayService;
    @Autowired
    public KakaoPayController(kakaopayService kakaoPayService) {
        this.kakaoPayService = kakaoPayService;
    }

    // 결제요청
    @PostMapping("/ready")
    public ResponseEntity<KakaoPayReadyResponse> readyToKakaoPay(@RequestBody PayRequest payRequest) {
        System.out.println(payRequest);
        KakaoPayReadyResponse kakaoPayReadyResponse = kakaoPayService.kakaoPayReady(payRequest.getBookingId());
        return ResponseEntity.ok(kakaoPayReadyResponse);  // 성공 시 200 OK 응답과 함께 결과 반환
    }

    // 결제 성공
    @GetMapping("/success")
    public ResponseEntity<KakaoPayApproveResponse> afterPayRequest(@RequestParam("pg_token") String pgToken) {
        KakaoPayApproveResponse kakaoApprove = kakaoPayService.ApproveResponse(pgToken);
        return ResponseEntity.ok(kakaoApprove);
    }

    // 결제 취소
    @PostMapping("/cancel")
    public ResponseEntity<KakaoPayCancelResponse> cancel(@RequestBody PayRequest payRequest) {
        KakaoPayCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel(payRequest.getBookingId());
        return ResponseEntity.ok(kakaoCancelResponse);
    }

}
