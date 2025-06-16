package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Perfil;
import com.mialquiler.demo.entity.Usuario;
import com.mialquiler.demo.repository.PerfilRepository;
import com.mialquiler.demo.repository.UserRepository;
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
class PerfilServiceTest {

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PerfilService perfilService;

    private Perfil perfil;

    @BeforeEach
    void setUp() {
        perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNombre("ADMIN");
    }

    @Test
    void listarTodos_deberiaDevolverLista() {
        when(perfilRepository.findAll()).thenReturn(List.of(perfil));

        List<Perfil> resultado = perfilService.listarTodos();

        assertEquals(1, resultado.size());
        verify(perfilRepository).findAll();
    }

    @Test
    void guardar_deberiaInvocarRepositorio() {
        perfilService.guardar(perfil);
        verify(perfilRepository).save(perfil);
    }

    @Test
    void buscarPorId_existente_deberiaDevolverOptional() {
        when(perfilRepository.findById(1L)).thenReturn(Optional.of(perfil));

        Optional<Perfil> resultado = perfilService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        verify(perfilRepository).findById(1L);
    }

    @Test
    void buscarPorId_noExistente_deberiaDevolverVacio() {
        when(perfilRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Perfil> resultado = perfilService.buscarPorId(2L);

        assertTrue(resultado.isEmpty());
        verify(perfilRepository).findById(2L);
    }

    @Test
    void actualizar_cuandoExiste_deberiaGuardar() {
        when(perfilRepository.existsById(perfil.getId())).thenReturn(true);

        perfilService.actualizar(perfil);

        verify(perfilRepository).save(perfil);
    }

    @Test
    void actualizar_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(perfilRepository.existsById(perfil.getId())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> perfilService.actualizar(perfil));
    }

    @Test
    void eliminar_deberiaInvocarRepositorio() {
        perfilService.eliminar(1L);
        verify(perfilRepository).deleteById(1L);
    }

    @Test
    void asignarPerfilAUsuario_deberiaActualizarRolYGuardarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername("user");
        usuario.setContrasenia("pass");

        when(userRepository.findById("user")).thenReturn(Optional.of(usuario));
        when(perfilRepository.findById(1L)).thenReturn(Optional.of(perfil));
        when(userRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArguments()[0]);

        Usuario resultado = perfilService.asignarPerfilAUsuario("user", 1L);

        assertEquals(perfil, resultado.getPerfil());
        assertEquals("ADMIN", resultado.getRol());
        verify(userRepository).save(resultado);
    }
}
