package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Contrato;
import com.mialquiler.demo.entity.Propiedad;
import com.mialquiler.demo.entity.PropiedadContrato;
import com.mialquiler.demo.entity.PropiedadContratoId;
import com.mialquiler.demo.repository.ContratoRepository;
import com.mialquiler.demo.repository.PropiedadContratoRepository;
import com.mialquiler.demo.repository.PropiedadRepository;
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
class PropiedadContratoServiceTest {

    @Mock
    private PropiedadContratoRepository propiedadContratoRepository;

    @Mock
    private PropiedadRepository propiedadRepository;

    @Mock
    private ContratoRepository contratoRepository;

    @InjectMocks
    private PropiedadContratoService propiedadContratoService;

    private Propiedad propiedad;
    private Contrato contrato;
    private PropiedadContrato pc;

    @BeforeEach
    void setUp() {
        propiedad = new Propiedad();
        propiedad.setId(1L);
        contrato = new Contrato();
        contrato.setId(2L);

        PropiedadContratoId id = new PropiedadContratoId();
        id.setId_propiedad(1L);
        id.setId_contrato(2L);

        pc = new PropiedadContrato();
        pc.setId(id);
        pc.setPropiedad(propiedad);
        pc.setContrato_propiedad(contrato);
        pc.setEstado("ACTIVO");
    }

    @Test
    void listarTodas_deberiaDevolverLista() {
        when(propiedadContratoRepository.findAll()).thenReturn(List.of(pc));

        List<PropiedadContrato> resultado = propiedadContratoService.listarTodas();

        assertEquals(1, resultado.size());
        verify(propiedadContratoRepository).findAll();
    }

    @Test
    void crear_deberiaGuardarAsignacion() {
        when(propiedadRepository.findById(1L)).thenReturn(Optional.of(propiedad));
        when(contratoRepository.findById(2L)).thenReturn(Optional.of(contrato));

        propiedadContratoService.crear(1L, 2L, "ACTIVO");

        verify(propiedadContratoRepository).save(any(PropiedadContrato.class));
    }

    @Test
    void buscarPorId_deberiaUsarRepositorio() {
        PropiedadContratoId id = new PropiedadContratoId();
        id.setId_propiedad(1L);
        id.setId_contrato(2L);
        when(propiedadContratoRepository.findById(id)).thenReturn(Optional.of(pc));

        Optional<PropiedadContrato> resultado = propiedadContratoService.buscarPorId(1L,2L);

        assertTrue(resultado.isPresent());
        verify(propiedadContratoRepository).findById(id);
    }

    @Test
    void actualizar_deberiaCambiarEstado() {
        PropiedadContratoId id = pc.getId();
        when(propiedadContratoRepository.findById(id)).thenReturn(Optional.of(pc));

        propiedadContratoService.actualizar(1L,2L,"INACTIVO");

        assertEquals("INACTIVO", pc.getEstado());
        verify(propiedadContratoRepository).save(pc);
    }

    @Test
    void eliminar_deberiaInvocarRepositorio() {
        PropiedadContratoId id = new PropiedadContratoId();
        id.setId_propiedad(1L);
        id.setId_contrato(2L);

        propiedadContratoService.eliminar(1L,2L);

        verify(propiedadContratoRepository).deleteById(id);
    }
}
