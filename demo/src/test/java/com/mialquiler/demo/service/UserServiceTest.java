package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Usuario;
import com.mialquiler.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setUsername("user");
        usuario.setContrasenia("pass");
    }

    @Test
    void cambiarRolToPropietario_deberiaGuardarUsuario() {
        Usuario result = userService.cambiarRolToPropietario(usuario);
        assertEquals("PROPIETARIO", result.getRol());
        verify(userRepository).save(usuario);
    }

    @Test
    void cambiarRolToInquilino_deberiaGuardarUsuario() {
        Usuario result = userService.cambiarRolToInquilino(usuario);
        assertEquals("INQUILINO", result.getRol());
        verify(userRepository).save(usuario);
    }

    @Test
    void validarUsername_cuandoNoExiste_deberiaGuardar() {
        when(userRepository.findById("user")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass")).thenReturn("hash");

        boolean valido = userService.validarUsername(usuario);

        assertTrue(valido);
        assertEquals("hash", usuario.getContrasenia());
        verify(userRepository).save(usuario);
    }

    @Test
    void validarUsername_cuandoExiste_deberiaRetornarFalse() {
        when(userRepository.findById("user")).thenReturn(Optional.of(new Usuario()));

        boolean valido = userService.validarUsername(usuario);

        assertFalse(valido);
        verify(userRepository, never()).save(any());
    }

    @Test
    void actualizar_conNuevaContrasena_deberiaCodificarYGuardar() {
        when(userRepository.existsById("user")).thenReturn(true);
        when(passwordEncoder.encode("pass")).thenReturn("hash");

        userService.actualizar(usuario);

        assertEquals("hash", usuario.getContrasenia());
        verify(userRepository).save(usuario);
    }

    @Test
    void actualizar_sinNuevaContrasena_deberiaMantenerAnterior() {
        usuario.setContrasenia("");
        Usuario existente = new Usuario();
        existente.setUsername("user");
        existente.setContrasenia("old");

        when(userRepository.existsById("user")).thenReturn(true);
        when(userRepository.findById("user")).thenReturn(Optional.of(existente));

        userService.actualizar(usuario);

        assertEquals("old", usuario.getContrasenia());
        verify(userRepository).save(usuario);
    }

    @Test
    void actualizar_usuarioNoExiste_deberiaLanzarExcepcion() {
        when(userRepository.existsById("user")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.actualizar(usuario));
    }

    @Test
    void eliminar_cuandoExiste_deberiaMarcarEliminado() {
        when(userRepository.findById("user")).thenReturn(Optional.of(usuario));

        userService.eliminar("user");

        assertTrue(usuario.isEliminado());
        verify(userRepository).save(usuario);
    }

    @Test
    void eliminar_usuarioNoExiste_deberiaLanzarExcepcion() {
        when(userRepository.findById("user")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.eliminar("user"));
    }

    @Test
    void contarUsuarios_deberiaRetornarDelRepositorio() {
        when(userRepository.count()).thenReturn(5L);

        long total = userService.contarUsuarios();

        assertEquals(5L, total);
        verify(userRepository).count();
    }
}
