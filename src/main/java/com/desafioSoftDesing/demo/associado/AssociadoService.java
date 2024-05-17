package com.desafioSoftDesing.demo.associado;

import com.desafioSoftDesing.demo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AssociadoService {

    private final AssociadoRepository associadoRepository;

    @Autowired
    public AssociadoService(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    public ResponseEntity<?> cadastrarAssociado(DadosCadastroAssociado dadosCadastroAssociado) {
        try {
            Associado associado = new Associado(dadosCadastroAssociado);
            Associado associadoSalvo = associadoRepository.save(associado);
            return ResponseEntity.ok(associadoSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar associado: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getAssociado(Long id) {
        Optional<Associado> optionalAssociado = associadoRepository.findById(id);
        if (optionalAssociado.isPresent() && optionalAssociado.get().isAtivo()) {
            return ResponseEntity.ok(optionalAssociado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associado não encontrado, ou inativo.");
        }
    }

    public ResponseEntity<?> atualizarAssociado(Long id, DadosCadastroAssociado dadosCadastroAssociado) {
        try {
            Associado associado = getAssociadoById(id);
            associado.setNome(dadosCadastroAssociado.nome());
            associado.setCpf(dadosCadastroAssociado.cpf());
            associado.setVotante(dadosCadastroAssociado.votante());
            Associado associadoAtualizado = associadoRepository.save(associado);
            return ResponseEntity.ok(associadoAtualizado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associado não encontrado com o ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar associado: " + e.getMessage());
        }
    }

    //Optei por fazer uma deleção lógica, assim ainda podemos fazer o controle de quem já foi associado, assim como seus votos
    public ResponseEntity<?> deleteAssociado(Long id) {
        Optional<Associado> optionalAssociado = associadoRepository.findById(id);
        if (optionalAssociado.isPresent()) {
            Associado associado = optionalAssociado.get();
            associado.setAtivo(false);
            associadoRepository.save(associado);
            return ResponseEntity.ok("Associado deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao tentar deletar o associado.");
        }
    }

    public ResponseEntity<Iterable<Associado>> buscarTodosAssociados() {
        return ResponseEntity.ok(associadoRepository.findAll());
    }

    public String getVotante(Long id) {
        Associado associado = getAssociadoById(id);
        return associado.isVotante() ? "ABLE_TO_VOTE" : "UNABLE_TO_VOTE";
    }

    public ResponseEntity<?> buscarAssociadoPorCpf(String cpf) {
        cpf = StringUtil.removeNonDigits(cpf);
        Optional<Object> optionalAssociado = associadoRepository.findByCpf(cpf);
        if (optionalAssociado.isPresent()) {
            return ResponseEntity.ok(optionalAssociado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Associado não encontrado");
        }
    }

    private Associado getAssociadoById(Long id) {
        Optional<Associado> optionalAssociado = associadoRepository.findById(id);
        if (optionalAssociado.isPresent() && optionalAssociado.get().isAtivo()) {
            return optionalAssociado.get();
        } else {
            throw new IllegalArgumentException("Associado não encontrado, ou inativo.");
        }
    }
}
