package com.desafioSoftDesing.demo.pauta;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PautaService {

    private final PautaRepository pautaRepository;

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public ResponseEntity<?> cadastrarPauta(DadosCadastroPauta dadosCadastroPauta) {
        try {
            if(isDataInicioAfterDataFim(dadosCadastroPauta)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de início da votação não pode ser maior que a data de fim da votação.");
            }

            if(isDataInicioBeforeDataAtual(dadosCadastroPauta)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de início da votação não pode ser menor que a data atual.");
            }

            Pauta pauta = new Pauta(dadosCadastroPauta);
            Pauta pautaSalva = pautaRepository.save(pauta);
            return ResponseEntity.ok(pautaSalva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar pauta: " + e.getMessage());
        }
    }

    public ResponseEntity<?> buscarPautaPorId(Long id) {
        Optional<Pauta> pautaOptional = pautaRepository.findById(id);
        if (pautaOptional.isPresent()) {
            return ResponseEntity.ok(pautaOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pauta não encontrada");
        }
    }

    public ResponseEntity<?> deletarPauta(Long id) {
        Pauta pauta = buscarPautaById(id);
        if (!isVotacaoIniciada(pauta)) {
            pautaRepository.delete(pauta);
            return ResponseEntity.ok("Pauta deletada com sucesso.");
        } else {
            return statusVotacaoPauta(pauta);
        }
    }

    public ResponseEntity<?> atualizarPauta(Long id, DadosCadastroPauta dadosCadastroPauta) {
        try {
            Pauta pauta = buscarPautaById(id);
            if (isVotacaoNaoIniciada(pauta)) {
                pauta.setTitulo(dadosCadastroPauta.titulo());
                pauta.setDescricao(dadosCadastroPauta.descricao());
                pauta.setVotacaoInicio(LocalDate.parse(dadosCadastroPauta.votacaoInicio()));
                pauta.setVotacaoFim(LocalDate.parse(dadosCadastroPauta.votacaoFim()));
                Pauta pautaAtualizada = pautaRepository.save(pauta);
                return ResponseEntity.ok(pautaAtualizada);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não é possível atualizar pauta com votação iniciada ou encerrada.");

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pauta não encontrada com o ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar pauta: " + e.getMessage());
        }
    }

    public Iterable<Pauta> buscarTodasPautas() {
        return pautaRepository.findAll();
    }

    private Pauta buscarPautaById(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pauta não encontrada"));
    }
     private boolean isVotacaoNaoIniciada(Pauta pauta) {
         return LocalDate.now().isBefore(pauta.getVotacaoInicio());
     }

    private boolean isVotacaoIniciada(Pauta pauta) {
        return LocalDate.now().isAfter(pauta.getVotacaoInicio());
    }

    private boolean isVotacaoEmAndamento(Pauta pauta) {
        return LocalDate.now().isAfter(pauta.getVotacaoInicio()) && LocalDate.now().isBefore(pauta.getVotacaoFim());
    }

    private boolean isDataInicioAfterDataFim(DadosCadastroPauta dadosCadastroPauta) {
        LocalDate dataInicio = LocalDate.parse(dadosCadastroPauta.votacaoInicio());
        LocalDate dataFim = LocalDate.parse(dadosCadastroPauta.votacaoFim());

        return dataInicio.isAfter(dataFim);
    }

    private boolean isDataInicioBeforeDataAtual(DadosCadastroPauta dadosCadastroPauta) {
        LocalDate dataInicio = LocalDate.parse(dadosCadastroPauta.votacaoInicio());
        return dataInicio.isBefore(LocalDate.now());
    }

    private  ResponseEntity<?> statusVotacaoPauta(Pauta pauta) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message;

        if(isVotacaoEmAndamento(pauta)) {
            message = "Votação da pauta em andamento.";
        } else if(LocalDate.now().isAfter(pauta.getVotacaoFim())) {
            message = "Votação da pauta já encerrada.";
        }else {
            message = "Erro ao tentar deletar a pauta.";
        }

        return ResponseEntity.status(status).body(message);
    }
}
