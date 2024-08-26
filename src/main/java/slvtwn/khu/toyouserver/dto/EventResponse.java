package slvtwn.khu.toyouserver.dto;

import slvtwn.khu.toyouserver.domain.Event;

public record EventResponse(Long id, String name, String eventType, String description, String profileImageUrl) {

    public static EventResponse from(Event event) {
        return new EventResponse(event.getId(), event.getName(), event.getEventType().name(),
                event.getDescription(), event.getProfileImageUrl());
    }
}
