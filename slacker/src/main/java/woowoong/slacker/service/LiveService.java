package woowoong.slacker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woowoong.slacker.domain.Live;
import woowoong.slacker.repository.LiveRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class LiveService {

    @Autowired
    private LiveRepository liveRepository;

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

}