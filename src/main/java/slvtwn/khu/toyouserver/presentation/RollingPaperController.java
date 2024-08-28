package slvtwn.khu.toyouserver.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.RollingPaperService;
import slvtwn.khu.toyouserver.dto.RollingPaperRequest;

@RestController
@RequiredArgsConstructor
public class RollingPaperController {

    private final RollingPaperService rollingPaperService;

    @PostMapping("/groups/{groupId}/members/{memberId}/rollingpapers")
    public void sendRollingPaper(@PathVariable Long groupId, @PathVariable Long memberId,
                                 @RequestBody RollingPaperRequest rollingPaperRequest) {
        rollingPaperService.sendRollingPaper(rollingPaperRequest, groupId, memberId);
    }
}
