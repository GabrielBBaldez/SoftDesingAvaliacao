package com.desafioSoftDesing.demo.controller;

import com.desafioSoftDesing.demo.associado.Associado;
import com.desafioSoftDesing.demo.associado.AssociadoService;
import com.desafioSoftDesing.demo.associado.DadosCadastroAssociado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/associado")
public class AssociadoController {
    private final AssociadoService associadoService;

    @Autowired
    public AssociadoController(AssociadoService associadoService) {
        this.associadoService = associadoService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarAssociado(@RequestBody DadosCadastroAssociado dadosCadastroAssociado) {
        return associadoService.cadastrarAssociado(dadosCadastroAssociado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssociado(@PathVariable Long id) {
        return associadoService.getAssociado(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAssociado(@PathVariable Long id, @RequestBody DadosCadastroAssociado dadosCadastroAssociado) {
        return associadoService.atualizarAssociado(id, dadosCadastroAssociado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssociado(@PathVariable Long id) {
        return associadoService.deleteAssociado(id);
    }

    @GetMapping("/votante/{id}")
    public ResponseEntity<String> getVotante(@PathVariable Long id) {
        return ResponseEntity.ok(associadoService.getVotante(id));
    }

    @GetMapping("/todos")
    public ResponseEntity<Iterable<Associado>> buscarTodosAssociados() {
        return associadoService.buscarTodosAssociados();
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> buscarAssociadoPorCpf(@PathVariable String cpf) {
        return associadoService.buscarAssociadoPorCpf(cpf);
    }
}
