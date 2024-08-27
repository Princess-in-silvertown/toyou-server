package slvtwn.khu.toyouserver.application;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.domain.Event;
import slvtwn.khu.toyouserver.dto.EventByDateResponse;
import slvtwn.khu.toyouserver.dto.EventResponse;
import slvtwn.khu.toyouserver.dto.EventsByYearMonthResponse;
import slvtwn.khu.toyouserver.persistance.EventRepository;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EventService {

    private static final int FIRST_DAY_OF_MONTH = 1;

    private final EventRepository eventRepository;

    public EventsByYearMonthResponse findEventsWithFilteringOptions(YearMonth yearMonth, LocalDate date) {
        if (yearMonth != null) {
            return findEventsByYearMonth(yearMonth);
        }
        return findEventsByDate(date);
    }

    private EventsByYearMonthResponse findEventsByYearMonth(YearMonth yearMonth) {
        LocalDate baseDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), FIRST_DAY_OF_MONTH);

        List<EventByDateResponse> eventByDateResponses = new ArrayList<>();
        Map<LocalDate, List<Event>> eventsCollectedByDate = eventRepository.findEventsByDateGreaterThanEqual(baseDate)
                .stream()
                .collect(Collectors.groupingBy(Event::getDate));

        convertCollectedEventsToEventResponses(eventByDateResponses, eventsCollectedByDate);
        return new EventsByYearMonthResponse(eventByDateResponses);
    }

    private EventsByYearMonthResponse findEventsByDate(LocalDate date) {
        List<EventResponse> eventResponses = eventRepository.findEventsByDate(date)
                .stream()
                .map(EventResponse::from)
                .toList();

        return new EventsByYearMonthResponse(
                List.of(new EventByDateResponse(date, eventResponses)));
    }

    private static void convertCollectedEventsToEventResponses(List<EventByDateResponse> eventByDateResponses, Map<LocalDate,
            List<Event>> eventsCollectedByDate) {
        for (Map.Entry<LocalDate, List<Event>> entry : eventsCollectedByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<EventResponse> eventResponses = entry.getValue().stream()
                    .map(EventResponse::from)
                    .toList();

            EventByDateResponse eventByDateResponse = new EventByDateResponse(date, eventResponses);
            eventByDateResponses.add(eventByDateResponse);
        }
    }
}
