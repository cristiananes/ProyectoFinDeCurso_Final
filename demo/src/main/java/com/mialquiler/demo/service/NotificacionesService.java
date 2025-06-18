package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Contrato;
import com.mialquiler.demo.entity.Pago;
import com.mialquiler.demo.repository.ContratoRepository;
import com.mialquiler.demo.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit; 
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionesService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    
    public List<Contrato> getContratosPorVencer() {
        LocalDate fechaLimite = LocalDate.now().plusDays(30);
        return contratoRepository.findAll().stream()
                .filter(contrato -> contrato.getFechaFin() != null)
                .filter(contrato -> contrato.getFechaFin().isBefore(fechaLimite))
                .filter(contrato -> contrato.getFechaFin().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }

    
    public List<Pago> getPagosAtrasados() {
        LocalDate hoy = LocalDate.now();
        return pagoRepository.findAll().stream()
                .filter(pago -> !pago.isEstado()) 
                .filter(pago -> pago.getFechaPrevista() != null)
                .filter(pago -> pago.getFechaPrevista().isBefore(hoy))
                .collect(Collectors.toList());
    }

    
    public List<Pago> getPagosPorVencer() {
        LocalDate fechaLimite = LocalDate.now().plusDays(7);
        return pagoRepository.findAll().stream()
                .filter(pago -> !pago.isEstado()) 
                .filter(pago -> pago.getFechaPrevista() != null)
                .filter(pago -> pago.getFechaPrevista().isBefore(fechaLimite))
                .filter(pago -> pago.getFechaPrevista().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }

    
    public int calcularRetraso(Pago pago) {
        if (pago.getFechaPrevista() == null) return 0;

        LocalDate fechaComparacion = pago.getFechaReal() != null ?
                pago.getFechaReal() : LocalDate.now();

        if (fechaComparacion.isAfter(pago.getFechaPrevista())) {
            
            return (int) ChronoUnit.DAYS.between(pago.getFechaPrevista(), fechaComparacion);
        }
        return 0;
    }
}