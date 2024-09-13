package slvtwn.khu.toyouserver.agent;

import java.util.function.Consumer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientWrapper {

    public <T> T send(String uri, Consumer<HttpHeaders> headersConsumer, Object body, Class<T> responseType) {
        return WebClient.create()
                .post()
                .uri(uri)
                .header("Content-Type", "application/json")
                .headers(headersConsumer)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(responseType)
                .block();
    }
}
