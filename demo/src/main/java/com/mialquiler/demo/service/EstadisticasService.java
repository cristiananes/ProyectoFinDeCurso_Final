package com.mialquiler.demo.service;

import com.mialquiler.demo.repository.ContratoRepository;
import com.mialquiler.demo.repository.PagoRepository;
import com.mialquiler.demo.repository.PropiedadRepository;
import com.mialquiler.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EstadisticasService {

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private UserRepository userRepository;

    
    public long contarPropiedadesDisponibles() {
        return propiedadRepository.countByEstadoIgnoreCase("DISPONIBLE");
    }

    
    public long contarPropiedadesOcupadas() {
        return propiedadRepository.countByEstadoIgnoreCase("OCUPADA");
    }

    
    public long contarContratosPorVencer30Dias() {
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusDays(30);
        return contratoRepository.countByFechaFinBetween(hoy, limite);
    }
    
    public Double calcularIngresosMensuales() {
        Double total = contratoRepository.sumPrecioByEstadoTrue();
        return total != null ? total : 0.0;
    }

    
    public long contarContratosPorAnio(int anio) {
        LocalDate inicio = LocalDate.of(anio, 1, 1);
        LocalDate fin = LocalDate.of(anio, 12, 31);
        return contratoRepository.countByFechaInicioBetween(inicio, fin);
    }
    
    public long contarPagosSuperioresA(double monto) {
        return pagoRepository.countByCantidadEsperadaGreaterThan((int)monto);
    }
}