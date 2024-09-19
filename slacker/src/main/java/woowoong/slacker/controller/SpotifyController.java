package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowoong.slacker.dto.spotify.RecommendedTrackResponse;
import woowoong.slacker.service.spotify.Recommendation;

import java.util.List;

@RestController
@RequestMapping("/spotify")
public class SpotifyController {
    private final Recommendation recommendation;

    // 생성자 주입 방식
    @Autowired
    public SpotifyController(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    // 아티스트 이름으로 추천곡 검색 엔드포인트
    @GetMapping("/recommend")
    public ResponseEntity<List<RecommendedTrackResponse>> getSongRecommendations(@RequestParam String artistName) {
        try {
            List<RecommendedTrackResponse> recommendedTracks = recommendation.getRecommendations(artistName);
            return ResponseEntity.ok(recommendedTracks);  // 성공 시 200 OK 응답과 함께 결과 반환
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // 예외 발생 시 500 서버 에러 반환
        }
    }
}
