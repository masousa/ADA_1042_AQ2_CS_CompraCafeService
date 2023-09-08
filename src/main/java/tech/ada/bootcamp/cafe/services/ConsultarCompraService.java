package tech.ada.bootcamp.cafe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.ada.bootcamp.cafe.client.PagamentoClient;
import tech.ada.bootcamp.cafe.entidades.Compra;
import tech.ada.bootcamp.cafe.mapper.CompraToRealizarCompraResponseMapper;
import tech.ada.bootcamp.cafe.payloads.FormaPagamentoResponse;
import tech.ada.bootcamp.cafe.payloads.PagamentoResponse;
import tech.ada.bootcamp.cafe.payloads.RealizarCompraResponse;
import tech.ada.bootcamp.cafe.repository.CompraRepository;
import tech.ada.bootcamp.cafe.repository.ItemCompraRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarCompraService {

    private final CompraRepository compraRepository;
    private final PagamentoClient pagamentoClient;
    private final ItemCompraRepository itemCompraRepository;

    public RealizarCompraResponse execute(String idCompra) {
        Optional<Compra> optionalCompra = compraRepository.findByIdentificador(idCompra);
        if (optionalCompra.isPresent()) {
            Compra compra = optionalCompra.get();
            RealizarCompraResponse realizarCompraResponse = CompraToRealizarCompraResponseMapper
                    .formatarResposta(compra, itemCompraRepository.findByCompraId(compra.getId()));


            PagamentoResponse pagamentoResponse = pagamentoClient
                    .getStatusPagamento(compra.getIdentificador());
            if (Objects.nonNull(pagamentoResponse)) {
                FormaPagamentoResponse formaPagamentoResponse =
                        FormaPagamentoResponse.builder()
                                .numeroPix(pagamentoResponse.getNumeroPix())
                                .codigoTransacao(pagamentoResponse.getCodigoTransacao())
                                .build();
                realizarCompraResponse.setPagamento(formaPagamentoResponse);
                realizarCompraResponse.setStatus(pagamentoResponse.getStatusCompra().getLabel());
            }

            return realizarCompraResponse;
        }
        return new RealizarCompraResponse();

    }
}
