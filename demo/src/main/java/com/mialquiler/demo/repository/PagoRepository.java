package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    
    long countByCantidadEsperadaGreaterThan(int monto);
}