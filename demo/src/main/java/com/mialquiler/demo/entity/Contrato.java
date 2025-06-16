package com.mialquiler.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contrato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private double precio;
	private boolean estado;

	@ManyToOne
	@JoinColumn(name = "inquilino_username")
	private Usuario inquilino;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato_propiedad", cascade = CascadeType.ALL)
	private List<PropiedadContrato> propiedadContratos;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Pago> pagos;


}