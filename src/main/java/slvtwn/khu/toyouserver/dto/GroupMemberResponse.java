package slvtwn.khu.toyouserver.dto;

import java.time.LocalDate;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.User;

public record GroupMemberResponse(Long id, String name, LocalDate birthday, String introduction, String imageUrl) {

    public static GroupMemberResponse of(Member member) {
        User user = member.getUser();
        return new GroupMemberResponse(member.getId(), user.getName(), user.getBirthday(),
                user.getIntroduction(), user.getProfilePicture());
    }
}
