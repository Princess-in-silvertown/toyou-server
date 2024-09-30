package slvtwn.khu.toyouserver.agent.model;

import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import slvtwn.khu.toyouserver.agent.WebClientWrapper;

@RequiredArgsConstructor
@Component
public class StickerModelAgent {

    private final StickerModelConfiguration configuration;

    private final WebClientWrapper webClientWrapper;

    public List<byte[]> generateStickers(StickerModelRequest request) {
        StickerModelResponse response = webClientWrapper.send(
                configuration.getEndpoint(), (httpHeaders -> {
                }), request, StickerModelResponse.class
        );

        return response.base64EncodedImages().stream()
                .map(each -> Base64.getDecoder().decode(each))
                .toList();
    }
}
