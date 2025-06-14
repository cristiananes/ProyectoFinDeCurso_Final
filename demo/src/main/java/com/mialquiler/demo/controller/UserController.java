package com.mialquiler.demo.controller;

import com.mialquiler.demo.entity.Usuario;
import com.mialquiler.demo.repository.UserRepository;
import com.mialquiler.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
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
        salida.addObject("isEdit", false);
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
        }else {
            ModelAndView salida = new ModelAndView("users/usersForm");
            salida.addObject("usuario", new Usuario());
            salida.addObject("isEdit", false);
            salida.addObject("mensaje", "Nombre de usuario existente utiliza otro");
            return salida;
        }
    }

    // NUEVO: Mostrar formulario de edición
    @GetMapping("/editar/{username}")
    public ModelAndView editarUsuario(@PathVariable String username) {
        ModelAndView mav = new ModelAndView("users/usersForm");
        Usuario usuario = userRepository.findById(username).orElse(new Usuario());
        mav.addObject("usuario", usuario);
        mav.addObject("isEdit", true);
        return mav;
    }

    // NUEVO: Actualizar usuario
    @PostMapping("/update")
    public ModelAndView actualizarUsuario(@ModelAttribute Usuario usuario) {
        userRepository.save(usuario);
        ModelAndView mav = new ModelAndView("users/users");
        mav.addObject("users", userRepository.findAll());
        mav.addObject("mensaje", "Usuario actualizado correctamente");
        return mav;
    }

    // NUEVO: Eliminar usuario
    @GetMapping("/eliminar/{username}")
    public ModelAndView eliminarUsuario(@PathVariable String username) {
        userRepository.deleteById(username);
        ModelAndView mav = new ModelAndView("redirect:/users/all");
        return mav;
    }
}