package slvtwn.khu.toyouserver.agent.gpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class ChatGptAgent {

    private ChatGptConfiguration configuration;

    public Mono<ChatGptResponse> requestWithPrompt(String prompt) {
        HashMap<String, Object> body = setupBody(prompt);

        return WebClient.create()
                .post()
                .uri(configuration.getImageModelEndpoint())
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + configuration.getOpenaiKey())
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(ChatGptResponse.class);
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
