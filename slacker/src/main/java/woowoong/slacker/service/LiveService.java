package woowoong.slacker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woowoong.slacker.domain.BookingStatus;
import woowoong.slacker.domain.Live;
import woowoong.slacker.dto.LiveResponse;
import woowoong.slacker.exception.LiveNotFoundException;
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
import java.util.stream.Collectors;

@Service
public class LiveService {

    @Autowired
    private LiveRepository liveRepository;


    public List<LiveResponse> getTodaysLives() {

    @Autowired
    private ClubRepository clubRepository;

    // 오늘 공연 조회
    public List<LiveResponse> getTodaysLives() {
        LocalDate today = LocalDate.now();
        List<Live> lives = liveRepository.findByDate(today);

        // Live -> LiveResponse 변환
        return lives.stream()
                .map(this::convertToLiveResponse)
                .collect(Collectors.toList());
    }

    // 변환 로직을 별도 메서드로 분리
    private LiveResponse convertToLiveResponse(Live live) {
        return new LiveResponse(live);
    }

    // 전체 공연 조회
    public List<LiveResponse> getAllLives() {
        List<Live> lives = liveRepository.findAll();

        // Live -> LiveResponse 변환
        return lives.stream()
                .map(this::convertToLiveResponse)
                .collect(Collectors.toList());
    }

    // 공연 상세 조회
    public LiveResponse getLiveById(Long id) {
        Live live = liveRepository.findById(id)
                .orElseThrow(() -> new LiveNotFoundException("Live not found with ID: " + id));
        return new LiveResponse(live);
    }

    public void changNumOfSeats(Long id, BookingStatus paymentState) {
        Live live = liveRepository.findById(id)
                .orElseThrow(() -> new LiveNotFoundException("Live not found with ID: " + id));

        int remainNumOfSeats = live.getRemainNumOfSeats();

        if(paymentState == BookingStatus.COMPLETED) {
            remainNumOfSeats = remainNumOfSeats - 1;
        } else if (paymentState == BookingStatus.CANCELED) {
            remainNumOfSeats = remainNumOfSeats + 1;
        }

        live.setRemainNumOfSeats(remainNumOfSeats);
        liveRepository.save(live);
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