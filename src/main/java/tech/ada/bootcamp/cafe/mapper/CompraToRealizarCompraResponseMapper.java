package tech.ada.bootcamp.cafe.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tech.ada.bootcamp.cafe.entidades.Compra;
import tech.ada.bootcamp.cafe.entidades.ItemCompra;
import tech.ada.bootcamp.cafe.payloads.ItemCompraResponse;
import tech.ada.bootcamp.cafe.payloads.RealizarCompraResponse;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompraToRealizarCompraResponseMapper {

    public static RealizarCompraResponse formatarResposta(Compra compraSaved, List<ItemCompra> itemComprasSaved) {
        RealizarCompraResponse realizarCompraResponse = new RealizarCompraResponse();
        realizarCompraResponse.setDataCompra(compraSaved.getDataCompra().toLocalDate());
        realizarCompraResponse.setTotal(compraSaved.getValorTotal());
        realizarCompraResponse.setItems(formatarRespostaItens(itemComprasSaved));
        realizarCompraResponse.setStatus(compraSaved.getStatus().getLabel());
        return realizarCompraResponse;
    }

    private static List<ItemCompraResponse> formatarRespostaItens(List<ItemCompra> itemComprasSaved) {
        return itemComprasSaved.stream().map(CompraToRealizarCompraResponseMapper::formatarRespostaItem).toList();
    }

    private static ItemCompraResponse formatarRespostaItem(ItemCompra itemCompra) {
        ItemCompraResponse itemCompraResponse = new ItemCompraResponse();
        itemCompraResponse.setValor(itemCompra.getTotal());
        itemCompraResponse.setQuantidade(itemCompra.getQuantidade());
        String identificador;
        boolean isCombo = false;
        if (Objects.nonNull(itemCompra.getCombo())) {
            isCombo = true;
            identificador = itemCompra.getCombo().getDescricao();
        } else {
            identificador = itemCompra.getItem().getDescricao();

        }
        itemCompraResponse.setIdentificadorItem(identificador);
        itemCompraResponse.setCombo(isCombo);
        return itemCompraResponse;
    }
}
