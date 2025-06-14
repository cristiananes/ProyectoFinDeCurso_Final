package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Contrato;
import com.mialquiler.demo.entity.Propiedad;
import com.mialquiler.demo.entity.PropiedadContrato;
import com.mialquiler.demo.entity.PropiedadContratoId;
import com.mialquiler.demo.repository.ContratoRepository;
import com.mialquiler.demo.repository.PropiedadContratoRepository;
import com.mialquiler.demo.repository.PropiedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropiedadContratoService {

    @Autowired
    private PropiedadContratoRepository propiedadContratoRepository;

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    public List<PropiedadContrato> listarTodas() {
        return propiedadContratoRepository.findAll();
    }

    public void crear(Long idPropiedad, Long idContrato, String estado) {
        Propiedad propiedad = propiedadRepository.findById(idPropiedad)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));
        Contrato contrato = contratoRepository.findById(idContrato)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado"));

        PropiedadContratoId id = new PropiedadContratoId();
        id.setId_propiedad(idPropiedad);
        id.setId_contrato(idContrato);

        PropiedadContrato pc = new PropiedadContrato();
        pc.setId(id);
        pc.setPropiedad(propiedad);
        pc.setContrato_propiedad(contrato);
        pc.setEstado(estado);

        propiedadContratoRepository.save(pc);
    }

    public Optional<PropiedadContrato> buscarPorId(Long idPropiedad, Long idContrato) {
        PropiedadContratoId id = new PropiedadContratoId();
        id.setId_propiedad(idPropiedad);
        id.setId_contrato(idContrato);
        return propiedadContratoRepository.findById(id);
    }

    public void actualizar(Long idPropiedad, Long idContrato, String estado) {
        PropiedadContratoId id = new PropiedadContratoId();
        id.setId_propiedad(idPropiedad);
        id.setId_contrato(idContrato);

        PropiedadContrato pc = propiedadContratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        pc.setEstado(estado);
        propiedadContratoRepository.save(pc);
    }

    public void eliminar(Long idPropiedad, Long idContrato) {
        PropiedadContratoId id = new PropiedadContratoId();
        id.setId_propiedad(idPropiedad);
        id.setId_contrato(idContrato);
        propiedadContratoRepository.deleteById(id);
    }
}