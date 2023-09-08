package tech.ada.bootcamp.cafe.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import tech.ada.bootcamp.cafe.client.PagamentoClient;
import tech.ada.bootcamp.cafe.entidades.Compra;
import tech.ada.bootcamp.cafe.entidades.StatusCompra;
import tech.ada.bootcamp.cafe.payloads.FormaPagamentoRequest;
import tech.ada.bootcamp.cafe.payloads.PagamentoRequest;
import tech.ada.bootcamp.cafe.payloads.PagamentoResponse;
import tech.ada.bootcamp.cafe.repository.CompraRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RealizarPagamentoService {

    private final PagamentoClient pagamentoClient;
    private final CompraRepository compraRepository;

    public void execute(Compra compra, FormaPagamentoRequest formaPagamentoRequest) {
        PagamentoRequest pagamentoRequest = new PagamentoRequest();
        pagamentoRequest.setTipoPagamento(formaPagamentoRequest.getTipoPagamento());
        pagamentoRequest.setDesconto(compra.getTotalDescontos());
        pagamentoRequest.setValor(compra.getValorTotal());
        pagamentoRequest.setCvv(formaPagamentoRequest.getCvv());
        pagamentoRequest.setNomeTitular(StringUtils.hasText(
                formaPagamentoRequest.getNomeTitular()) ? formaPagamentoRequest.getNomeTitular()
                : compra.getCliente().getNome());
        pagamentoRequest.setIdentificadorUsuario(compra.getCliente().getIdentificador());
        pagamentoRequest.setNumeroCartao(formaPagamentoRequest.getNumeroCartao());
        pagamentoRequest.setIdentificadorCompra(compra.getIdentificador());

        Mono<PagamentoResponse> pagamentoResponseMono = pagamentoClient.realizarPagamento(pagamentoRequest);

        PagamentoResponse pagamentoResponse = new PagamentoResponse();
        pagamentoResponse.setIdentificadorCompra(compra.getIdentificador());
        pagamentoResponse.setStatusCompra(StatusCompra.PENDENTE);
        log.info("Enviando requisição de pagamento {}", pagamentoRequest);
        pagamentoResponseMono.onErrorReturn(pagamentoResponse).doOnNext(this::updateCompra)
                .subscribe();

    }

    private void updateCompra(PagamentoResponse pagamentoResponse) {
        log.info("Atualizando status da compra {}", pagamentoResponse);
        Optional<Compra> optionalCompra = compraRepository.findByIdentificador(pagamentoResponse.getIdentificadorCompra());
        if (optionalCompra.isPresent()) {
            Compra compra = optionalCompra.get();
            compra.setStatus(pagamentoResponse.getStatusCompra());
            compraRepository.save(compra);

        }

    }


}
