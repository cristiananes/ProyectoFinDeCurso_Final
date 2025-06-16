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
// 1. PERMISO GENERAL: Permitimos que cualquier usuario autenticado acceda a las URLs de este controlador.
@PreAuthorize("isAuthenticated()")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public ModelAndView listarContratos() {
        ModelAndView mav = new ModelAndView("contratos/contratos");
        // IMPORTANTE: Esto sigue mostrando TODOS los contratos. Más abajo te explico cómo arreglarlo.
        mav.addObject("contratos", contratoService.listarTodos());
        return mav;
    }

    // 2. PERMISOS ESPECÍFICOS: Solo el ADMIN puede acceder a la URL para crear.
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

    // 3. Dejamos el acceso a la edición y eliminación solo para ADMIN.
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
