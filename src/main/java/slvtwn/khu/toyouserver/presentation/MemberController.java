package slvtwn.khu.toyouserver.presentation;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.MemberService;
import slvtwn.khu.toyouserver.common.authentication.UserAuthentication;
import slvtwn.khu.toyouserver.common.response.ToyouResponse;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.MemberResponse;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public ToyouResponse<List<MemberResponse>> findMembersWithFilteringOptions(
            @UserAuthentication User user,
            @RequestParam String search, @RequestParam(required = false) Long groupId) {
        return ToyouResponse.from(memberService.findMembersWithFilteringOptions(user, search, groupId));
    }
}
