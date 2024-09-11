package slvtwn.khu.toyouserver.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.RollingPaperService;
import slvtwn.khu.toyouserver.common.authentication.UserAuthentication;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.CoverRequest;

@RequiredArgsConstructor
@RestController
public class RollingPaperController {

    private final RollingPaperService rollingPaperService;

    @PostMapping("/rollingpapers/{rollingPaperId}/generate-cover")
    public void generateCoverImage(@UserAuthentication User user,
                                   @PathVariable Long rollingPaperId, @RequestBody CoverRequest request) {
        rollingPaperService.generateCoverImageAndUpdateRollingPaper(request, rollingPaperId);
    }
}
