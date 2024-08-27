package slvtwn.khu.toyouserver.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import slvtwn.khu.toyouserver.domain.Event;

public record EventByDateResponse(LocalDate date, List<EventResponse> events) {

}
