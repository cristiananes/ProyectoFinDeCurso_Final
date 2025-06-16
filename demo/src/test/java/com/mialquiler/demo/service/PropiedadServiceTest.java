package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Propiedad;
import com.mialquiler.demo.repository.PropiedadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropiedadServiceTest {

    @Mock
    private PropiedadRepository propiedadRepository;

    @InjectMocks
    private PropiedadService propiedadService;

    private Propiedad propiedad1;

    @BeforeEach
    void setUp() {
        propiedad1 = new Propiedad();
        propiedad1.setId(1L);
        propiedad1.setDireccion("Calle Falsa 123");
        propiedad1.setPrecio(1000);
    }

    @Test
    void testGetPropiedades_ShouldReturnListOfPropiedades() {
        // --- ARRANGE ---
        // Se simula la respuesta del repositorio cuando se llame a su método findAll()
        when(propiedadRepository.findAll()).thenReturn(Arrays.asList(propiedad1));

        // --- ACT ---
        // Se llama al método getPropiedades() de tu servicio
        List<Propiedad> propiedades = propiedadService.listarTodas();

        // --- ASSERT ---
        assertNotNull(propiedades);
        assertEquals(1, propiedades.size());
        // Se verifica que el método findAll() del repositorio fue llamado una vez
        verify(propiedadRepository, times(1)).findAll();
    }

    @Test
    void testGet_WhenPropiedadExists_ShouldReturnPropiedad() {
        // --- ARRANGE ---
        // Se simula que el repositorio encuentra la propiedad
        when(propiedadRepository.findById(1L)).thenReturn(Optional.of(propiedad1));

        // --- ACT ---
        // Se llama al método get() de tu servicio
        Propiedad propiedadEncontrada = propiedadService.buscarPorId(1L).get();

        // --- ASSERT ---
        assertNotNull(propiedadEncontrada);
        assertEquals("Calle Falsa 123", propiedadEncontrada.getDireccion());
        verify(propiedadRepository).findById(1L);
    }

    @Test
    void testGet_WhenPropiedadNotExists_ShouldReturnEmptyOptional() {
        // --- ARRANGE ---
        // Se simula que el repositorio NO encuentra la propiedad
        when(propiedadRepository.findById(99L)).thenReturn(Optional.empty());

        // --- ACT ---
        Optional<Propiedad> resultado = propiedadService.buscarPorId(99L);

        // --- ASSERT ---
        assertTrue(resultado.isEmpty());
        verify(propiedadRepository).findById(99L);
    }

    @Test
    void testSave_ShouldCallRepositorySave() {
        // --- ARRANGE ---
        // No es necesario simular el `when` para métodos `void` si solo queremos verificar la llamada.

        // --- ACT ---
        propiedadService.guardar(propiedad1);

        // --- ASSERT ---
        // Se verifica que el método save() del repositorio fue llamado con el objeto propiedad1
        verify(propiedadRepository).save(propiedad1);
    }

    @Test
    void testActualizar_CuandoExiste_DeberiaGuardar() {
        when(propiedadRepository.existsById(propiedad1.getId())).thenReturn(true);

        propiedadService.actualizar(propiedad1);

        verify(propiedadRepository).save(propiedad1);
    }

    @Test
    void testActualizar_CuandoNoExiste_DeberiaLanzarExcepcion() {
        when(propiedadRepository.existsById(propiedad1.getId())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> propiedadService.actualizar(propiedad1));
    }

    @Test
    void testDelete_ShouldCallRepositoryDelete() {
        // --- ARRANGE ---
        Long idParaBorrar = 1L;
        // doNothing() se usa para métodos void. Asegura que no ocurra un error al llamar al método mockeado.
        doNothing().when(propiedadRepository).deleteById(idParaBorrar);

        // --- ACT ---
        propiedadService.eliminar(idParaBorrar);

        // --- ASSERT ---
        // Se verifica que el método deleteById() del repositorio fue llamado con el id correcto
        verify(propiedadRepository).deleteById(idParaBorrar);
    }
}
