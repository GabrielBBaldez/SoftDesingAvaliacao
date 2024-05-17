package com.desafioSoftDesing.demo;

import com.desafioSoftDesing.demo.pauta.Pauta;
import com.desafioSoftDesing.demo.pauta.PautaRepository;
import com.desafioSoftDesing.demo.pauta.PautaService;
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

public class PautaServiceTest {

    @InjectMocks
    private PautaService pautaService;

    @Mock
    private PautaRepository pautaRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarPautaPorId() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        ResponseEntity<?> response = pautaService.buscarPautaPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(Pauta.class, response.getBody());
        assertEquals(1L, ((Pauta) response.getBody()).getId());
    }

    @Test
    public void testBuscarPautaPorIdPautaNaoEncontrada() {

        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = pautaService.buscarPautaPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Pauta não encontrada", response.getBody());
    }
}