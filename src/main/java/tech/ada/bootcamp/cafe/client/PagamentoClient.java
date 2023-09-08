package tech.ada.bootcamp.cafe.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;
import tech.ada.bootcamp.cafe.payloads.PagamentoRequest;
import tech.ada.bootcamp.cafe.payloads.PagamentoResponse;

public interface PagamentoClient {

    @PostExchange(value = "/", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<PagamentoResponse> realizarPagamento(@RequestBody PagamentoRequest request);

}
