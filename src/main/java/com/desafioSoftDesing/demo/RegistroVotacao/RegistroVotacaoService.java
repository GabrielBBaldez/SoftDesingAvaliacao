package com.desafioSoftDesing.demo.RegistroVotacao;

import com.desafioSoftDesing.demo.associado.Associado;
import com.desafioSoftDesing.demo.associado.AssociadoRepository;
import com.desafioSoftDesing.demo.pauta.Pauta;
import com.desafioSoftDesing.demo.pauta.PautaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class RegistroVotacaoService {

    private final RegistroVotacaoRepository registroVotacaoRepository;
    private final AssociadoRepository associadoRepository;
    private final PautaRepository pautaRepository;

    public RegistroVotacaoService(RegistroVotacaoRepository registroVotacaoRepository, AssociadoRepository associadoRepository, PautaRepository pautaRepository) {
        this.registroVotacaoRepository = registroVotacaoRepository;
        this.associadoRepository = associadoRepository;
        this.pautaRepository = pautaRepository;
    }


    public ResponseEntity<?> cadastrarRegistroVotacao(DadosCadastroRegistroVotacao dadosCadastroRegistroVotacao) {
        try {

            Associado associado = associadoRepository.findById(dadosCadastroRegistroVotacao.associadoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associado não encontrado"));

            Pauta pauta = pautaRepository.findById(dadosCadastroRegistroVotacao.pautaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pauta não encontrada"));

            if(!isAssociadoAtivo(associado.getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Associado não está ativo");
            }

            if (isAssociadoVotouPauta(pauta.getId(), associado.getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Associado já votou nesta pauta");
            }

            if (!isPautaVotacaoEmAndamento(pauta)) {
                verificarStatusVotacaoPauta(pauta);
            }

            RegistroVotacao registroVotacao = new RegistroVotacao(dadosCadastroRegistroVotacao, pauta, associado);
            RegistroVotacao registroVotacaoSalvo = registroVotacaoRepository.save(registroVotacao);

            return ResponseEntity.ok(registroVotacaoSalvo);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao cadastrar registro de votação: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar registro de votação: " + e.getMessage());
        }
    }

    public ResponseEntity<?> buscarRegistroVotacaoPorId(Long id) {
        Optional<RegistroVotacao> optionalRegistroVotacao = registroVotacaoRepository.findById(id);
        if (optionalRegistroVotacao.isPresent()) {
            return ResponseEntity.ok(optionalRegistroVotacao.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro de votação não encontrado");
        }
    }

    //Existe apenas a deleção de votos, a atualização de um voto não é permito, assim o associado podera votar de novo..
    public ResponseEntity<?> deletarRegistroVotacao(Long idPauta, Long idAssociado) {
        try {
            RegistroVotacao registroVotacao = registroVotacaoRepository.findByPautaIdAndAssociadoId(idPauta, idAssociado);
            if (registroVotacao != null && isPautaVotacaoEmAndamento(registroVotacao.getPauta())){
                registroVotacaoRepository.delete(registroVotacao);
                return ResponseEntity.ok("Registro de votação deletado com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro de votação não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar registro de votação: " + e.getMessage());
        }
    }

    public ResponseEntity<Iterable<RegistroVotacao>> buscarRegistrosVotacaoPorPauta(Long idPauta) {
        return ResponseEntity.ok(registroVotacaoRepository.findByPautaId(idPauta));
    }

    public ResponseEntity<Iterable<RegistroVotacao>> buscarRegistrosVotacaoPorAssociado(Long idAssociado) {
        return ResponseEntity.ok(registroVotacaoRepository.findByAssociadoId(idAssociado));
    }

    public ResponseEntity<RegistroVotacao> buscarRegistroVotacaoPorPautaEAssociado(Long idPauta, Long idAssociado) {
        return ResponseEntity.ok(registroVotacaoRepository.findByPautaIdAndAssociadoId(idPauta, idAssociado));
    }

    public ResponseEntity<Iterable<RegistroVotacao>> buscarRegistrosVotacaoPorPautaEDataVoto(Long idPauta, String dataVoto) {
        return ResponseEntity.ok(registroVotacaoRepository.findByPautaIdAndDataVoto(idPauta, LocalDate.parse(dataVoto)));
    }

    private boolean isAssociadoAtivo(Long idAssociado) {
        return associadoRepository.findById(idAssociado).map(Associado::isAtivo).orElse(false);
    }

    private boolean isAssociadoVotouPauta(Long idPauta, Long idAssociado) {
        RegistroVotacao registroVotacao = registroVotacaoRepository.findByPautaIdAndAssociadoId(idPauta, idAssociado);
        return registroVotacao != null ? registroVotacao.getVoto() : false;
    }

    private boolean isPautaVotacaoEmAndamento(Pauta pauta) {
        return LocalDate.now().isAfter(pauta.getVotacaoInicio()) && LocalDate.now().isBefore(pauta.getVotacaoFim());
    }

    private ResponseEntity<?> verificarStatusVotacaoPauta(Pauta pauta) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message;

        if(LocalDate.now().isBefore(pauta.getVotacaoInicio())){
            message = String.format("Votação da pauta não começou, começará em: %s", pauta.getVotacaoInicio());
        } else if(LocalDate.now().isAfter(pauta.getVotacaoFim())){
            message = String.format("Votação da pauta já acabou, acabou em: %s", pauta.getVotacaoFim());
        } else {
            message = "Votação da pauta não está em andamento";
        }

       return ResponseEntity.status(status).body(message);
    }
}
