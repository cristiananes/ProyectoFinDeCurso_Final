package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    long countByFechaFinBetween(LocalDate inicio, LocalDate fin);

    
    @Query("SELECT SUM(c.precio) FROM Contrato c WHERE c.estado = true")
    Double sumPrecioByEstadoTrue();

    
    long countByFechaInicioBetween(LocalDate inicio, LocalDate fin);

    List<Contrato> findByInquilino_Username(String username);
}
