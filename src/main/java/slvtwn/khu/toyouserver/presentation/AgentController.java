package slvtwn.khu.toyouserver.presentation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.AgentService;
import slvtwn.khu.toyouserver.common.response.ToyouResponse;
import slvtwn.khu.toyouserver.dto.KeywordRequest;
import slvtwn.khu.toyouserver.dto.KeywordResponse;

@AllArgsConstructor
@RestController
public class AgentController {

    private final AgentService agentService;

    @PostMapping("/generate-keywords")
    public ToyouResponse<KeywordResponse> generateKeywords(@RequestBody KeywordRequest keywordRequest) {
        KeywordResponse response = agentService.generateKeywords(keywordRequest);
        return ToyouResponse.from(response);
    }
}
