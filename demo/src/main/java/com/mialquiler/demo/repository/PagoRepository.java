package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    long countByEstadoFalseAndFechaPrevistaBefore(LocalDate fecha);
}
