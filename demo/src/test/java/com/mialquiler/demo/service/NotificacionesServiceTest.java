package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Contrato;
import com.mialquiler.demo.entity.Pago;
import com.mialquiler.demo.repository.ContratoRepository;
import com.mialquiler.demo.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionesServiceTest {

    @Mock
    private ContratoRepository contratoRepository;
    @Mock
    private PagoRepository pagoRepository;
    @InjectMocks
    private NotificacionesService notificacionesService;

    private Contrato contratoVigente;
    private Contrato contratoFueraRango;
    private Pago pagoAtrasado;
    private Pago pagoPorVencer;

    @BeforeEach
    void setUp() {
        contratoVigente = new Contrato();
        contratoVigente.setFechaFin(LocalDate.now().plusDays(10));
        contratoFueraRango = new Contrato();
        contratoFueraRango.setFechaFin(LocalDate.now().plusDays(40));

        pagoAtrasado = new Pago();
        pagoAtrasado.setEstado(false);
        pagoAtrasado.setFechaPrevista(LocalDate.now().minusDays(5));

        pagoPorVencer = new Pago();
        pagoPorVencer.setEstado(false);
        pagoPorVencer.setFechaPrevista(LocalDate.now().plusDays(3));
    }

    @Test
    void getContratosPorVencer_deberiaFiltrarCorrectamente() {
        when(contratoRepository.findAll()).thenReturn(List.of(contratoVigente, contratoFueraRango));

        List<Contrato> resultado = notificacionesService.getContratosPorVencer();

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(contratoVigente));
    }

    @Test
    void getPagosAtrasados_deberiaFiltrarCorrectamente() {
        when(pagoRepository.findAll()).thenReturn(List.of(pagoAtrasado, pagoPorVencer));

        List<Pago> resultado = notificacionesService.getPagosAtrasados();

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(pagoAtrasado));
    }

    @Test
    void getPagosPorVencer_deberiaFiltrarCorrectamente() {
        when(pagoRepository.findAll()).thenReturn(List.of(pagoAtrasado, pagoPorVencer));

        List<Pago> resultado = notificacionesService.getPagosPorVencer();

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(pagoPorVencer));
    }

    @Test
    void calcularRetraso_deberiaCalcularDias() {
        Pago pago = new Pago();
        pago.setFechaPrevista(LocalDate.now().minusDays(3));
        pago.setFechaReal(LocalDate.now());

        int dias = notificacionesService.calcularRetraso(pago);

        assertEquals(3, dias);
    }
}
