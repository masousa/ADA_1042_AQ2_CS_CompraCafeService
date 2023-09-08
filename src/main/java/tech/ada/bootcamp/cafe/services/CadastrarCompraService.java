package tech.ada.bootcamp.cafe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.ada.bootcamp.cafe.entidades.*;
import tech.ada.bootcamp.cafe.exceptions.NotFoundException;
import tech.ada.bootcamp.cafe.mapper.CompraToRealizarCompraResponseMapper;
import tech.ada.bootcamp.cafe.payloads.ItemCompraRequest;
import tech.ada.bootcamp.cafe.payloads.RealizarCompraRequest;
import tech.ada.bootcamp.cafe.payloads.RealizarCompraResponse;
import tech.ada.bootcamp.cafe.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CadastrarCompraService {

    private final CompraRepository compraRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComboRepository comboRepository;
    private final ItemRepository itemRepository;
    private final ItemCompraRepository itemCompraRepository;
    private final RealizarPagamentoService realizarPagamentoService;

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
        realizarPagamentoService.execute(compraSaved, realizarCompraRequest.getFormaPagamento());
        return CompraToRealizarCompraResponseMapper.formatarResposta(compraSaved, itemComprasSaved);
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

}
