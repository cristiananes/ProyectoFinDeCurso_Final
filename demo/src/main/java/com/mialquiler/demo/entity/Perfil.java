package com.mialquiler.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	private String descripcion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy ="perfil", cascade = CascadeType.ALL)
	private List<Usuario> usuarios;
}

