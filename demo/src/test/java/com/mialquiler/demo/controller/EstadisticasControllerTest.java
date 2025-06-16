package com.mialquiler.demo.controller;

import com.mialquiler.demo.service.EstadisticasService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EstadisticasController.class)
@AutoConfigureMockMvc(addFilters = false)
class EstadisticasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstadisticasService estadisticasService;

    @Test
    void mostrarEstadisticas_sinParametros_deberiaCargarDatosBasicos() throws Exception {
        when(estadisticasService.contarPropiedadesDisponibles()).thenReturn(1L);
        when(estadisticasService.contarPropiedadesOcupadas()).thenReturn(2L);
        when(estadisticasService.contarContratosPorVencer30Dias()).thenReturn(0L);
        when(estadisticasService.calcularIngresosMensuales()).thenReturn(1000.0);

        mockMvc.perform(get("/estadisticas"))
                .andExpect(status().isOk())
                .andExpect(view().name("estadisticas/estadisticas"))
                .andExpect(model().attributeExists("propiedadesDisponibles", "propiedadesOcupadas",
                        "contratosPorVencer30Dias", "totalIngresosMensuales"));

        verify(estadisticasService).contarPropiedadesDisponibles();
        verify(estadisticasService).contarPropiedadesOcupadas();
        verify(estadisticasService).contarContratosPorVencer30Dias();
        verify(estadisticasService).calcularIngresosMensuales();
    }

    @Test
    void mostrarEstadisticas_conParametros_deberiaConsultarFiltros() throws Exception {
        when(estadisticasService.contarPropiedadesDisponibles()).thenReturn(0L);
        when(estadisticasService.contarPropiedadesOcupadas()).thenReturn(0L);
        when(estadisticasService.contarContratosPorVencer30Dias()).thenReturn(0L);
        when(estadisticasService.calcularIngresosMensuales()).thenReturn(0.0);
        when(estadisticasService.contarContratosPorAnio(anyInt())).thenReturn(5L);
        when(estadisticasService.contarPagosSuperioresA(anyDouble())).thenReturn(3L);

        mockMvc.perform(get("/estadisticas").param("anio", "2024").param("montoMinimo", "100"))
                .andExpect(status().isOk())
                .andExpect(view().name("estadisticas/estadisticas"));

        verify(estadisticasService).contarContratosPorAnio(2024);
        verify(estadisticasService).contarPagosSuperioresA(100.0);
    }
}
