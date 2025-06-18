package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Perfil;
import com.mialquiler.demo.entity.Usuario;
import com.mialquiler.demo.repository.PerfilRepository;
import com.mialquiler.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UserRepository userRepository;

    
    public List<Perfil> listarTodos() {
        return perfilRepository.findAll();
    }

    
    public void guardar(Perfil perfil) {
        perfilRepository.save(perfil);
    }

    
    public Optional<Perfil> buscarPorId(Long id) {
        return perfilRepository.findById(id);
    }

    
    public void actualizar(Perfil perfil) {
        if (perfil.getId() != null && perfilRepository.existsById(perfil.getId())) {
            perfilRepository.save(perfil);
        } else {
            throw new RuntimeException("Perfil no encontrado con ID: " + perfil.getId());
        }
    }

    
    public void eliminar(Long id) {
        perfilRepository.deleteById(id);
    }

    
    public Usuario asignarPerfilAUsuario(String username, Long perfilId) {
        Usuario usuario = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        usuario.setPerfil(perfil);
        if (perfil.getNombre() != null) {
            usuario.setRol(perfil.getNombre().toUpperCase());
        }

        return userRepository.save(usuario);
    }

    

}
