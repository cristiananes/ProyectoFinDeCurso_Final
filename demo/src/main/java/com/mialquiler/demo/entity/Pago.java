package com.mialquiler.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pago {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	
	private LocalDate fechaPrevista;
	private LocalDate fechaReal;
	private int cantidadEsperada;
	private int cantidadAbonada;
	private int retraso;
	private boolean estado;

	@ManyToOne
	@JoinColumn(name = "contrato_id")
	private Contrato contrato;
	

	
	
	
}
