package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PropiedadContratoRepositoryTest {

    @Autowired
    private PropiedadContratoRepository propiedadContratoRepository;
    @Autowired
    private PropiedadRepository propiedadRepository;
    @Autowired
    private ContratoRepository contratoRepository;

    @Test
    void saveAndFindById_deberiaPersistirAsignacion() {
        Propiedad propiedad = new Propiedad();
        propiedadRepository.save(propiedad);

        Contrato contrato = new Contrato();
        contratoRepository.save(contrato);

        PropiedadContrato pc = new PropiedadContrato();
        PropiedadContratoId id = new PropiedadContratoId();
        id.setId_propiedad(propiedad.getId());
        id.setId_contrato(contrato.getId());
        pc.setId(id);
        pc.setPropiedad(propiedad);
        pc.setContrato_propiedad(contrato);
        pc.setEstado("ACTIVO");
        propiedadContratoRepository.save(pc);

        PropiedadContrato encontrado = propiedadContratoRepository.findById(id).orElse(null);
        assertNotNull(encontrado);
        assertEquals("ACTIVO", encontrado.getEstado());
    }
}
