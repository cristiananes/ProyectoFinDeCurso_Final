package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.PropiedadContrato;
import com.mialquiler.demo.entity.PropiedadContratoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropiedadContratoRepository extends JpaRepository<PropiedadContrato, PropiedadContratoId> {
}