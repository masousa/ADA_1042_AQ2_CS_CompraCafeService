package tech.ada.bootcamp.cafe.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagamentoRequest {
    private TipoPagamento tipoPagamento;
    private String numeroCartao;
    private String cvv;
    private String nomeTitular;
    private double valor;
    private double desconto;
    private String identificadorUsuario;
}
