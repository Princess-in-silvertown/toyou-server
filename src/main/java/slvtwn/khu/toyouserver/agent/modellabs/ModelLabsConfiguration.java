package slvtwn.khu.toyouserver.agent.modellabs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ModelLabsConfiguration {

    @Value("${model-labs.endpoint}")
    private String modelLabsEndPoint;
    @Value("${model-labs.key}")
    private String key;
    @Value("${model-labs.model-id}")
    private String modelId;
}
