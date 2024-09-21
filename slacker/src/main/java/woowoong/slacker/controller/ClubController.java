package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowoong.slacker.domain.Club;
import woowoong.slacker.dto.Club.ClubDto;
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
    @GetMapping("/{id}")
    public ResponseEntity<Club> getClubById(@PathVariable Long id) {
        Optional<Club> club = clubService.getClubById(id);
        return club.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 공연장 등록하기
    @PostMapping("/register")
    public ResponseEntity<Club> registerClub(@RequestBody ClubDto clubDto) {
        Club club = clubService.registerClub(
                clubDto.getClubName(),
                clubDto.getLocation(),
                clubDto.getPhoneNumber(),
                clubDto.getWebsite(),
                clubDto.getNotice()
        );
        return ResponseEntity.ok(club);
    }
}