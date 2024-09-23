package woowoong.slacker.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import woowoong.slacker.dto.kakaopay.KakaoPayApproveResponse;
import woowoong.slacker.dto.kakaopay.KakaoPayCancelResponse;
import woowoong.slacker.dto.kakaopay.KakaoPayReadyResponse;
import woowoong.slacker.dto.kakaopay.PayRequest;
import woowoong.slacker.service.kakaopayService;

import java.io.IOException;

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

    @GetMapping("/success")
    public void afterPayRequest(@RequestParam("pg_token") String pgToken, HttpServletResponse response) throws IOException {
        // 결제 승인 처리
        kakaoPayService.ApproveResponse(pgToken);
        // success.html로 리다이렉트
        response.sendRedirect("/payment_success.html");
    }

    // 결제 취소
    @PostMapping("/cancel")
    public ResponseEntity<KakaoPayCancelResponse> cancel(@RequestBody PayRequest payRequest) {
        KakaoPayCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel(payRequest.getBookingId());
        return ResponseEntity.ok(kakaoCancelResponse);
    }
//    @PostMapping("/cancel")
//    public String cancelPayRequest(@RequestBody PayRequest payRequest, Model model) {
//        KakaoPayCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel(payRequest.getBookingId());
//
//        // 결제 승인 후 메시지 설정
//        model.addAttribute("message", "결제가 취소되었습니다.");
//        return "payment"; // payment_success.html로 이동
//    }

}
