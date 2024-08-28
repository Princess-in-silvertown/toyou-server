package slvtwn.khu.toyouserver.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.RollingPaperService;
import slvtwn.khu.toyouserver.common.response.ToyouResponse;
import slvtwn.khu.toyouserver.dto.RollingPaperRequest;
import slvtwn.khu.toyouserver.dto.RollingPaperResponse;

@RestController
@RequiredArgsConstructor
public class RollingPaperController {

    private final RollingPaperService rollingPaperService;

    @PostMapping("/groups/{groupId}/members/{memberId}/rollingpapers")
    public void sendRollingPaper(@PathVariable Long groupId, @PathVariable Long memberId,
                                 @RequestBody RollingPaperRequest rollingPaperRequest) {
        rollingPaperService.sendRollingPaper(rollingPaperRequest, groupId, memberId);
    }

    @GetMapping("/groups/{groupId}/members/{memberId}/rollingpapers/{rollingPaperId}")
    public ToyouResponse<RollingPaperResponse> getRollingPaper(@PathVariable Long groupId, @PathVariable Long memberId,
                                                               @PathVariable Long rollingPaperId) {
        return ToyouResponse.from(rollingPaperService.getRollingPaper(groupId, memberId, rollingPaperId));
    }

}
