package com.mialquiler.demo.controller;

import com.mialquiler.demo.entity.Contrato;
import com.mialquiler.demo.repository.UserRepository;
import com.mialquiler.demo.service.ContratoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ContratoController.class)
@AutoConfigureMockMvc(addFilters = false)
class ContratoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContratoService contratoService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void listarContratos_deberiaMostrarVista() throws Exception {
        when(contratoService.listarTodos()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/contratos/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("contratos/contratos"))
                .andExpect(model().attributeExists("contratos"));

        verify(contratoService).listarTodos();
    }

    @Test
    void guardar_deberiaRedirigir() throws Exception {
        mockMvc.perform(post("/contratos/crear").flashAttr("contrato", new Contrato()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contratos/all"));

        verify(contratoService).guardar(any(Contrato.class));
    }

    @Test
    void mostrarFormularioEdicion_deberiaCargarContrato() throws Exception {
        Contrato c = new Contrato();
        when(contratoService.buscarPorId(1L)).thenReturn(Optional.of(c));
        when(userRepository.findAll()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/contratos/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("contratos/contratoForm"))
                .andExpect(model().attributeExists("contrato", "usuarios", "esEdicion"));

        verify(contratoService).buscarPorId(1L);
    }

    @Test
    void actualizar_deberiaRedirigir() throws Exception {
        mockMvc.perform(post("/contratos/actualizar/1").flashAttr("contrato", new Contrato()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contratos/all"));

        verify(contratoService).actualizar(any(Contrato.class));
    }

    @Test
    void eliminar_deberiaRedirigir() throws Exception {
        mockMvc.perform(get("/contratos/eliminar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contratos/all"));

        verify(contratoService).eliminar(1L);
    }
}
