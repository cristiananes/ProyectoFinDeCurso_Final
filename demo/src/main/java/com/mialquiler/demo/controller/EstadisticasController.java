package com.mialquiler.demo.controller;

import com.mialquiler.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/estadisticas")
public class EstadisticasController {

    @Autowired
    private EstadisticasService estadisticasService;

    @GetMapping
    public ModelAndView mostrarEstadisticas(@RequestParam(required = false) Integer dias,
                                            @RequestParam(required = false) Integer precioMinimo) {
        ModelAndView mav = new ModelAndView("estadisticas/estadisticas");

        // Estadísticas fijas
        mav.addObject("totalUsuarios", estadisticasService.contarUsuarios());
        mav.addObject("totalPropiedades", estadisticasService.contarPropiedades());
        mav.addObject("propiedadesDisponibles", estadisticasService.contarPropiedadesDisponibles());
        mav.addObject("pagosAtrasados", estadisticasService.contarPagosAtrasados());

        // Estadísticas personalizables
        int diasVencimiento = (dias != null) ? dias : 30;
        int precioMin = (precioMinimo != null) ? precioMinimo : 500;

        mav.addObject("contratosPorVencer", estadisticasService.contratosPorVencerEnDias(diasVencimiento));
        mav.addObject("propiedadesCaras", estadisticasService.propiedadesConPrecioMayorA(precioMin));

        // Para los formularios
        mav.addObject("diasParam", diasVencimiento);
        mav.addObject("precioParam", precioMin);

        return mav;
    }
}