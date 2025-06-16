package com.mialquiler.demo.controller;

import com.mialquiler.demo.service.ContratoService;
import com.mialquiler.demo.service.PagoService;
import com.mialquiler.demo.service.PropiedadService;
import com.mialquiler.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MainController.class)
@AutoConfigureMockMvc(addFilters = false)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private PropiedadService propiedadService;
    @MockBean
    private ContratoService contratoService;
    @MockBean
    private PagoService pagoService;

    @Test
    void dashboard_deberiaCargarDatosYRetornarVista() throws Exception {
        when(userService.contarUsuarios()).thenReturn(1L);
        when(propiedadService.listarTodas()).thenReturn(java.util.List.of());
        when(contratoService.listarTodos()).thenReturn(java.util.List.of());
        when(pagoService.listarTodos()).thenReturn(java.util.List.of());
        when(contratoService.contratosPorVencer()).thenReturn(java.util.List.of());
        when(pagoService.pagosAtrasados()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("totalUsuarios", "totalPropiedades",
                        "totalContratos", "totalPagos", "contratosPorVencer", "pagosAtrasados"));

        verify(userService).contarUsuarios();
        verify(propiedadService).listarTodas();
        verify(contratoService).listarTodos();
        verify(pagoService).listarTodos();
    }

    @Test
    void dashboardAlternativo_deberiaDelegarADashboard() throws Exception {
        when(userService.contarUsuarios()).thenReturn(0L);
        when(propiedadService.listarTodas()).thenReturn(java.util.List.of());
        when(contratoService.listarTodos()).thenReturn(java.util.List.of());
        when(pagoService.listarTodos()).thenReturn(java.util.List.of());
        when(contratoService.contratosPorVencer()).thenReturn(java.util.List.of());
        when(pagoService.pagosAtrasados()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        verify(userService).contarUsuarios();
    }
}
