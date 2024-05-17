package com.desafioSoftDesing.demo.RegistroVotacao;

import com.desafioSoftDesing.demo.associado.Associado;
import com.desafioSoftDesing.demo.pauta.Pauta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "registroVotacao", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistroVotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;

    @ManyToOne
    @JoinColumn(name = "associado_id", nullable = false)
    private Associado associado;

    @Column(name = "voto")
    private Boolean voto;

    @Column(name = "data_voto")
    private LocalDate dataVoto;

    public RegistroVotacao(DadosCadastroRegistroVotacao dadosCadastroRegistroVotacao, Pauta pauta, Associado associado) {
        this.pauta = pauta;
        this.associado = associado;
        this.voto = dadosCadastroRegistroVotacao.voto();
        this.dataVoto = LocalDate.now();
    }


}