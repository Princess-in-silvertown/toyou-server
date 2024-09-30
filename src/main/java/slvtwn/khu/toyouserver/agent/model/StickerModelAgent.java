package slvtwn.khu.toyouserver.agent.model;

import java.util.Base64;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import slvtwn.khu.toyouserver.agent.WebClientWrapper;
import slvtwn.khu.toyouserver.agent.aws.S3Agent;

@RequiredArgsConstructor
@Component
public class StickerModelAgent {

    private static final String STICKER_MIME_TYPE = "image/png";

    private final StickerModelConfiguration configuration;

    private final WebClientWrapper webClientWrapper;
    private final S3Agent s3Agent;

    public List<String> generateStickerUrls(StickerModelRequest request) {
        StickerModelResponse response = webClientWrapper.send(
                configuration.getEndpoint(), (httpHeaders -> {
                }), request, StickerModelResponse.class
        );

        return response.base64EncodedImages().stream()
                .map(each -> Base64.getDecoder().decode(each))
                .map(each -> s3Agent.uploadFile(each, UUID.nameUUIDFromBytes(each).toString(), STICKER_MIME_TYPE))
                .toList();
    }
}
