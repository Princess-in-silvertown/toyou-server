package slvtwn.khu.toyouserver.persistance;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import slvtwn.khu.toyouserver.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findEventsByDateEqualsOrDateAfter(LocalDate date);
    List<Event> findEventsByDate(LocalDate date);
}
