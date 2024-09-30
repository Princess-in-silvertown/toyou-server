package slvtwn.khu.toyouserver.dto;

import java.time.LocalDate;
import java.util.List;
import slvtwn.khu.toyouserver.domain.User;

public record UserResponse(Long id, String name, LocalDate birthday, String introduction, String imageUrl,
                           Long groupId) {

    public static UserResponse of(User user) {
        return of(user, null);
    }

    public static UserResponse of(User user, Long groupId) {
        return new UserResponse(user.getId(), user.getName(), user.getBirthday(), user.getIntroduction(),
                user.getProfilePicture(), groupId);
    }
}
