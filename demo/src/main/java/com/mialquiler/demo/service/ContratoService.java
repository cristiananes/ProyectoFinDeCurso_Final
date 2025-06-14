package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Contrato;
import com.mialquiler.demo.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private NotificacionesService notificacionesService;

    public List<Contrato> listarTodos() {
        return contratoRepository.findAll();
    }

    public void guardar(Contrato contrato) {
        contratoRepository.save(contrato);
    }

    public Optional<Contrato> buscarPorId(Long id) {
        return contratoRepository.findById(id);
    }

    public void eliminar(Long id) {
        contratoRepository.deleteById(id);
    }

    public List<Contrato> contratosPorVencer() {
        return notificacionesService.getContratosPorVencer();
    }
}
