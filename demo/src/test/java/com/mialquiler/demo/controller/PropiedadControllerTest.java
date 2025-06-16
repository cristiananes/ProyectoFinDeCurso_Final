package com.mialquiler.demo.controller;

import com.mialquiler.demo.entity.Propiedad;
import com.mialquiler.demo.repository.UserRepository;
import com.mialquiler.demo.service.PropiedadService;
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
@WebMvcTest(controllers = PropiedadController.class)
@AutoConfigureMockMvc(addFilters = false)
class PropiedadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropiedadService propiedadService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void listarPropiedades_deberiaMostrarVista() throws Exception {
        when(propiedadService.listarTodas()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/propiedades/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("propiedades/propiedades"))
                .andExpect(model().attributeExists("propiedades"));

        verify(propiedadService).listarTodas();
    }

    @Test
    void guardar_deberiaRedirigir() throws Exception {
        mockMvc.perform(post("/propiedades/crear").flashAttr("propiedad", new Propiedad()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/propiedades/all"));

        verify(propiedadService).guardar(any(Propiedad.class));
    }

    @Test
    void mostrarFormularioEdicion_deberiaCargarPropiedad() throws Exception {
        Propiedad p = new Propiedad();
        when(propiedadService.buscarPorId(1L)).thenReturn(Optional.of(p));
        when(userRepository.findAll()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/propiedades/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("propiedades/propiedadForm"))
                .andExpect(model().attributeExists("propiedad", "usuarios", "esEdicion"));

        verify(propiedadService).buscarPorId(1L);
    }

    @Test
    void actualizar_deberiaRedirigir() throws Exception {
        mockMvc.perform(post("/propiedades/actualizar/1").flashAttr("propiedad", new Propiedad()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/propiedades/all"));

        verify(propiedadService).actualizar(any(Propiedad.class));
    }

    @Test
    void eliminar_deberiaRedirigir() throws Exception {
        mockMvc.perform(get("/propiedades/eliminar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/propiedades/all"));

        verify(propiedadService).eliminar(1L);
    }
}
