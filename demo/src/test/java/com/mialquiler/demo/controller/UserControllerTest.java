package com.mialquiler.demo.controller;

import com.mialquiler.demo.entity.Usuario;
import com.mialquiler.demo.repository.UserRepository;
import com.mialquiler.demo.service.UserService;
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
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;

    @Test
    void obtenerListaUsuarios_deberiaMostrarVista() throws Exception {
        when(userRepository.findAll()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/users"))
                .andExpect(model().attributeExists("users"));

        verify(userRepository).findAll();
    }

    @Test
    void crearUsuario_deberiaMostrarFormulario() throws Exception {
        mockMvc.perform(get("/users/crear"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/usersForm"))
                .andExpect(model().attributeExists("usuario", "esEdicion"));
    }

    @Test
    void guardarUsuario_correcto_deberiaMostrarLista() throws Exception {
        when(userService.validarUsername(any(Usuario.class))).thenReturn(true);
        when(userRepository.findAll()).thenReturn(java.util.List.of());

        mockMvc.perform(post("/users/create").flashAttr("usuario", new Usuario()))
                .andExpect(status().isOk())
                .andExpect(view().name("users/users"))
                .andExpect(model().attributeExists("mensaje"));

        verify(userService).validarUsername(any(Usuario.class));
    }

    @Test
    void guardarUsuario_usuarioExistente_deberiaRegresarFormulario() throws Exception {
        when(userService.validarUsername(any(Usuario.class))).thenReturn(false);

        mockMvc.perform(post("/users/create").flashAttr("usuario", new Usuario()))
                .andExpect(status().isOk())
                .andExpect(view().name("users/usersForm"))
                .andExpect(model().attributeExists("mensaje"));
    }

    @Test
    void mostrarFormularioEdicion_deberiaCargarUsuario() throws Exception {
        Usuario u = new Usuario();
        when(userRepository.findById("test")).thenReturn(Optional.of(u));

        mockMvc.perform(get("/users/editar/test"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/usersForm"))
                .andExpect(model().attributeExists("usuario", "esEdicion"));

        verify(userRepository).findById("test");
    }

    @Test
    void actualizar_deberiaRedirigir() throws Exception {
        mockMvc.perform(post("/users/actualizar/test").flashAttr("usuario", new Usuario()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"));

        verify(userService).actualizar(any(Usuario.class));
    }

    @Test
    void eliminar_deberiaRedirigir() throws Exception {
        mockMvc.perform(get("/users/eliminar/test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"));

        verify(userService).eliminar("test");
    }
}
