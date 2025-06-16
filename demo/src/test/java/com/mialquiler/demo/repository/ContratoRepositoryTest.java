package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Contrato;
import com.mialquiler.demo.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ContratoRepositoryTest {

    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private UserRepository userRepository;

    private Usuario inquilino;

    @BeforeEach
    void setUp() {
        inquilino = new Usuario();
        inquilino.setUsername("user");
        userRepository.save(inquilino);
    }

    @Test
    void countByFechaFinBetween_deberiaRetornarCantidad() {
        Contrato c = new Contrato();
        c.setInquilino(inquilino);
        c.setFechaFin(LocalDate.now().plusDays(5));
        contratoRepository.save(c);

        long total = contratoRepository.countByFechaFinBetween(LocalDate.now(), LocalDate.now().plusDays(10));
        assertEquals(1L, total);
    }

    @Test
    void sumPrecioByEstadoTrue_deberiaRetornarSuma() {
        Contrato c1 = new Contrato();
        c1.setEstado(true);
        c1.setPrecio(100.0);
        contratoRepository.save(c1);

        Contrato c2 = new Contrato();
        c2.setEstado(true);
        c2.setPrecio(200.0);
        contratoRepository.save(c2);

        Double suma = contratoRepository.sumPrecioByEstadoTrue();
        assertEquals(300.0, suma);
    }

    @Test
    void countByFechaInicioBetween_deberiaRetornarCantidad() {
        Contrato c = new Contrato();
        c.setFechaInicio(LocalDate.of(2024,1,1));
        contratoRepository.save(c);

        long total = contratoRepository.countByFechaInicioBetween(LocalDate.of(2023,12,1), LocalDate.of(2024,12,31));
        assertEquals(1L, total);
    }

    @Test
    void findByInquilino_Username_deberiaRetornarContratos() {
        Contrato c = new Contrato();
        c.setInquilino(inquilino);
        contratoRepository.save(c);

        List<Contrato> contratos = contratoRepository.findByInquilino_Username("user");
        assertEquals(1, contratos.size());
    }
}
