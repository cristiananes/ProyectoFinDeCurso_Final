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
public class Usuario {

    @Id
    private String username;
    private String nombre;
    //poner un metodo que cambie los nombres del rol cuando registren una propiedad y andando
    private String rol;
    private String tipoInquilino;
    private String email;
    private String contrasenia;
    private int deuda;
    private boolean eliminado;

    @ManyToOne
    private Perfil perfil;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "duenio", cascade = CascadeType.ALL)
    private List<Propiedad> propiedades;
}
