package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindById_deberiaPersistirUsuario() {
        Usuario u = new Usuario();
        u.setUsername("test");
        userRepository.save(u);

        Usuario encontrado = userRepository.findById("test").orElse(null);
        assertNotNull(encontrado);
    }
}
