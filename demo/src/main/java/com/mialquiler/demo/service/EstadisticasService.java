package com.mialquiler.demo.service;

import com.mialquiler.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EstadisticasService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropiedadRepository propiedadRepository;
    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private PagoRepository pagoRepository;

    public long contarUsuarios() {
        return userRepository.count();
    }

    public long contarPropiedades() {
        return propiedadRepository.count();
    }

    public long contarPropiedadesDisponibles() {
        return propiedadRepository.countByEstado("DISPONIBLE");
    }

    public long contarPagosAtrasados() {
        return pagoRepository.countByEstadoFalseAndFechaPrevistaBefore(LocalDate.now());
    }

    public long contratosPorVencerEnDias(int dias) {
        LocalDate fechaLimite = LocalDate.now().plusDays(dias);
        return contratoRepository.countByFechaFinBetween(LocalDate.now(), fechaLimite);
    }

    public long propiedadesConPrecioMayorA(int precio) {
        return propiedadRepository.countByPrecioGreaterThan(precio);
    }
}