package slvtwn.khu.toyouserver.persistance;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import slvtwn.khu.toyouserver.domain.Event;
import slvtwn.khu.toyouserver.domain.User;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findEventsByDateGreaterThanEqual(LocalDate date);
    List<Event> findEventsByDate(LocalDate date);

    void deleteAllByUserIs(User user);
}
