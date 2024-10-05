package woowoong.slacker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woowoong.slacker.domain.BookingStatus;
import woowoong.slacker.domain.Live;
import woowoong.slacker.dto.Live.LiveResponse;
import woowoong.slacker.exception.LiveNotFoundException;
import woowoong.slacker.domain.Club;
import woowoong.slacker.repository.ClubRepository;
import woowoong.slacker.repository.LiveRepository;

import java.sql.Time;
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

    // 특정 공연장 공연 조회
    public List<LiveResponse> getLivesByClub(Long clubId) {
        // clubId로 공연을 조회
        List<Live> lives = liveRepository.findByClub_Id(clubId);

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
//    public LiveResponse registerLiveWithImage(MultipartFile imageFile, LiveResponse liveResponse) throws IOException {
//        // S3에 이미지 업로드
//        String imageUrl = s3Service.uploadFile(imageFile);
//
//        // 클럽 ID로 Club 객체 조회
//        Club club = clubRepository.findById(liveResponse.getClubId())
//                .orElseThrow(() -> new IllegalArgumentException("해당 클럽을 찾을 수 없습니다."));
//
//        // Live 엔티티 생성
//        Live live = new Live();
//        live.setTitle(liveResponse.getTitle());
//        live.setBandLineup(liveResponse.getBandLineup());
//        live.setDate(liveResponse.getDate());
//        live.setClubId(club);  // Club 객체 설정
//        live.setGenre(liveResponse.getGenre());
//        live.setAdvancePrice(liveResponse.getAdvancePrice());
//        live.setDoorPrice(liveResponse.getDoorPrice());
//        live.setNotice(liveResponse.getNotice());
//        live.setTimetable(liveResponse.getTimetable());
//        live.setImage(imageUrl);  // S3에 업로드된 이미지 URL
//        live.setStartTime(liveResponse.getStartTime());
//
//        Live registeredLive = liveRepository.save(live);// DB에 공연 저장
//
//        return new LiveResponse(registeredLive);
//    }

    public LiveResponse registerLiveWithImage(
            String imageUrl,
            String title,
            String bandLineup,
            String date,
            Long club_id,
            String genre,
            Integer advancePrice,
            Integer doorPrice,
            String notice,
            String timetable,
            Integer remainNumOfSeat,
            String start_time) {

        // 클럽 ID로 Club 객체 조회
        Club club = clubRepository.findById(club_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 클럽을 찾을 수 없습니다."));

        // Live 엔티티 생성
        Live live = new Live();
        live.setTitle(title);
        live.setBandLineup(bandLineup);
        live.setDate(LocalDate.parse(date));  // String을 LocalDate로 변환
        live.setClub(club);  // Club 객체 설정
        live.setGenre(genre);
        live.setAdvancePrice(advancePrice);
        live.setDoorPrice(doorPrice);
        live.setNotice(notice);
        live.setTimetable(timetable);
        live.setRemainNumOfSeats(remainNumOfSeat);
        live.setStartTime(Time.valueOf(start_time));
        live.setImage(imageUrl);  // 이미지 URL 설정

        System.out.println("공연 데이터" + live);

        // DB에 공연 저장
        //Live registeredLive = liveRepository.save(live);
        try {
            Live registeredLive = liveRepository.save(live); // DB에 공연 저장
            return new LiveResponse(registeredLive);
        } catch (Exception e) {
            // 예외가 발생할 경우 로그에 출력
            e.printStackTrace();
            throw new RuntimeException("DB 저장 중 문제가 발생했습니다.");
        }
    }

    // 공연 수정
    public LiveResponse updateLive(
            Long id,
            String imageUrl,
            String title,
            String bandLineup,
            String date,
            Long clubId,
            String genre,
            Integer advancePrice,
            Integer doorPrice,
            String notice,
            String timetable,
            Integer remainNumOfSeat,
            String startTime) {

        // 공연 ID로 기존 데이터를 조회
        Live live = liveRepository.findById(id)
                .orElseThrow(() -> new LiveNotFoundException("Live not found with ID: " + id));

        // 공연 정보를 업데이트
        live.setTitle(title);
        live.setBandLineup(bandLineup);
        live.setDate(LocalDate.parse(date));
        live.setGenre(genre);
        live.setAdvancePrice(advancePrice);
        live.setDoorPrice(doorPrice);
        live.setNotice(notice);
        live.setTimetable(timetable);
        live.setRemainNumOfSeats(remainNumOfSeat);
        live.setStartTime(Time.valueOf(startTime));
        live.setImage(imageUrl);

        // 클럽 정보 업데이트 (필요 시)
        if (clubId != null) {
            Club club = clubRepository.findById(clubId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 클럽을 찾을 수 없습니다."));
            live.setClub(club);
        }

        // 변경된 공연 정보를 저장
        Live updatedLive = liveRepository.save(live);
        return new LiveResponse(updatedLive);
    }

    // 공연 삭제
    public void deleteLive(Long id) {
        // 공연 존재 여부 확인
        Live live = liveRepository.findById(id)
                .orElseThrow(() -> new LiveNotFoundException("Live not found with ID: " + id));

        // 공연 삭제
        liveRepository.delete(live);
    }


}