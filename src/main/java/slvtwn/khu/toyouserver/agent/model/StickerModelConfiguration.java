package slvtwn.khu.toyouserver.agent.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class StickerModelConfiguration {

    @Value("sticker-model.endpoint")
    private String endpoint;
}
