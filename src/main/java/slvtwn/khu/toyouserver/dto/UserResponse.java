package slvtwn.khu.toyouserver.dto;

import java.time.LocalDate;
import slvtwn.khu.toyouserver.domain.User;

public record UserResponse(Long id, String name, LocalDate birthday, String introduction, String imageUrl) {

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getBirthday(), user.getIntroduction(), user.getProfilePicture());
    }
}
