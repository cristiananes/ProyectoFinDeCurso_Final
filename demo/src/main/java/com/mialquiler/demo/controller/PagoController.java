package com.mialquiler.demo.controller;

import com.mialquiler.demo.entity.Pago;
import com.mialquiler.demo.repository.ContratoRepository;
import com.mialquiler.demo.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private ContratoRepository contratoRepository;

    @GetMapping("/all")
    public ModelAndView listarPagos() {
        ModelAndView mav = new ModelAndView("pagos/pagos");
        mav.addObject("pagos", pagoService.listarTodos());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView mostrarFormulario() {
        ModelAndView mav = new ModelAndView("pagos/pagoForm");
        mav.addObject("pago", new Pago());
        mav.addObject("contratos", contratoRepository.findAll());
        mav.addObject("esEdicion", false);
        return mav;
    }

    @PostMapping("/crear")
    public ModelAndView guardar(@ModelAttribute Pago pago) {
        pagoService.guardar(pago);
        return new ModelAndView("redirect:/pagos/all");
    }

    // NUEVO: Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public ModelAndView mostrarFormularioEdicion(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("pagos/pagoForm");
        Pago pago = pagoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        mav.addObject("pago", pago);
        mav.addObject("contratos", contratoRepository.findAll());
        mav.addObject("esEdicion", true);
        return mav;
    }

    // NUEVO: Actualizar pago
    @PostMapping("/actualizar/{id}")
    public ModelAndView actualizar(@PathVariable Long id, @ModelAttribute Pago pago) {
        pago.setId(id);
        pagoService.actualizar(pago);
        return new ModelAndView("redirect:/pagos/all");
    }

    @GetMapping("/eliminar/{id}")
    public ModelAndView eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return new ModelAndView("redirect:/pagos/all");
    }
}