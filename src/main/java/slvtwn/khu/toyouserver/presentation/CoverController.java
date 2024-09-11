package slvtwn.khu.toyouserver.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.CoverService;
import slvtwn.khu.toyouserver.common.response.ToyouResponse;
import slvtwn.khu.toyouserver.dto.CoverRequest;
import slvtwn.khu.toyouserver.dto.CoverResponse;

@RequiredArgsConstructor
@RestController
public class CoverController {

    private final CoverService coverService;

    @PostMapping("/covers")
    public ToyouResponse<CoverResponse> generateCoverImage(@RequestBody CoverRequest request) {
        CoverResponse response = coverService.generateCoverImage(request);
        return ToyouResponse.from(response);
    }
}
