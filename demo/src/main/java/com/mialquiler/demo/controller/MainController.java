package com.mialquiler.demo.controller;

import com.mialquiler.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// Se elimina la importación de PreAuthorize ya que no se usará a nivel de clase
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
// SE ELIMINA LA ANOTACIÓN @PreAuthorize("hasRole('ADMIN')") DE AQUÍ
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

        // La vista (index.html) ya utiliza Spring Security con Thymeleaf (sec:authorize)
        // para mostrar u ocultar contenido según el rol del usuario autenticado.
        // Por lo tanto, es seguro cargar estos datos para todos.

        // Estadísticas básicas
        mav.addObject("totalUsuarios", userService.contarUsuarios());
        mav.addObject("totalPropiedades", propiedadService.listarTodas().size());
        mav.addObject("totalContratos", contratoService.listarTodos().size());
        mav.addObject("totalPagos", pagoService.listarTodos().size());

        // Datos para las alertas y notificaciones
        mav.addObject("contratosPorVencer", contratoService.contratosPorVencer());
        mav.addObject("pagosAtrasados", pagoService.pagosAtrasados());

        return mav;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboardAlternativo() {
        return dashboard();
    }
}
