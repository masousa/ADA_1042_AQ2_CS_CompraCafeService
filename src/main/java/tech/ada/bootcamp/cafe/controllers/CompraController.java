package tech.ada.bootcamp.cafe.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.ada.bootcamp.cafe.payloads.RealizarCompraRequest;
import tech.ada.bootcamp.cafe.payloads.RealizarCompraResponse;
import tech.ada.bootcamp.cafe.services.CadastrarCompraService;
import tech.ada.bootcamp.cafe.services.ConsultarCompraService;

@RestController
@RequestMapping(name = "/compra")
@RequiredArgsConstructor
public class CompraController {

    private final CadastrarCompraService cadastrarCompraService;
    private final ConsultarCompraService consultarCompraService;

    @PostMapping(value = "/")
    public RealizarCompraResponse realizarCompra(@RequestBody RealizarCompraRequest realizarCompraRequest) {
        return cadastrarCompraService.cadastrarCompra(realizarCompraRequest);
    }

    @GetMapping("/{idCompra}")
    public RealizarCompraResponse consultarCompra(@PathVariable String idCompra) {
        return consultarCompraService.execute(idCompra);
    }
}
