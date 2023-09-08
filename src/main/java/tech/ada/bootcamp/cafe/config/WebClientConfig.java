package tech.ada.bootcamp.cafe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import tech.ada.bootcamp.cafe.client.PagamentoClient;

@Configuration
public class WebClientConfig {
    @Value("${negocio.pagamento.url}")
    private String url;

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .baseUrl(url)
                .build();
    }

    @Bean
    PagamentoClient postPagamentoClient(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                        .build();
        return httpServiceProxyFactory.createClient(PagamentoClient.class);
    }
}
