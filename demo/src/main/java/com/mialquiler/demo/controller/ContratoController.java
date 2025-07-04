package com.mialquiler.demo.controller;

import com.mialquiler.demo.entity.Contrato;
import com.mialquiler.demo.repository.UserRepository;
import com.mialquiler.demo.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/contratos")

@PreAuthorize("isAuthenticated()")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public ModelAndView listarContratos() {
        ModelAndView mav = new ModelAndView("contratos/contratos");
        
        mav.addObject("contratos", contratoService.listarTodos());
        return mav;
    }

    
    @GetMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarFormulario() {
        ModelAndView mav = new ModelAndView("contratos/contratoForm");
        mav.addObject("contrato", new Contrato());
        mav.addObject("usuarios", userRepository.findAll());
        return mav;
    }

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView guardar(@ModelAttribute Contrato contrato) {
        contratoService.guardar(contrato);
        return new ModelAndView("redirect:/contratos/all");
    }

    
    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarFormularioEdicion(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("contratos/contratoForm");
        Contrato contrato = contratoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado"));
        mav.addObject("contrato", contrato);
        mav.addObject("usuarios", userRepository.findAll());
        mav.addObject("esEdicion", true);
        return mav;
    }

    @PostMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView actualizar(@PathVariable Long id, @ModelAttribute Contrato contrato) {
        contrato.setId(id);
        contratoService.actualizar(contrato);
        return new ModelAndView("redirect:/contratos/all");
    }

    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView eliminar(@PathVariable Long id) {
        contratoService.eliminar(id);
        return new ModelAndView("redirect:/contratos/all");
    }
}
