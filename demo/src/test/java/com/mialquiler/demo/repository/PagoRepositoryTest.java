package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Pago;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PagoRepositoryTest {

    @Autowired
    private PagoRepository pagoRepository;

    @Test
    void countByCantidadEsperadaGreaterThan_deberiaRetornarCorrecto() {
        Pago p1 = new Pago();
        p1.setCantidadEsperada(50);
        pagoRepository.save(p1);

        Pago p2 = new Pago();
        p2.setCantidadEsperada(200);
        pagoRepository.save(p2);

        long total = pagoRepository.countByCantidadEsperadaGreaterThan(100);
        assertEquals(1L, total);
    }
}
