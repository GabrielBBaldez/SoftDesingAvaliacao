package com.desafioSoftDesing.demo;


import com.desafioSoftDesing.demo.associado.Associado;
import com.desafioSoftDesing.demo.associado.AssociadoRepository;
import com.desafioSoftDesing.demo.associado.AssociadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AssociadoServiceTest {

    @InjectMocks
    private AssociadoService associadoService;

    @Mock
    private AssociadoRepository associadoRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarAssociadoPorCpf() {

        Associado associado = new Associado();
        associado.setCpf("12345678901");
        associado.setAtivo(true);

        when(associadoRepository.findByCpf("12345678901")).thenReturn(Optional.of(associado));

        ResponseEntity<?> response = associadoService.buscarAssociadoPorCpf("12345678901");

        assertSame(response.getStatusCode(), HttpStatus.OK);
        assertInstanceOf(Associado.class, response.getBody());
        assertEquals("12345678901", ((Associado) response.getBody()).getCpf());
    }

    @Test
    public void testBuscarAssociadoPorCpfAssociadoNaoEncontrado() {

        when(associadoRepository.findByCpf("12345678901")).thenReturn(Optional.empty());

        ResponseEntity<?> response = associadoService.buscarAssociadoPorCpf("12345678901");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Associado não encontrado", response.getBody());
    }

}