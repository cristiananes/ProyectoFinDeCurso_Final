package com.mialquiler.demo.service;

import com.mialquiler.demo.repository.ContratoRepository;
import com.mialquiler.demo.repository.PagoRepository;
import com.mialquiler.demo.repository.PropiedadRepository;
import com.mialquiler.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadisticasServiceTest {

    @Mock
    private PropiedadRepository propiedadRepository;
    @Mock
    private ContratoRepository contratoRepository;
    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EstadisticasService estadisticasService;

    @Test
    void contarPropiedadesDisponibles_deberiaConsultarRepositorio() {
        when(propiedadRepository.countByEstado("DISPONIBLE")).thenReturn(3L);

        long total = estadisticasService.contarPropiedadesDisponibles();

        assertEquals(3L, total);
        verify(propiedadRepository).countByEstado("DISPONIBLE");
    }

    @Test
    void contarPropiedadesOcupadas_deberiaConsultarRepositorio() {
        when(propiedadRepository.countByEstado("OCUPADA")).thenReturn(2L);

        long total = estadisticasService.contarPropiedadesOcupadas();

        assertEquals(2L, total);
        verify(propiedadRepository).countByEstado("OCUPADA");
    }

    @Test
    void contarContratosPorVencer30Dias_deberiaConsultarRepositorio() {
        when(contratoRepository.countByFechaFinBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(1L);

        long total = estadisticasService.contarContratosPorVencer30Dias();

        assertEquals(1L, total);
        verify(contratoRepository).countByFechaFinBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void calcularIngresosMensuales_deberiaConsultarRepositorio() {
        when(contratoRepository.sumPrecioByEstadoTrue()).thenReturn(1000.0);

        Double total = estadisticasService.calcularIngresosMensuales();

        assertEquals(1000.0, total);
        verify(contratoRepository).sumPrecioByEstadoTrue();
    }

    @Test
    void calcularIngresosMensuales_cuandoEsNull_deberiaRetornarCero() {
        when(contratoRepository.sumPrecioByEstadoTrue()).thenReturn(null);

        Double total = estadisticasService.calcularIngresosMensuales();

        assertEquals(0.0, total);
        verify(contratoRepository).sumPrecioByEstadoTrue();
    }

    @Test
    void contarContratosPorAnio_deberiaConsultarRepositorio() {
        when(contratoRepository.countByFechaInicioBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(5L);

        long total = estadisticasService.contarContratosPorAnio(2024);

        assertEquals(5L, total);
        verify(contratoRepository).countByFechaInicioBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    void contarPagosSuperioresA_deberiaConsultarRepositorio() {
        when(pagoRepository.countByCantidadEsperadaGreaterThan(100)).thenReturn(4L);

        long total = estadisticasService.contarPagosSuperioresA(100);

        assertEquals(4L, total);
        verify(pagoRepository).countByCantidadEsperadaGreaterThan(100);
    }
}
