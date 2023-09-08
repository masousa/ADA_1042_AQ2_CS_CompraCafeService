package tech.ada.bootcamp.cafe.payloads;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RealizarCompraResponse {
    private LocalDate dataCompra;
    private double total;

    private List<ItemCompraResponse> items;

    private String status;

    private FormaPagamentoResponse pagamento;
}
