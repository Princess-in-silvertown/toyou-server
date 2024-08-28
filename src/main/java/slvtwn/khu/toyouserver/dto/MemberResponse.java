package slvtwn.khu.toyouserver.dto;

import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.User;

public record MemberResponse(Long id, Long groupId, String name, String introduction, String imageUrl) {

    public static MemberResponse of(Member member) {
        User user = member.getUser();
        return new MemberResponse(member.getId(), member.getGroup().getId(), user.getName(),
                user.getIntroduction(), user.getProfilePicture());
    }
}
