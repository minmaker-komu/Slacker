package woowoong.slacker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woowoong.slacker.domain.BookingStatus;
import woowoong.slacker.domain.Live;
import woowoong.slacker.dto.Live.LiveResponse;
import woowoong.slacker.exception.LiveNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import woowoong.slacker.domain.Club;
import woowoong.slacker.repository.ClubRepository;
import woowoong.slacker.repository.LiveRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LiveService {
    private final LiveRepository liveRepository;
    private final ClubRepository clubRepository;
    private final S3Service s3Service;

    @Autowired
    public LiveService(LiveRepository liveRepository, ClubRepository clubRepository, S3Service s3Service) {
        this.liveRepository = liveRepository;
        this.clubRepository = clubRepository;
        this.s3Service = s3Service;
    }

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

    public void changeNumOfSeats(Long id, int numberOfTickets, BookingStatus paymentState) {
        Live live = liveRepository.findById(id)
                .orElseThrow(() -> new LiveNotFoundException("Live not found with ID: " + id));

        int remainNumOfSeats = live.getRemainNumOfSeats();

        if(paymentState == BookingStatus.COMPLETED) {
            remainNumOfSeats = remainNumOfSeats - numberOfTickets;
        } else if (paymentState == BookingStatus.CANCELED) {
            remainNumOfSeats = remainNumOfSeats + numberOfTickets;
        }

        live.setRemainNumOfSeats(remainNumOfSeats);
        liveRepository.save(live);
    }

    // 이미지 포함 공연 등록
    public LiveResponse registerLiveWithImage(MultipartFile imageFile, LiveResponse liveResponse) throws IOException {
        // S3에 이미지 업로드
        String imageUrl = s3Service.uploadFile(imageFile);

        // 클럽 ID로 Club 객체 조회
        Club club = clubRepository.findById(liveResponse.getClubId())
                .orElseThrow(() -> new IllegalArgumentException("해당 클럽을 찾을 수 없습니다."));

        // Live 엔티티 생성
        Live live = new Live();
        live.setTitle(liveResponse.getTitle());
        live.setBandLineup(liveResponse.getBandLineup());
        live.setDate(liveResponse.getDate());
        live.setClubId(club);  // Club 객체 설정
        live.setGenre(liveResponse.getGenre());
        live.setAdvancePrice(liveResponse.getAdvancePrice());
        live.setDoorPrice(liveResponse.getDoorPrice());
        live.setNotice(liveResponse.getNotice());
        live.setTimetable(liveResponse.getTimetable());
        live.setImage(imageUrl);  // S3에 업로드된 이미지 URL
        live.setStartTime(liveResponse.getStartTime());

        Live registeredLive = liveRepository.save(live);// DB에 공연 저장

        return new LiveResponse(registeredLive);
    }

}