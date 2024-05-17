package com.desafioSoftDesing.demo.pauta;

public record DadosCadastroPauta(
        String titulo,
        String descricao,
        String votacaoInicio,
        String votacaoFim
) {
    public DadosCadastroPauta {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia");
        }
        if (votacaoInicio == null) {
            throw new IllegalArgumentException("Data de início da votação não pode ser nula");
        }
        if (votacaoFim == null) {
            throw new IllegalArgumentException("Data de fim da votação não pode ser nula");
        }
    }
}
