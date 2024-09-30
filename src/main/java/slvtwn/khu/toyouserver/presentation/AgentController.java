package slvtwn.khu.toyouserver.presentation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.AgentService;
import slvtwn.khu.toyouserver.common.response.ToyouResponse;
import slvtwn.khu.toyouserver.dto.GenerateStickerRequest;
import slvtwn.khu.toyouserver.dto.GenerateStickerResponse;
import slvtwn.khu.toyouserver.dto.KeywordRequest;
import slvtwn.khu.toyouserver.dto.KeywordResponse;

@AllArgsConstructor
@RestController
public class AgentController {

    private final AgentService agentService;

    @PostMapping("/generate-stickers")
    public ToyouResponse<GenerateStickerResponse> generateStickers(@RequestBody GenerateStickerRequest request) {
        GenerateStickerResponse response = agentService.generateStickers(request);
        return ToyouResponse.from(response);
    }

    @PostMapping("/generate-keywords")
    public ToyouResponse<KeywordResponse> generateKeywords(@RequestBody KeywordRequest request) {
        KeywordResponse response = agentService.generateKeywords(request);
        return ToyouResponse.from(response);
    }
}
