package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Pago;
import com.mialquiler.demo.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private NotificacionesService notificacionesService;

    @InjectMocks
    private PagoService pagoService;

    private Pago pago;

    @BeforeEach
    void setUp() {
        pago = new Pago();
        pago.setId(1L);
        pago.setFechaPrevista(LocalDate.now().minusDays(2));
        pago.setEstado(false);
    }

    @Test
    void listarTodos_deberiaCalcularRetraso() {
        when(pagoRepository.findAll()).thenReturn(List.of(pago));
        when(notificacionesService.calcularRetraso(pago)).thenReturn(2);

        List<Pago> resultado = pagoService.listarTodos();

        assertEquals(2, resultado.get(0).getRetraso());
        verify(pagoRepository).findAll();
    }

    @Test
    void guardar_deberiaCalcularRetrasoYGuardar() {
        when(notificacionesService.calcularRetraso(pago)).thenReturn(3);

        pagoService.guardar(pago);

        assertEquals(3, pago.getRetraso());
        verify(pagoRepository).save(pago);
    }

    @Test
    void buscarPorId_existente_deberiaDevolverOptional() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

        Optional<Pago> resultado = pagoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        verify(pagoRepository).findById(1L);
    }

    @Test
    void buscarPorId_noExistente_deberiaDevolverVacio() {
        when(pagoRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Pago> resultado = pagoService.buscarPorId(2L);

        assertTrue(resultado.isEmpty());
        verify(pagoRepository).findById(2L);
    }

    @Test
    void actualizar_cuandoExiste_deberiaGuardar() {
        when(pagoRepository.existsById(pago.getId())).thenReturn(true);
        when(notificacionesService.calcularRetraso(pago)).thenReturn(1);

        pagoService.actualizar(pago);

        verify(pagoRepository).save(pago);
        assertEquals(1, pago.getRetraso());
    }

    @Test
    void actualizar_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(pagoRepository.existsById(pago.getId())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> pagoService.actualizar(pago));
    }

    @Test
    void eliminar_deberiaInvocarRepositorio() {
        pagoService.eliminar(1L);
        verify(pagoRepository).deleteById(1L);
    }

    @Test
    void pagosAtrasados_deberiaRetornarListaDeServicioNotificaciones() {
        when(notificacionesService.getPagosAtrasados()).thenReturn(List.of(pago));

        List<Pago> resultado = pagoService.pagosAtrasados();

        assertEquals(1, resultado.size());
        verify(notificacionesService).getPagosAtrasados();
    }
}
