package com.mialquiler.demo.controller;

import com.mialquiler.demo.entity.Propiedad;
import com.mialquiler.demo.repository.UserRepository;
import com.mialquiler.demo.service.PropiedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/propiedades")
public class PropiedadController {

    @Autowired
    private PropiedadService propiedadService;

    @Autowired
    private UserRepository userRepository;

    
    @GetMapping("/all")
    public ModelAndView listarPropiedades() {
        ModelAndView mav = new ModelAndView("propiedades/propiedades");
        mav.addObject("propiedades", propiedadService.listarTodas());
        return mav;
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/crear")
    public ModelAndView mostrarFormulario() {
        ModelAndView mav = new ModelAndView("propiedades/propiedadForm");
        mav.addObject("propiedad", new Propiedad());
        mav.addObject("usuarios", userRepository.findAll());
        mav.addObject("esEdicion", false);
        return mav;
    }

    
    @PostMapping("/crear")
    public ModelAndView guardar(@ModelAttribute Propiedad propiedad) {
        propiedadService.guardar(propiedad);
        return new ModelAndView("redirect:/propiedades/all");
    }

    
    @GetMapping("/editar/{id}")
    public ModelAndView mostrarFormularioEdicion(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("propiedades/propiedadForm");
        Propiedad propiedad = propiedadService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));
        mav.addObject("propiedad", propiedad);
        mav.addObject("usuarios", userRepository.findAll());
        mav.addObject("esEdicion", true);
        return mav;
    }

    
    @PostMapping("/actualizar/{id}")
    public ModelAndView actualizar(@PathVariable Long id, @ModelAttribute Propiedad propiedad) {
        propiedad.setId(id);
        propiedadService.actualizar(propiedad);
        return new ModelAndView("redirect:/propiedades/all");
    }

    
    @GetMapping("/eliminar/{id}")
    public ModelAndView eliminar(@PathVariable Long id) {
        propiedadService.eliminar(id);
        return new ModelAndView("redirect:/propiedades/all");
    }
}