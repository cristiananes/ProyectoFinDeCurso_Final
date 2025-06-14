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

    // 1. Contar propiedades disponibles
    public long contarPropiedadesDisponibles() {
        return propiedadRepository.countByEstado("DISPONIBLE");
    }

    // 2. Contar propiedades ocupadas
    public long contarPropiedadesOcupadas() {
        return propiedadRepository.countByEstado("OCUPADA");
    }

    // 3. Contratos que vencen en los próximos 30 días
    public long contarContratosPorVencer30Dias() {
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusDays(30);
        return contratoRepository.countByFechaFinBetween(hoy, limite);
    }
/*
    // 4. Calcular ingresos mensuales totales
    public Double calcularIngresosMensuales() {
        return contratoRepository.sumPrecioByEstadoTrue();
    }

    // 5. PERSONALIZABLE: Contar contratos por año
    public long contarContratosPorAnio(int anio) {
        LocalDate inicio = LocalDate.of(anio, 1, 1);
        LocalDate fin = LocalDate.of(anio, 12, 31);
        return contratoRepository.countByFechaInicioBetween(inicio, fin);
    }
*/
    // 6. PERSONALIZABLE: Contar pagos superiores a un monto
    public long contarPagosSuperioresA(double monto) {
        return pagoRepository.countByCantidadEsperadaGreaterThan((int)monto);
    }
}