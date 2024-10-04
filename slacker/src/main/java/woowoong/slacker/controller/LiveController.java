package woowoong.slacker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import woowoong.slacker.dto.Live.LiveResponse;
import woowoong.slacker.service.LiveService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
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

    // 특정 클럽의 공연 조회
    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<LiveResponse>> getLivesByClub(@PathVariable Long clubId) {
        List<LiveResponse> lives = liveService.getLivesByClub(clubId);
        return ResponseEntity.ok(lives);
    }

    // 공연 등록하기

//    @PostMapping("/register")
//    public ResponseEntity<LiveResponse> registerLiveWithImage(
//            @RequestParam("image") MultipartFile imageFile,
//            @RequestParam("liveDto") String liveDtoJson) {
//        try {
//            // JSON 문자열을 LiveDto 객체로 변환
//            ObjectMapper objectMapper = new ObjectMapper();
//            LiveResponse liveResponse = objectMapper.readValue(liveDtoJson, LiveResponse.class);
//
//            // 서비스 호출하여 공연 등록
//            LiveResponse live = liveService.registerLiveWithImage(imageFile, liveResponse);
//            return ResponseEntity.ok(live);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body(null);
//        }
//    }
    @PostMapping("/register")
    public ResponseEntity<LiveResponse> registerLiveWithImage(
            @RequestParam("image") String imageUrl,
            @RequestParam("title") String title,
            @RequestParam("bandLineup") String bandLineup,
            @RequestParam("date") String date,
            @RequestParam("club_id") Long clubId,
            @RequestParam("genre") String genre,
            @RequestParam("advancePrice") Integer advancePrice,
            @RequestParam("doorPrice") Integer doorPrice,
            @RequestParam("notice") String notice,
            @RequestParam("timetable") String timetable,
            @RequestParam("remain_num_of_seat") Integer remainNumOfSeat,
            @RequestParam("start_time") String startTime) {
        try {
            // 클럽 ID 확인
            System.out.println("clubId: " + clubId);

            // 서비스 호출하여 공연 등록
            LiveResponse live = liveService.registerLiveWithImage(
                    imageUrl, title, bandLineup, date, clubId, genre, advancePrice, doorPrice, notice, timetable, remainNumOfSeat, startTime);
            return ResponseEntity.ok(live);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // 공연 수정하기
    @PutMapping("/{id}")
    public ResponseEntity<LiveResponse> updateLive(
            @PathVariable Long id,
            @RequestParam("image") String imageUrl,
            @RequestParam("title") String title,
            @RequestParam("bandLineup") String bandLineup,
            @RequestParam("date") String date,
            @RequestParam("club_id") Long clubId,
            @RequestParam("genre") String genre,
            @RequestParam("advancePrice") Integer advancePrice,
            @RequestParam("doorPrice") Integer doorPrice,
            @RequestParam("notice") String notice,
            @RequestParam("timetable") String timetable,
            @RequestParam("remain_num_of_seat") Integer remainNumOfSeat,
            @RequestParam("start_time") String startTime) {
        try {
            // 공연 업데이트 서비스 호출
            LiveResponse updatedLive = liveService.updateLive(
                    id, imageUrl, title, bandLineup, date, clubId, genre, advancePrice, doorPrice, notice, timetable, remainNumOfSeat, startTime);
            return ResponseEntity.ok(updatedLive);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // 공연 삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLive(@PathVariable Long id) {
        try {
            liveService.deleteLive(id);
            return ResponseEntity.noContent().build();  // 삭제 후 성공 시 204 No Content
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();  // 오류 발생 시 500 Internal Server Error
        }
    }



}
