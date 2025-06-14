package com.mialquiler.demo.controller;

import com.mialquiler.demo.entity.Contrato;
import com.mialquiler.demo.repository.UserRepository;
import com.mialquiler.demo.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/contratos")
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
    public ModelAndView mostrarFormulario() {
        ModelAndView mav = new ModelAndView("contratos/contratoForm");
        mav.addObject("contrato", new Contrato());
        mav.addObject("usuarios", userRepository.findAll());
        return mav;
    }

    @PostMapping("/crear")
    public ModelAndView guardar(@ModelAttribute Contrato contrato) {
        contratoService.guardar(contrato);
        return new ModelAndView("redirect:/contratos/all");
    }

    @GetMapping("/eliminar/{id}")
    public ModelAndView eliminar(@PathVariable Long id) {
        contratoService.eliminar(id);
        return new ModelAndView("redirect:/contratos/all");
    }
}

