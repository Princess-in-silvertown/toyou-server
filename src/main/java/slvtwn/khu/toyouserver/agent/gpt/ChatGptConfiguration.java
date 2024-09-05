package slvtwn.khu.toyouserver.agent.gpt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ChatGptConfiguration {

    @Value("${openai.key}")
    private String openaiKey;
    @Value("${openai.image-generation.endpoint}")
    private String imageModelEndpoint;
    @Value("${openai.text-generation.endpoint}")
    private String textModelEndpoint;
    @Value("${openai.image-generation.model}")
    private String imageModel;
    @Value("${openai.text-generation.model}")
    private String textModel;
    @Value("${openai.image-generation.n}")
    private String n;
    @Value("${openai.image-generation.size}")
    private String size;
}
