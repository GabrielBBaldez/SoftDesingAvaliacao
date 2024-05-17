package com.desafioSoftDesing.demo.associado;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "associado", schema = "public")
@Getter
@Setter
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 255)
    private String nome;

    @CPF
    @Column(name = "cpf", length = 11, unique = true)
    private String cpf;

    @Column(name = "votante")
    private boolean votante;

    @Column(name = "ativo")
    private boolean ativo = true;

    public Associado(DadosCadastroAssociado dadosCadastroAssociado) {
        this.nome = dadosCadastroAssociado.nome();
        this.cpf = dadosCadastroAssociado.cpf();
        this.votante = dadosCadastroAssociado.votante();
    }
}
