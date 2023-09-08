package tech.ada.bootcamp.cafe.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import tech.ada.bootcamp.cafe.entidades.StatusCompra;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagamentoResponse {
    private String numeroPix;
    private String codigoTransacao;
    private boolean pendente;
    private String identificadorCompra;
    private StatusCompra statusCompra;
}
