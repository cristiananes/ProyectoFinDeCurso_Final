package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
