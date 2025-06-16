package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Perfil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PerfilRepositoryTest {

    @Autowired
    private PerfilRepository perfilRepository;

    @Test
    void saveAndFindAll_deberiaPersistirPerfil() {
        Perfil p = new Perfil();
        p.setId(1L);
        perfilRepository.save(p);

        assertEquals(1, perfilRepository.findAll().size());
    }
}
