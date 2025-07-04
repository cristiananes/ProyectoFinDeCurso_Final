package com.mialquiler.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    private String username;
    private String nombre;
    
    private String rol;
    private String tipoInquilino;
    private String email;
    private String contrasenia;
    private int deuda;
    private boolean eliminado;

    @ManyToOne
    private Perfil perfil;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "duenio", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Propiedad> propiedades;
}
