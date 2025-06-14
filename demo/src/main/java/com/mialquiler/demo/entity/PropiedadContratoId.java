package com.mialquiler.demo.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

@Embeddable
@Data
public class PropiedadContratoId {

    private Long id_propiedad;
    private Long id_contrato;

}
