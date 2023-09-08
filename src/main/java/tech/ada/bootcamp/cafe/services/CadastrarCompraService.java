package tech.ada.bootcamp.cafe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.ada.bootcamp.cafe.entidades.*;
import tech.ada.bootcamp.cafe.exceptions.NotFoundException;
import tech.ada.bootcamp.cafe.payloads.ItemCompraRequest;
import tech.ada.bootcamp.cafe.payloads.ItemCompraResponse;
import tech.ada.bootcamp.cafe.payloads.RealizarCompraRequest;
import tech.ada.bootcamp.cafe.payloads.RealizarCompraResponse;
import tech.ada.bootcamp.cafe.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CadastrarCompraService {

    private final CompraRepository compraRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComboRepository comboRepository;
    private final ItemRepository itemRepository;
    private final ItemCompraRepository itemCompraRepository;

    public RealizarCompraResponse cadastrarCompra(RealizarCompraRequest realizarCompraRequest) {
        Compra compra = new Compra();
        compra.setIdentificador(UUID.randomUUID().toString());
        compra.setDataCompra(LocalDateTime.now());
        compra.setStatus(StatusCompra.EM_PROCESSAMENTO);
        compra.setCliente(usuarioRepository.findByIdentificador(realizarCompraRequest.getIdentificadorCliente())
                .orElseThrow(() -> new NotFoundException("Usuario")));
        List<ItemCompra> itensCompra = converterItensCompra
                (realizarCompraRequest.getItems(), compra, compra.getCliente().getDesconto());
        compra.setValorTotal(itensCompra.stream().mapToDouble(ItemCompra::getTotal).sum());
        compra.setTotalDescontos(itensCompra.stream().mapToDouble(ItemCompra::getDesconto).sum());
        Compra compraSaved = compraRepository.save(compra);
        itensCompra.forEach(itemCompra -> itemCompra.setCompra(compraSaved));
        List<ItemCompra> itemComprasSaved = itemCompraRepository.saveAll(itensCompra);

        return formatarResposta(compraSaved, itemComprasSaved);
    }

    private RealizarCompraResponse formatarResposta(Compra compraSaved, List<ItemCompra> itemComprasSaved) {
        RealizarCompraResponse realizarCompraResponse = new RealizarCompraResponse();
        realizarCompraResponse.setDataCompra(compraSaved.getDataCompra().toLocalDate());
        realizarCompraResponse.setTotal(compraSaved.getValorTotal());
        realizarCompraResponse.setItems(formatarRespostaItens(itemComprasSaved));
        realizarCompraResponse.setStatus(compraSaved.getStatus().getLabel());
        return realizarCompraResponse;
    }

    private RealizarCompraResponse formatarResposta(Compra compraSaved) {
        List<ItemCompra> itemCompras = itemCompraRepository.findByCompraId(compraSaved.getId());
        return formatarResposta(compraSaved, itemCompras);
    }

    private List<ItemCompraResponse> formatarRespostaItens(List<ItemCompra> itemComprasSaved) {
        return itemComprasSaved.stream().map(this::formatarRespostaItem).toList();
    }

    private ItemCompraResponse formatarRespostaItem(ItemCompra itemCompra) {
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

    private List<ItemCompra> converterItensCompra(List<ItemCompraRequest> items, Compra compra, double desconto) {
        List<ItemCompra> itemsCompra = new ArrayList<>();

        for (ItemCompraRequest itemRequest :
                items) {
            ItemCompra iCompra = new ItemCompra();
            iCompra.setCompra(compra);

            iCompra.setQuantidade(itemRequest.getQuantidade());
            double valorBruto;
            if (itemRequest.isCombo()) {
                Combo combo = comboRepository.findByIdentificador(itemRequest.getIdentificadorItem())
                        .orElseThrow(() -> new NotFoundException("Combo"));
                iCompra.setCombo(combo);
                valorBruto = combo.getValorFinal() * itemRequest.getQuantidade();
            } else {
                Item item = itemRepository.findByIdentificador(itemRequest.getIdentificadorItem())
                        .orElseThrow(() -> new NotFoundException("Item"));
                iCompra.setItem(item);
                valorBruto = item.getValorUnitario() * itemRequest.getQuantidade();
            }

            iCompra.setDesconto((valorBruto * desconto) / 100);


            iCompra.setValorBruto(valorBruto);
            iCompra.setTotal(valorBruto - iCompra.getDesconto());
            itemsCompra.add(iCompra);
        }

        return itemsCompra;
    }


    public List<RealizarCompraResponse> listarCompras() {
        return compraRepository.findAll()
                .stream().map(this::formatarResposta).toList();
    }
}
