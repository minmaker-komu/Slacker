package woowoong.slacker.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowoong.slacker.domain.Club;
import woowoong.slacker.domain.Role;
import woowoong.slacker.domain.User;
import woowoong.slacker.dto.Club.ClubDto;
import woowoong.slacker.dto.Club.ClubResponse;
import woowoong.slacker.dto.Club.UserClubResponse;
import woowoong.slacker.exception.ClubNotFoundException;
import woowoong.slacker.exception.UserNotFoundException;
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
    public ClubResponse getClubById(Long id) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new ClubNotFoundException("Club not found with Id: " + id));
        return new ClubResponse(club);
    }


    // 공연장 등록
//    public ClubDto registerClub(String clubName, String location, String phoneNumber, String website, String notice, Long userId) {
//        ClubDto club = new ClubDto(clubName, location, phoneNumber, website, notice, userId);
//        return clubRepository.save(club);
//    }

    public ClubResponse registerClub(ClubDto clubDto) {

        Long ownerId = clubDto.getOwnerId();

        // userId를 통해 사장님 조회
        User user = userRepository.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException("User not found with Id: " + ownerId));

        // DTO 데이터를 기반으로 Club 객체 생성
        Club club = new Club(clubDto.getClubName(), clubDto.getLocation(),
                    clubDto.getPhoneNumber(), clubDto.getWebsite(),
                    clubDto.getNotice(), user);

        Club savedClub = clubRepository.save(club);

        return new ClubResponse(savedClub);
    }

    @Transactional
    public ClubResponse updateClub(ClubResponse clubResponse) {
        Long clubId = clubResponse.getId();

        // 기존 공연장 정보 업데이트
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ClubNotFoundException("Club not found with Id: " + clubId));

        club.setClubName(clubResponse.getClubName());
        club.setLocation(clubResponse.getLocation());
        club.setPhoneNumber(clubResponse.getPhoneNumber());
        club.setWebsite(clubResponse.getWebsite());
        club.setNotice(clubResponse.getNotice());

        return new ClubResponse(clubRepository.save(club));
    }

    @Transactional
    public void deleteClub(Long clubId) {
        if (clubRepository.existsById(clubId)) {
            clubRepository.deleteById(clubId);
        } else {
            throw new EntityNotFoundException("Club with id " + clubId + " not found");
        }
    }

    public UserClubResponse userGetClubById(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ClubNotFoundException("Club not found with Id: " + clubId));
        return new UserClubResponse(club);
    }

    public Long getClubIdByOwnerId(Long ownerId) {
        Club club = clubRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new ClubNotFoundException("Club not found with Id: " + ownerId));
        return club.getId();
    }
}
