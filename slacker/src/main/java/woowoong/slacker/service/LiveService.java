package woowoong.slacker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import woowoong.slacker.domain.Club;
import woowoong.slacker.domain.Live;
import woowoong.slacker.dto.Live.LiveDto;
import woowoong.slacker.dto.LiveDto;
import woowoong.slacker.repository.ClubRepository;
import woowoong.slacker.repository.LiveRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class LiveService {

    @Autowired
    private LiveRepository liveRepository;

    @Autowired
    private ClubRepository clubRepository;

    // 오늘 공연 조회
    public List<Live> getTodaysLives() {
        LocalDate today = LocalDate.now();
        return liveRepository.findByDate(today);
    }

    // 전체 공연 조회
    public List<Live> getAllLives() {
        return liveRepository.findAll();
    }

    // 공연 상세 조회
    public Live getLiveById(Long id) {
        return liveRepository.findById(id).orElseThrow(() -> new RuntimeException("공연을 찾을 수 없습니다."));
    }
    // 공연 등록
//    public Live registerLive(LiveDto liveDto) {
//        Live live = new Live(
//                liveDto.getTitle(),
//                liveDto.getBandLineup(),
//                liveDto.getDate(),
//                liveDto.getId(),
//                liveDto.getGenre(),
//                liveDto.getAdvancePrice(),
//                liveDto.getDoorPrice(),
//                liveDto.getNotice(),
//                liveDto.getTimetable(),
//                liveDto.getImage(),
//                liveDto.getStartTime()
//        );
//
//        return liveRepository.save(live);
//    }

    @Autowired
    private S3Service s3Service;

//    public Live registerLiveWithImage(MultipartFile imageFile, String title) throws IOException {
//        // S3에 이미지 업로드
//        String imageUrl = s3Service.uploadFile(imageFile);
//
//        // 엔티티 생성 후 이미지 URL과 타이틀 저장
//        Live live = new Live();
//        live.setTitle(title);
//        live.setImage(imageUrl);
//
//        return liveRepository.save(live);  // 데이터베이스에 저장
//    }



    // 이미지 포함 공연 등록
    public Live registerLiveWithImage(MultipartFile imageFile, LiveDto liveDto) throws IOException {
        // S3에 이미지 업로드
        String imageUrl = s3Service.uploadFile(imageFile);

        // 클럽 ID로 Club 객체 조회
        Club club = clubRepository.findById(liveDto.getClub_id())
                .orElseThrow(() -> new IllegalArgumentException("해당 클럽을 찾을 수 없습니다."));

        // Live 엔티티 생성
        Live live = new Live();
        live.setTitle(liveDto.getTitle());
        live.setBandLineup(liveDto.getBandLineup());
        live.setDate(liveDto.getDate());
        live.setClub_id(club);  // Club 객체 설정
        live.setGenre(liveDto.getGenre());
        live.setAdvancePrice(liveDto.getAdvancePrice());
        live.setDoorPrice(liveDto.getDoorPrice());
        live.setNotice(liveDto.getNotice());
        live.setTimetable(liveDto.getTimetable());
        live.setImage(imageUrl);  // S3에 업로드된 이미지 URL
        live.setStartTime(liveDto.getStartTime());

        return liveRepository.save(live);  // DB에 공연 저장
    }

}