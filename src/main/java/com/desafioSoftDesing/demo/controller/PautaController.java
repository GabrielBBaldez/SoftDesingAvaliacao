package com.desafioSoftDesing.demo.controller;

import com.desafioSoftDesing.demo.pauta.DadosCadastroPauta;
import com.desafioSoftDesing.demo.pauta.Pauta;
import com.desafioSoftDesing.demo.pauta.PautaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pauta")
public class PautaController {

    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarPauta(@RequestBody DadosCadastroPauta dadosCadastroPauta) {
        return pautaService.cadastrarPauta(dadosCadastroPauta);
    }

    @GetMapping("/todas")
    public ResponseEntity<Iterable<Pauta>> buscarTodasPautas() {
        Iterable<Pauta> pautas = pautaService.buscarTodasPautas();
        return ResponseEntity.ok(pautas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPauta(@PathVariable Long id, @RequestBody DadosCadastroPauta dadosCadastroPauta) {
        return pautaService.atualizarPauta(id, dadosCadastroPauta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPauta(@PathVariable Long id) {
        return pautaService.deletarPauta(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPautaPorId(@PathVariable Long id) {
        return pautaService.buscarPautaPorId(id);
    }
}
