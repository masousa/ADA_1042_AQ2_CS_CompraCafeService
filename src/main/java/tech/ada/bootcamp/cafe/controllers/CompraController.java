package tech.ada.bootcamp.cafe.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.bootcamp.cafe.payloads.RealizarCompraRequest;
import tech.ada.bootcamp.cafe.payloads.RealizarCompraResponse;
import tech.ada.bootcamp.cafe.services.CadastrarCompraService;

@RestController
@RequestMapping(name = "/compra")
@RequiredArgsConstructor
public class CompraController {

    private final CadastrarCompraService cadastrarCompraService;

    @PostMapping(value = "/")
    public RealizarCompraResponse realizarCompra(@RequestBody RealizarCompraRequest realizarCompraRequest) {
        return cadastrarCompraService.cadastrarCompra(realizarCompraRequest);
    }
}
