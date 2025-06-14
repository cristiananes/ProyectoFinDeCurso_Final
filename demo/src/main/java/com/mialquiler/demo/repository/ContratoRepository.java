package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    long countByFechaFinBetween(LocalDate inicio, LocalDate fin);
}
