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

    /**
     * Obtener todos los perfiles registrados
     */
    public List<Perfil> listarTodos() {
        return perfilRepository.findAll();
    }

    /**
     * Guardar un nuevo perfil
     */
    public void guardar(Perfil perfil) {
        perfilRepository.save(perfil);
    }

    /**
     * Buscar un perfil por su identificador
     */
    public Optional<Perfil> buscarPorId(Long id) {
        return perfilRepository.findById(id);
    }

    /**
     * Actualizar un perfil existente
     */
    public void actualizar(Perfil perfil) {
        if (perfil.getId() != null && perfilRepository.existsById(perfil.getId())) {
            perfilRepository.save(perfil);
        } else {
            throw new RuntimeException("Perfil no encontrado con ID: " + perfil.getId());
        }
    }

    /**
     * Eliminar un perfil por su ID
     */
    public void eliminar(Long id) {
        perfilRepository.deleteById(id);
    }

    /**
     * Asignar un perfil a un usuario y actualizar su rol
     */
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

    //gestion de roles, perfiles y permisos.

}
