package com.mialquiler.demo.controller;

import com.mialquiler.demo.service.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
// PERMISO GENERAL: Cualquier usuario autenticado puede ver las estadísticas.
@PreAuthorize("isAuthenticated()")
public class EstadisticasController {

    @Autowired
    private EstadisticasService estadisticasService;

    @GetMapping("/estadisticas")
    public ModelAndView mostrarEstadisticas(
            @RequestParam(required = false) Integer anio,
            @RequestParam(required = false) Double montoMinimo) {

        ModelAndView mav = new ModelAndView("estadisticas/estadisticas");

        // NOTA: De nuevo, sería ideal filtrar estas estadísticas para que un inquilino
        // o propietario solo vea las que le conciernen.
        mav.addObject("propiedadesDisponibles", estadisticasService.contarPropiedadesDisponibles());
        mav.addObject("propiedadesOcupadas", estadisticasService.contarPropiedadesOcupadas());
        mav.addObject("contratosPorVencer30Dias", estadisticasService.contarContratosPorVencer30Dias());
        mav.addObject("totalIngresosMensuales", estadisticasService.calcularIngresosMensuales());

        if (anio != null) {
            mav.addObject("contratosPorAnio", estadisticasService.contarContratosPorAnio(anio));
            mav.addObject("anioConsultado", anio);
        }

        if (montoMinimo != null) {
            mav.addObject("pagosSuperioresA", estadisticasService.contarPagosSuperioresA(montoMinimo));
            mav.addObject("montoConsultado", montoMinimo);
        }

        return mav;
    }
}
