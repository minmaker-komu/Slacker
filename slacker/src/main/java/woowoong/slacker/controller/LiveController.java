package woowoong.slacker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import woowoong.slacker.domain.Live;
import woowoong.slacker.dto.Live.LiveDto;
import woowoong.slacker.service.LiveService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/lives")
public class LiveController{

    @Autowired
    private LiveService liveService;

    // 오늘 공연 조회
    @GetMapping("/today")
    public ResponseEntity<List<Live>> getTodaysLives() {
        List<Live> lives = liveService.getTodaysLives();
        return ResponseEntity.ok(lives);
    }

    // 전체 공연 조회
    @GetMapping("/all")
    public ResponseEntity<List<Live>> getAllLives() {
        List<Live> lives = liveService.getAllLives();
        return ResponseEntity.ok(lives);
    }

    // 공연 상세정보 보기
    @GetMapping("/{id}")
    public ResponseEntity<Live> getConcertDetails(@PathVariable Long id) {
        Live live = liveService.getLiveById(id);
        return ResponseEntity.ok(live);
    }

    // 공연 등록하기

    @PostMapping("/register")
    public ResponseEntity<Live> registerLiveWithImage(
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam("liveDto") String liveDtoJson) {
        try {
            // JSON 문자열을 LiveDto 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            LiveDto liveDto = objectMapper.readValue(liveDtoJson, LiveDto.class);

            // 서비스 호출하여 공연 등록
            Live live = liveService.registerLiveWithImage(imageFile, liveDto);
            return ResponseEntity.ok(live);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
