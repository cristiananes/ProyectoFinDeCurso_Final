package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Contrato;
import com.mialquiler.demo.repository.ContratoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratoServiceTest {

    @Mock
    private ContratoRepository contratoRepository;

    @Mock
    private NotificacionesService notificacionesService;

    @InjectMocks
    private ContratoService contratoService;

    private Contrato contrato;

    @BeforeEach
    void setUp() {
        contrato = new Contrato();
        contrato.setId(1L);
    }

    @Test
    void listarTodos_deberiaDevolverLista() {
        when(contratoRepository.findAll()).thenReturn(List.of(contrato));

        List<Contrato> resultado = contratoService.listarTodos();

        assertEquals(1, resultado.size());
        verify(contratoRepository).findAll();
    }

    @Test
    void guardar_deberiaInvocarRepositorio() {
        contratoService.guardar(contrato);
        verify(contratoRepository).save(contrato);
    }

    @Test
    void buscarPorId_existente_deberiaDevolverOptional() {
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));

        Optional<Contrato> resultado = contratoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        verify(contratoRepository).findById(1L);
    }

    @Test
    void buscarPorId_noExistente_deberiaDevolverVacio() {
        when(contratoRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Contrato> resultado = contratoService.buscarPorId(2L);

        assertTrue(resultado.isEmpty());
        verify(contratoRepository).findById(2L);
    }

    @Test
    void actualizar_cuandoExiste_deberiaGuardar() {
        when(contratoRepository.existsById(contrato.getId())).thenReturn(true);

        contratoService.actualizar(contrato);

        verify(contratoRepository).save(contrato);
    }

    @Test
    void actualizar_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(contratoRepository.existsById(contrato.getId())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> contratoService.actualizar(contrato));
    }

    @Test
    void eliminar_deberiaInvocarRepositorio() {
        contratoService.eliminar(1L);
        verify(contratoRepository).deleteById(1L);
    }

    @Test
    void contratosPorVencer_deberiaRetornarListaDeServicioNotificaciones() {
        when(notificacionesService.getContratosPorVencer()).thenReturn(List.of(contrato));

        List<Contrato> resultado = contratoService.contratosPorVencer();

        assertEquals(1, resultado.size());
        verify(notificacionesService).getContratosPorVencer();
    }

    @Test
    void listarContratosPorUsuario_deberiaInvocarRepositorio() {
        when(contratoRepository.findByInquilino_Username("user")).thenReturn(List.of(contrato));

        List<Contrato> resultado = contratoService.listarContratosPorUsuario("user");

        assertEquals(1, resultado.size());
        verify(contratoRepository).findByInquilino_Username("user");
    }
}
