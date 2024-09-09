//package woowoong.slacker.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import woowoong.slacker.domain.Live;
//import woowoong.slacker.repository.LiveRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//public class LiveService {
//
//    @Autowired
//    private LiveRepository liveRepository;
//
//    public List<Live> getTodaysConcerts() {
//        LocalDate today = LocalDate.now();
//        return liveRepository.findByDate(today);
//    }
//
//    public Concert getConcertById(Long id) {
//        return concertRepository.findById(id).orElseThrow(() -> new RuntimeException("공연을 찾을 수 없습니다."));
//    }
//}