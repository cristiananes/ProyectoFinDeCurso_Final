package com.mialquiler.demo.controller;

import com.mialquiler.demo.entity.Pago;
import com.mialquiler.demo.repository.ContratoRepository;
import com.mialquiler.demo.service.PagoService;
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
@WebMvcTest(controllers = PagoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;
    @MockBean
    private ContratoRepository contratoRepository;

    @Test
    void listarPagos_deberiaMostrarVista() throws Exception {
        when(pagoService.listarTodos()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/pagos/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("pagos/pagos"))
                .andExpect(model().attributeExists("pagos"));

        verify(pagoService).listarTodos();
    }

    @Test
    void guardar_deberiaRedirigir() throws Exception {
        mockMvc.perform(post("/pagos/crear").flashAttr("pago", new Pago()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pagos/all"));

        verify(pagoService).guardar(any(Pago.class));
    }

    @Test
    void mostrarFormularioEdicion_deberiaCargarPago() throws Exception {
        Pago p = new Pago();
        when(pagoService.buscarPorId(1L)).thenReturn(Optional.of(p));
        when(contratoRepository.findAll()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/pagos/editar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("pagos/pagoForm"))
                .andExpect(model().attributeExists("pago", "contratos", "esEdicion"));

        verify(pagoService).buscarPorId(1L);
    }

    @Test
    void actualizar_deberiaRedirigir() throws Exception {
        mockMvc.perform(post("/pagos/actualizar/1").flashAttr("pago", new Pago()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pagos/all"));

        verify(pagoService).actualizar(any(Pago.class));
    }

    @Test
    void eliminar_deberiaRedirigir() throws Exception {
        mockMvc.perform(get("/pagos/eliminar/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pagos/all"));

        verify(pagoService).eliminar(1L);
    }
}
