package tech.ada.bootcamp.cafe.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCompra {

    EM_PROCESSAMENTO("Em processamento"), PENDENTE("Pendente"), PAGO("Pago");

    private final String label;

}
