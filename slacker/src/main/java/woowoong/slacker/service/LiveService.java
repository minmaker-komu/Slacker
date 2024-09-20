package woowoong.slacker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woowoong.slacker.domain.BookingStatus;
import woowoong.slacker.domain.Live;
import woowoong.slacker.dto.LiveResponse;
import woowoong.slacker.exception.LiveNotFoundException;
import woowoong.slacker.repository.LiveRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LiveService {

    @Autowired
    private LiveRepository liveRepository;

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

}