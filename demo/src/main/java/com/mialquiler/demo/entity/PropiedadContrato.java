package com.mialquiler.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropiedadContrato {

    @EmbeddedId
    private PropiedadContratoId id;

    private String estado;


    @ManyToOne
    @MapsId("id_propiedad")
    private Propiedad propiedad;

    @ManyToOne
    @MapsId("id_contrato")
    private Contrato contrato_propiedad;
}
