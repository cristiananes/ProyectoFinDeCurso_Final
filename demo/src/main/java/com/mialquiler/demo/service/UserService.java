package com.mialquiler.demo.service;

import com.mialquiler.demo.entity.Usuario;
import com.mialquiler.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    // Cambiar rol a propietario
    public Usuario cambiarRolToPropietario(Usuario usuario){
        usuario.setRol("PROPIETARIO");
        userRepository.save(usuario);
        return usuario;
    }

    // Cambiar rol a inquilino
    public Usuario cambiarRolToInquilino(Usuario usuario){
        usuario.setRol("INQUILINO");
        userRepository.save(usuario);
        return usuario;
    }

    // Validar username único
    public Boolean validarUsername(Usuario usuario){
        Optional<Usuario> user = userRepository.findById(usuario.getUsername());
        if (user.isEmpty()){
            userRepository.save(usuario);
            return true;
        } else {
            return false;
        }
    }

    // NUEVO: Actualizar usuario
    public void actualizar(Usuario usuario) {
        if (userRepository.existsById(usuario.getUsername())) {
            // Mantener la contraseña si no se proporciona una nueva
            if (usuario.getContrasenia() == null || usuario.getContrasenia().isEmpty()) {
                Usuario usuarioExistente = userRepository.findById(usuario.getUsername()).get();
                usuario.setContrasenia(usuarioExistente.getContrasenia());
            }
            userRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado: " + usuario.getUsername());
        }
    }

    // NUEVO: Eliminar usuario (eliminación lógica)
    public void eliminar(String username) {
        Optional<Usuario> usuario = userRepository.findById(username);
        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            u.setEliminado(true);
            userRepository.save(u);
        } else {
            throw new RuntimeException("Usuario no encontrado: " + username);
        }
    }

    public long contarUsuarios() {
        return userRepository.count();
    }
}