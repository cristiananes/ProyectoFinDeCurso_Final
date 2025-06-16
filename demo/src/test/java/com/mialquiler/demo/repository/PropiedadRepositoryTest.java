package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Propiedad;
import com.mialquiler.demo.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PropiedadRepositoryTest {

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void countByEstado_deberiaRetornarCantidadCorrecta() {
        Usuario u = new Usuario();
        u.setUsername("u1");
        userRepository.save(u);

        Propiedad p1 = new Propiedad();
        p1.setDireccion("d1");
        p1.setEstado("DISPONIBLE");
        p1.setDuenio(u);
        propiedadRepository.save(p1);

        Propiedad p2 = new Propiedad();
        p2.setDireccion("d2");
        p2.setEstado("OCUPADA");
        p2.setDuenio(u);
        propiedadRepository.save(p2);

        long total = propiedadRepository.countByEstadoIgnoreCase("DISPONIBLE");
        assertEquals(1L, total);
    }

    @Test
    void countByPrecioGreaterThan_deberiaRetornarCorrecto() {
        Propiedad p1 = new Propiedad();
        p1.setPrecio(500);
        propiedadRepository.save(p1);

        Propiedad p2 = new Propiedad();
        p2.setPrecio(2000);
        propiedadRepository.save(p2);

        long total = propiedadRepository.countByPrecioGreaterThan(1500);
        assertEquals(1L, total);
    }
}
