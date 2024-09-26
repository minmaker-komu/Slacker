package woowoong.slacker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woowoong.slacker.domain.Club;
import woowoong.slacker.domain.Live;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LiveRepository extends JpaRepository<Live, Long> {
    List<Live> findByDate(LocalDate date);
//    List<Live> findByClub(Club club);
    //List<Live> findByClubId(Long clubId);
    public List<Live> findByClub_Id(Long clubId);
}
