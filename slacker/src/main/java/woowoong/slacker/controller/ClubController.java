package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowoong.slacker.domain.Club;
import woowoong.slacker.dto.Club.ClubDto;
import woowoong.slacker.dto.Club.ClubResponse;
import woowoong.slacker.dto.Club.UserClubResponse;
import woowoong.slacker.service.ClubService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    // 전체 공연장 조회
    @GetMapping
    public ResponseEntity<List<Club>> getAllClubs() {
        List<Club> clubs = clubService.getAllClubs();
        return ResponseEntity.ok(clubs);
    }

    // 특정 공연장 조회 (ID로 조회)
    @GetMapping("/{clubId}")
    public ResponseEntity<ClubResponse> getClubById(@PathVariable Long clubId) {
        ClubResponse clubFoundById = clubService.getClubById(clubId);
        return ResponseEntity.ok(clubFoundById);
    }

    @GetMapping("/user/{clubId}")
    public ResponseEntity<UserClubResponse> userGetClubById(@PathVariable Long clubId) {
        UserClubResponse clubFoundById = clubService.userGetClubById(clubId);
        return ResponseEntity.ok(clubFoundById);
    }

    @GetMapping("/id/{ownerId}")
    public ResponseEntity<Long> getClubIdByOwnerId(@PathVariable Long ownerId) {
        Long clubId = clubService.getClubIdByOwnerId(ownerId);
        return ResponseEntity.ok(clubId);
    }

    // 공연장 등록하기
    @PostMapping("/register")
    public ResponseEntity<ClubResponse> registerClub(@RequestBody ClubDto clubDto) {
        ClubResponse registeredClub = clubService.registerClub(clubDto);
        return ResponseEntity.ok(registeredClub);
    }

    @PostMapping("/update")
    public ResponseEntity<ClubResponse> updateClub(@RequestBody ClubResponse clubResponse) {
        ClubResponse registeredClub = clubService.updateClub(clubResponse);
        return ResponseEntity.ok(registeredClub);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteClub(@RequestBody Long clubId) {
        clubService.deleteClub(clubId);
        return ResponseEntity.ok("Success");
    }
}