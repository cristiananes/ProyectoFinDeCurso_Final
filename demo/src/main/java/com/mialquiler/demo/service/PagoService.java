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
        return pagoRepository.findAll();
    }

    public void guardar(Pago pago) {
        pagoRepository.save(pago);
    }

    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }
    public List<Pago> pagosAtrasados() {
        return notificacionesService.getPagosAtrasados();
    }
}

