package slvtwn.khu.toyouserver.agent;

import java.util.function.Consumer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientWrapper {

    public <T> T send(String uri, Consumer<HttpHeaders> headersConsumer, Object body, Class<T> responseType) {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                .build();

        WebClient webClient = WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .build();

        return webClient.post()
                .uri(uri)
                .header("Content-Type", "application/json")
                .headers(headersConsumer)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(responseType)
                .block();
    }
}
