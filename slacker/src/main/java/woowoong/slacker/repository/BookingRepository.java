package woowoong.slacker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woowoong.slacker.domain.Booking;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // 특정 유저의 예매 내역 조회
    List<Booking> findByUserId(Long userId);
}
