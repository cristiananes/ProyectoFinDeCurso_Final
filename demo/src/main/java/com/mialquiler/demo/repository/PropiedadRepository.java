package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {
    long countByEstado(String estado);
    long countByPrecioGreaterThan(int precio);
}
