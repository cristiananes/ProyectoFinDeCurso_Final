package com.mialquiler.demo.controller;

import com.mialquiler.demo.entity.PropiedadContrato;
import com.mialquiler.demo.repository.ContratoRepository;
import com.mialquiler.demo.repository.PropiedadRepository;
import com.mialquiler.demo.service.PropiedadContratoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PropiedadContratoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PropiedadContratoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropiedadContratoService propiedadContratoService;
    @MockBean
    private PropiedadRepository propiedadRepository;
    @MockBean
    private ContratoRepository contratoRepository;

    @Test
    void listar_deberiaMostrarVista() throws Exception {
        when(propiedadContratoService.listarTodas()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/propiedad-contrato/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("propiedadContrato/lista"))
                .andExpect(model().attributeExists("asignaciones"));

        verify(propiedadContratoService).listarTodas();
    }

    @Test
    void guardar_deberiaRedirigir() throws Exception {
        mockMvc.perform(post("/propiedad-contrato/crear")
                        .param("idPropiedad", "1")
                        .param("idContrato", "2")
                        .param("estado", "ACTIVO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/propiedad-contrato/all"));

        verify(propiedadContratoService).crear(1L, 2L, "ACTIVO");
    }

    @Test
    void mostrarFormularioEdicion_deberiaCargarAsignacion() throws Exception {
        PropiedadContrato pc = new PropiedadContrato();
        when(propiedadContratoService.buscarPorId(1L, 2L)).thenReturn(Optional.of(pc));
        when(propiedadRepository.findAll()).thenReturn(java.util.List.of());
        when(contratoRepository.findAll()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/propiedad-contrato/editar/1/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("propiedadContrato/formulario"))
                .andExpect(model().attributeExists("asignacion", "propiedades", "contratos", "esEdicion"));

        verify(propiedadContratoService).buscarPorId(1L, 2L);
    }

    @Test
    void actualizar_deberiaRedirigir() throws Exception {
        mockMvc.perform(post("/propiedad-contrato/actualizar/1/2")
                        .param("estado", "INACTIVO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/propiedad-contrato/all"));

        verify(propiedadContratoService).actualizar(1L, 2L, "INACTIVO");
    }

    @Test
    void eliminar_deberiaRedirigir() throws Exception {
        mockMvc.perform(get("/propiedad-contrato/eliminar/1/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/propiedad-contrato/all"));

        verify(propiedadContratoService).eliminar(1L, 2L);
    }
}
