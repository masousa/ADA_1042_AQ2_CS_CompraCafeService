package tech.ada.bootcamp.cafe.payloads;

import lombok.Data;

@Data
public class ItemCompraRequest {

    private Long quantidade;
    private String identificadorItem;
    private boolean isCombo;
}
