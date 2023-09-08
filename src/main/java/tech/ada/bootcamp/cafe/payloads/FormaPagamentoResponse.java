package tech.ada.bootcamp.cafe.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormaPagamentoResponse {

    private String numeroPix;
    private String codigoTransacao;
}
