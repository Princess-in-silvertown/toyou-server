package slvtwn.khu.toyouserver.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.dto.GroupResponse;
import slvtwn.khu.toyouserver.dto.UserProfileResponse;
import slvtwn.khu.toyouserver.dto.UserResponse;

@Component
@RequiredArgsConstructor
public class UserProfileFacade {

    private final UserService userService;
    private final GroupService groupService;

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(Long userId) {
        UserResponse userResponse = userService.getProfile(userId);
        List<GroupResponse> groupResponses = groupService.findGroups(userId, null).groups();

        return new UserProfileResponse(userResponse.id(), userResponse.birthday(), userResponse.name(),
                userResponse.introduction(), userResponse.imageUrl(), groupResponses);
    }
}
