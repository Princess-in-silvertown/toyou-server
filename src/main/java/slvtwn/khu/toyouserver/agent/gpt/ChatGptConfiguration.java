package slvtwn.khu.toyouserver.agent.gpt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ChatGptConfiguration {

    @Value("${openai.key}")
    private String openaiKey;
    @Value("${openai.text-generation.endpoint}")
    private String textModelEndpoint;
    @Value("${openai.text-generation.model}")
    private String textModel;
}
