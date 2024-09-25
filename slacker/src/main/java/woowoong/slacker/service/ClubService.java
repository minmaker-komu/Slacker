package woowoong.slacker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woowoong.slacker.domain.Club;
import woowoong.slacker.domain.Role;
import woowoong.slacker.domain.User;
import woowoong.slacker.dto.Club.ClubDto;
import woowoong.slacker.repository.ClubRepository;
import woowoong.slacker.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    // 전체 공연장 조회
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    // 특정 공연장 조회
    public Optional<Club> getClubById(Long id) {
        return clubRepository.findById(id);
    }


    // 공연장 등록
//    public ClubDto registerClub(String clubName, String location, String phoneNumber, String website, String notice, Long userId) {
//        ClubDto club = new ClubDto(clubName, location, phoneNumber, website, notice, userId);
//        return clubRepository.save(club);
//    }
    public Club registerClub(ClubDto clubDto) {
        // userId를 통해 사용자 조회
        Optional<User> userOptional = userRepository.findById(clubDto.getOwnerId());
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found with ID: " + clubDto.getOwnerId());
        }

        User user = userOptional.get();


        // DTO 데이터를 기반으로 Club 객체 생성
        Club club = new Club(clubDto.getClubName(), clubDto.getLocation(),
                clubDto.getPhoneNumber(), clubDto.getWebsite(),
                clubDto.getNotice(),user );

        return clubRepository.save(club);
    }


}
