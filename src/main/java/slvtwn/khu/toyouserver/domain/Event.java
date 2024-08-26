package slvtwn.khu.toyouserver.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate date;

    @Embedded
    private EventType eventType;

    private String description;

    private String profileImageUrl;

    public Event(String name, LocalDate date, EventType eventType, String description, String profileImageUrl) {
        this.name = name;
        this.date = date;
        this.eventType = eventType;
        this.description = description;
        this.profileImageUrl = profileImageUrl;
    }
}
