package slvtwn.khu.toyouserver.application;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.common.response.ToyouResponse;
import slvtwn.khu.toyouserver.domain.Event;
import slvtwn.khu.toyouserver.domain.EventType;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.EventByDateResponse;
import slvtwn.khu.toyouserver.dto.EventResponse;
import slvtwn.khu.toyouserver.dto.EventsByYearMonthResponse;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
@SpringBootTest
class EventServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EventService eventService;

    @Test
    void 연월로_이벤트를_조회할_수_있다() {
        // given
        LocalDate baseDate = LocalDate.of(2024, 9, 15);
        User user1 = new User("name", baseDate, "introduction", "picture_url");
        User user2 = new User("name", baseDate.minus(1, ChronoUnit.DAYS), "introduction", "picture_url");
        Event event1 = new Event("name", baseDate, EventType.BIRTHDAY, "description", user1);
        Event event2 = new Event("name", baseDate.minus(1, ChronoUnit.DAYS), EventType.BIRTHDAY, "description", user2);

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(event1);
        entityManager.persist(event2);

        // when
        YearMonth yearMonth = YearMonth.of(2024, 9);
        ToyouResponse<EventsByYearMonthResponse> response = eventService.findEventsWithFilteringOptions(yearMonth, null);

        // then
        assertThat(response.data().days())
                .containsExactlyInAnyOrder(
                        new EventByDateResponse(baseDate, List.of(EventResponse.from(event1))),
                        new EventByDateResponse(baseDate.minus(1, ChronoUnit.DAYS), List.of(EventResponse.from(event2))))
                .hasSize(2);
    }

    @Test
    void 같은년도에_해당하지_않는_이벤트는_조회되지_않는다() {
        // given
        LocalDate baseDate = LocalDate.of(2024, 9, 15);
        LocalDate beforeDate = LocalDate.of(2023, 9, 15);
        User user1 = new User("name", baseDate, "introduction", "picture_url");
        User user2 = new User("name", beforeDate, "introduction", "picture_url");
        Event event1 = new Event("name", baseDate, EventType.BIRTHDAY, "description", user1);
        Event event2 = new Event("name", beforeDate, EventType.BIRTHDAY, "description", user2);

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(event1);
        entityManager.persist(event2);

        // when
        YearMonth yearMonth = YearMonth.of(2024, 9);
        ToyouResponse<EventsByYearMonthResponse> response = eventService.findEventsWithFilteringOptions(yearMonth, null);

        // then
        assertThat(response.data().days())
                .containsExactly(
                        new EventByDateResponse(baseDate, List.of(EventResponse.from(event1))))
                .hasSize(1);
    }

    @Test
    void 같은달에_해당하지_않는_이벤트는_조회되지_않는다() {
        // given
        LocalDate baseDate = LocalDate.of(2024, 9, 15);
        LocalDate beforeDate = LocalDate.of(2024, 8, 15);
        User user1 = new User("name", baseDate, "introduction", "picture_url");
        User user2 = new User("name", beforeDate, "introduction", "picture_url");
        Event event1 = new Event("name", baseDate, EventType.BIRTHDAY, "description", user1);
        Event event2 = new Event("name", beforeDate, EventType.BIRTHDAY, "description", user2);

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(event1);
        entityManager.persist(event2);

        // when
        YearMonth yearMonth = YearMonth.of(2024, 9);
        ToyouResponse<EventsByYearMonthResponse> response = eventService.findEventsWithFilteringOptions(yearMonth, null);

        // then
        assertThat(response.data().days())
                .containsExactly(
                        new EventByDateResponse(baseDate, List.of(EventResponse.from(event1))))
                .hasSize(1);
    }

    @Test
    void 특정_날짜로_이벤트를_조회할_수_있다() {
        // given
        LocalDate baseDate = LocalDate.of(2024, 9, 15);
        LocalDate beforeDate = LocalDate.of(2024, 8, 15);
        User user1 = new User("name", baseDate, "introduction", "picture_url");
        User user2 = new User("name", beforeDate, "introduction", "picture_url");
        Event event1 = new Event("name", baseDate, EventType.BIRTHDAY, "description", user1);
        Event event2 = new Event("name", beforeDate, EventType.BIRTHDAY, "description", user2);

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(event1);
        entityManager.persist(event2);

        // when
        ToyouResponse<EventsByYearMonthResponse> response = eventService.findEventsWithFilteringOptions(null, baseDate);

        // then
        assertThat(response.data().days())
                .containsExactly(
                        new EventByDateResponse(baseDate, List.of(EventResponse.from(event1))))
                .hasSize(1);
    }
}
