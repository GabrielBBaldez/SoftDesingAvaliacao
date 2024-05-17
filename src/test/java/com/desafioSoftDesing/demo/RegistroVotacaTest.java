package com.desafioSoftDesing.demo;

import com.desafioSoftDesing.demo.RegistroVotacao.DadosCadastroRegistroVotacao;
import com.desafioSoftDesing.demo.RegistroVotacao.RegistroVotacao;
import com.desafioSoftDesing.demo.RegistroVotacao.RegistroVotacaoRepository;
import com.desafioSoftDesing.demo.RegistroVotacao.RegistroVotacaoService;
import com.desafioSoftDesing.demo.associado.Associado;
import com.desafioSoftDesing.demo.associado.AssociadoRepository;
import com.desafioSoftDesing.demo.pauta.Pauta;
import com.desafioSoftDesing.demo.pauta.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegistroVotacaTest {

    @Mock
    private RegistroVotacaoRepository registroVotacaoRepository;

    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private RegistroVotacaoService registroVotacaoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarRegistroVotacao_Sucesso() {

        DadosCadastroRegistroVotacao dadosCadastro = new DadosCadastroRegistroVotacao(1L, 1L, true, "SomeString");

        Associado associado = new Associado();
        associado.setId(1L);
        associado.setAtivo(true);

        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setVotacaoInicio(LocalDate.now().minusDays(1));
        pauta.setVotacaoFim(LocalDate.now().plusDays(1));

        when(associadoRepository.findById(anyLong())).thenReturn(Optional.of(associado));
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.of(pauta));
        when(registroVotacaoRepository.save(any(RegistroVotacao.class))).thenReturn(new RegistroVotacao());

        ResponseEntity<?> response = registroVotacaoService.cadastrarRegistroVotacao(dadosCadastro);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(registroVotacaoRepository, times(1)).save(any(RegistroVotacao.class));
    }
}