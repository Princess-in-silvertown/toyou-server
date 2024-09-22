package slvtwn.khu.toyouserver.agent.gpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import slvtwn.khu.toyouserver.agent.WebClientWrapper;

@RequiredArgsConstructor
@Component
public class ChatGptAgent {

    private final ChatGptConfiguration configuration;
    private final WebClientWrapper webClientWrapper;

    public ChatGptResponse requestWithPrompt(String prompt) {
        HashMap<String, Object> body = setupBody(prompt);

        return webClientWrapper.send(configuration.getTextModelEndpoint(), (httpHeaders -> {
            httpHeaders.add("Authorization", "Bearer " + configuration.getOpenaiKey());
        }), body, ChatGptResponse.class);
    }


    private HashMap<String, Object> setupBody(String prompt) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("model", configuration.getTextModel());

        HashMap<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);

        List<HashMap<String, String>> messages = new ArrayList<>();
        messages.add(userMessage);
        body.put("messages", messages);
        return body;
    }
}
