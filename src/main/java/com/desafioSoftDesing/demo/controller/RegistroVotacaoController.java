package com.desafioSoftDesing.demo.controller;

import com.desafioSoftDesing.demo.RegistroVotacao.DadosCadastroRegistroVotacao;
import com.desafioSoftDesing.demo.RegistroVotacao.RegistroVotacao;
import com.desafioSoftDesing.demo.RegistroVotacao.RegistroVotacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registro-votacao")
public class RegistroVotacaoController {

    private final RegistroVotacaoService registroVotacaoService;

    public RegistroVotacaoController(RegistroVotacaoService registroVotacaoService) {
        this.registroVotacaoService = registroVotacaoService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarRegistroVotacao(@RequestBody DadosCadastroRegistroVotacao dadosCadastroRegistroVotacao) {
        return registroVotacaoService.cadastrarRegistroVotacao(dadosCadastroRegistroVotacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarRegistroVotacaoPorId(@PathVariable Long id) {
        return registroVotacaoService.buscarRegistroVotacaoPorId(id);
    }

    @DeleteMapping("/pauta/{idPauta}/associado/{idAssociado}")
    public ResponseEntity<?> deletarRegistroVotacao(@PathVariable Long idPauta, @PathVariable Long idAssociado) {
        return registroVotacaoService.deletarRegistroVotacao(idPauta, idAssociado);
    }

    @GetMapping("/pauta/{id}")
    public ResponseEntity<Iterable<RegistroVotacao>> buscarRegistrosVotacaoPorPauta(@PathVariable Long id) {
        return registroVotacaoService.buscarRegistrosVotacaoPorPauta(id);
    }

    @GetMapping("/associado/{id}")
    public ResponseEntity<Iterable<RegistroVotacao>> buscarRegistrosVotacaoPorAssociado(@PathVariable Long id) {
        return registroVotacaoService.buscarRegistrosVotacaoPorAssociado(id);
    }

    @GetMapping("/pauta/{idPauta}/associado/{idAssociado}")
    public ResponseEntity<RegistroVotacao> buscarRegistroVotacaoPorPautaEAssociado(@PathVariable Long idPauta, @PathVariable Long idAssociado) {
        return registroVotacaoService.buscarRegistroVotacaoPorPautaEAssociado(idPauta, idAssociado);
    }

    @GetMapping("/pauta/{idPauta}/data-voto/{dataVoto}")
    public ResponseEntity<Iterable<RegistroVotacao>> buscarRegistrosVotacaoPorPautaEDataVoto(@PathVariable Long idPauta, @PathVariable String dataVoto) {
        return registroVotacaoService.buscarRegistrosVotacaoPorPautaEDataVoto(idPauta, dataVoto);
    }
}
