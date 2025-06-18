package com.mialquiler.demo.controller;

import com.mialquiler.demo.entity.Usuario;
import com.mialquiler.demo.repository.UserRepository;
import com.mialquiler.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    
    @GetMapping("/all")
    public ModelAndView obtenerListaUsuarios(){
        ModelAndView salida = new ModelAndView("users/users");
        salida.addObject("users", userRepository.findAll());
        return salida;
    }

    
    @GetMapping("/crear")
    public ModelAndView crearUsuario(){
        ModelAndView salida = new ModelAndView("users/usersForm");
        salida.addObject("usuario", new Usuario());
        salida.addObject("esEdicion", false);
        return salida;
    }

    
    @PostMapping("/create")
    public ModelAndView guardarUsuario(@ModelAttribute Usuario usuario){
        Boolean guardado = userService.validarUsername(usuario);

        if (guardado == true){
            ModelAndView salida = new ModelAndView("users/users");
            salida.addObject("users", userRepository.findAll());
            salida.addObject("mensaje", "Usuario guardado correctamente");
            return salida;
        } else {
            ModelAndView salida = new ModelAndView("users/usersForm");
            salida.addObject("usuario", new Usuario());
            salida.addObject("mensaje", "Nombre de usuario existente, utiliza otro");
            return salida;
        }
    }

    
    @GetMapping("/editar/{username}")
    public ModelAndView mostrarFormularioEdicion(@PathVariable String username) {
        ModelAndView mav = new ModelAndView("users/usersForm");
        Usuario usuario = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        mav.addObject("usuario", usuario);
        mav.addObject("esEdicion", true);
        return mav;
    }

    
    @PostMapping("/actualizar/{username}")
    public ModelAndView actualizar(@PathVariable String username, @ModelAttribute Usuario usuario) {
        usuario.setUsername(username);
        userService.actualizar(usuario);
        ModelAndView mav = new ModelAndView("redirect:/users/all");
        return mav;
    }

    
    @GetMapping("/eliminar/{username}")
    public ModelAndView eliminar(@PathVariable String username) {
        userService.eliminar(username);
        return new ModelAndView("redirect:/users/all");
    }
}