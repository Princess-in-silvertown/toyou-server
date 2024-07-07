package slvtwn.khu.toyouserver.dto;

import java.time.LocalDate;

public record UserResponse(Long id, String name, LocalDate birthday, String imageUrl) {
}
