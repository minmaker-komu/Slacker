package woowoong.slacker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woowoong.slacker.domain.Club;
import woowoong.slacker.repository.ClubRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
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
    public Club registerClub(String clubName, String location, String phoneNumber, String website, String notice) {
        Club club = new Club(null, clubName, location, phoneNumber, website, notice);
        return clubRepository.save(club);
    }


}
