package slvtwn.khu.toyouserver.presentation;

import java.time.LocalDate;
import java.time.YearMonth;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.EventService;
import slvtwn.khu.toyouserver.dto.EventsByYearMonthResponse;

@RequiredArgsConstructor
@RestController
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    public EventsByYearMonthResponse findEventsWithDate(@RequestParam(required = false) YearMonth yearMonth,
                                   @RequestParam(required = false) LocalDate date) {
        return eventService.findEventsWithYearMonth(yearMonth);
    }
}
