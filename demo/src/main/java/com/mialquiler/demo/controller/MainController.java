package com.mialquiler.demo.controller;

import com.mialquiler.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private PropiedadService propiedadService;

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private PagoService pagoService;

    @GetMapping("/")
    public ModelAndView dashboard() {
        ModelAndView mav = new ModelAndView("index");

        
        
        

        
        mav.addObject("totalUsuarios", userService.contarUsuarios());
        mav.addObject("totalPropiedades", propiedadService.listarTodas().size());
        mav.addObject("totalContratos", contratoService.listarTodos().size());
        mav.addObject("totalPagos", pagoService.listarTodos().size());

        
        mav.addObject("contratosPorVencer", contratoService.contratosPorVencer());
        mav.addObject("pagosAtrasados", pagoService.pagosAtrasados());

        return mav;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboardAlternativo() {
        return dashboard();
    }
}
