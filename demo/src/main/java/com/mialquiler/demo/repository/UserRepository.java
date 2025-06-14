package com.mialquiler.demo.repository;

import com.mialquiler.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, String> {
}
