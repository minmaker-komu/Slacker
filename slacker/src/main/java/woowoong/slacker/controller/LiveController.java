package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowoong.slacker.domain.Live;
import woowoong.slacker.service.LiveService;

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
//    @PostMapping("/register")
//    public ResponseEntity<String> registerConcert(@RequestBody ConcertRequest concertRequest) {
//        liveService.registerlive(concertRequest);
//        return ResponseEntity.ok("공연 등록 완료");
//    }
}
