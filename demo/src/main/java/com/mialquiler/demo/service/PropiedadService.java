package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Propiedad;
import com.mialquiler.demo.repository.PropiedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository propiedadRepository;

    public List<Propiedad> listarTodas() {
        return propiedadRepository.findAll();
    }

    public void guardar(Propiedad propiedad) {
        propiedadRepository.save(propiedad);
    }

    public Optional<Propiedad> buscarPorId(Long id) {
        return propiedadRepository.findById(id);
    }

    public void eliminar(Long id) {
        propiedadRepository.deleteById(id);
    }


}
