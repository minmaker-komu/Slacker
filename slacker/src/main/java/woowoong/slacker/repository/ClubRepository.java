package woowoong.slacker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import woowoong.slacker.domain.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
    // 기본적으로 JpaRepository에서 CRUD 메서드 제공
    // 필요한 경우 커스텀 쿼리 메서드를 추가할 수 있음
}