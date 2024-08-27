package slvtwn.khu.toyouserver.dto;

import java.util.List;

public record EventsByYearMonthResponse(List<EventByDateResponse> days) {
}
