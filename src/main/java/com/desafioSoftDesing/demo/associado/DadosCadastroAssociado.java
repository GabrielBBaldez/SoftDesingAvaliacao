package com.desafioSoftDesing.demo.associado;

import com.desafioSoftDesing.demo.util.StringUtil;

public record DadosCadastroAssociado(
        String nome,
        String cpf,
        Boolean votante
) {
    public DadosCadastroAssociado {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        cpf = StringUtil.removeNonDigits(cpf); // Remove todos os caracteres não numéricos
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 caracteres");
        }
    }
}
