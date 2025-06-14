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
public class Propiedad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String direccion;
	private String descripcion;
	private int precio;
	private String estado;

	@ManyToOne
	private Usuario duenio;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "propiedad", cascade = CascadeType.ALL)
	private List<PropiedadContrato> contratos;

}
