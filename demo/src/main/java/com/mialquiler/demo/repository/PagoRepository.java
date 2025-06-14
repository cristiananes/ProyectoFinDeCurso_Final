package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    // Contar pagos con cantidad esperada mayor a un monto
    long countByCantidadEsperadaGreaterThan(int monto);
}