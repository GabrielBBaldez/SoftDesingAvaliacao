package com.desafioSoftDesing.demo.RegistroVotacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RegistroVotacaoRepository extends JpaRepository<RegistroVotacao, Long> {
    Iterable<RegistroVotacao> findByPautaId(Long idPauta);

    Iterable<RegistroVotacao> findByAssociadoId(Long idAssociado);

    RegistroVotacao findByPautaIdAndAssociadoId(Long idPauta, Long idAssociado);

    Iterable<RegistroVotacao> findByPautaIdAndDataVoto(Long pauta_id, LocalDate dataVoto);
}
