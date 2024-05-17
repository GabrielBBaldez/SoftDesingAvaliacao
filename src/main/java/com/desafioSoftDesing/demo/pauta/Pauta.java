package com.desafioSoftDesing.demo.pauta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pauta", schema = "public")
@Getter
@Setter
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", length = 255)
    private String titulo;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "votacao_inicio")
    private LocalDate votacaoInicio;

    @Column(name = "votacao_fim")
    private LocalDate votacaoFim;

    public Pauta(DadosCadastroPauta dadosCadastroPauta) {
        this.titulo = dadosCadastroPauta.titulo();
        this.descricao = dadosCadastroPauta.descricao();
        this.dataCriacao = LocalDate.now();
        this.votacaoInicio = LocalDate.parse(dadosCadastroPauta.votacaoInicio());
        this.votacaoFim = LocalDate.parse(dadosCadastroPauta.votacaoFim());
    }
}
