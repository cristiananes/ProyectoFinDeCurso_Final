package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Pago;
import com.mialquiler.demo.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private NotificacionesService notificacionesService;

    public List<Pago> listarTodos() {
        List<Pago> pagos = pagoRepository.findAll();
        
        pagos.forEach(pago -> {
            pago.setRetraso(notificacionesService.calcularRetraso(pago));
        });
        return pagos;
    }

    public void guardar(Pago pago) {
        
        pago.setRetraso(notificacionesService.calcularRetraso(pago));
        pagoRepository.save(pago);
    }

    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    
    public void actualizar(Pago pago) {
        if (pagoRepository.existsById(pago.getId())) {
            
            pago.setRetraso(notificacionesService.calcularRetraso(pago));
            pagoRepository.save(pago);
        } else {
            throw new RuntimeException("Pago no encontrado con ID: " + pago.getId());
        }
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }

    public List<Pago> pagosAtrasados() {
        return notificacionesService.getPagosAtrasados();
    }
}