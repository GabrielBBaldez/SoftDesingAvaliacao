package com.desafioSoftDesing.demo.RegistroVotacao;

public record DadosCadastroRegistroVotacao(
        Long associadoId,
        Long pautaId,
        Boolean voto,
        String dataVoto
) {
}
