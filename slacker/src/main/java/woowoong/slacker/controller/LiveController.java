package woowoong.slacker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import woowoong.slacker.dto.Live.LiveResponse;
import woowoong.slacker.service.LiveService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/lives")
public class LiveController{
    private final LiveService liveService;
    @Autowired
    public LiveController(LiveService liveService) {
        this.liveService = liveService;
    }

    // 오늘 공연 조회
    @GetMapping("/today")
    public ResponseEntity<List<LiveResponse>> getTodaysLives() {
        List<LiveResponse> lives = liveService.getTodaysLives();
        System.out.println(lives);
        return ResponseEntity.ok(lives);
    }

    // 전체 공연 조회
    @GetMapping("/all")
    public ResponseEntity<List<LiveResponse>> getAllLives() {
        List<LiveResponse> lives = liveService.getAllLives();
        return ResponseEntity.ok(lives);
    }

    // 공연 상세정보 보기
    @GetMapping("/{id}")
    public ResponseEntity<LiveResponse> getConcertDetails(@PathVariable Long id) {
        LiveResponse live = liveService.getLiveById(id);
        return ResponseEntity.ok(live);
    }

    // 공연 등록하기

    @PostMapping("/register")
    public ResponseEntity<LiveResponse> registerLiveWithImage(
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam("liveDto") String liveDtoJson) {
        try {
            // JSON 문자열을 LiveDto 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            LiveResponse liveResponse = objectMapper.readValue(liveDtoJson, LiveResponse.class);

            // 서비스 호출하여 공연 등록
            LiveResponse live = liveService.registerLiveWithImage(imageFile, liveResponse);
            return ResponseEntity.ok(live);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }


}
