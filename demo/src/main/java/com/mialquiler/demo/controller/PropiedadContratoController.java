package com.mialquiler.demo.controller;

import com.mialquiler.demo.entity.PropiedadContrato;
import com.mialquiler.demo.entity.PropiedadContratoId;
import com.mialquiler.demo.repository.ContratoRepository;
import com.mialquiler.demo.repository.PropiedadRepository;
import com.mialquiler.demo.service.PropiedadContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/propiedad-contrato")
public class PropiedadContratoController {

    @Autowired
    private PropiedadContratoService propiedadContratoService;

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    @GetMapping("/all")
    public ModelAndView listar() {
        ModelAndView mav = new ModelAndView("propiedadContrato/lista");
        mav.addObject("asignaciones", propiedadContratoService.listarTodas());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView mostrarFormulario() {
        ModelAndView mav = new ModelAndView("propiedadContrato/formulario");
        mav.addObject("propiedades", propiedadRepository.findAll());
        mav.addObject("contratos", contratoRepository.findAll());
        mav.addObject("esEdicion", false);
        return mav;
    }

    @PostMapping("/crear")
    public ModelAndView guardar(@RequestParam Long idPropiedad,
                                @RequestParam Long idContrato,
                                @RequestParam String estado) {
        propiedadContratoService.crear(idPropiedad, idContrato, estado);
        return new ModelAndView("redirect:/propiedad-contrato/all");
    }

    @GetMapping("/editar/{idPropiedad}/{idContrato}")
    public ModelAndView mostrarFormularioEdicion(@PathVariable Long idPropiedad,
                                                 @PathVariable Long idContrato) {
        ModelAndView mav = new ModelAndView("propiedadContrato/formulario");
        PropiedadContrato pc = propiedadContratoService.buscarPorId(idPropiedad, idContrato)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        mav.addObject("asignacion", pc);
        mav.addObject("propiedades", propiedadRepository.findAll());
        mav.addObject("contratos", contratoRepository.findAll());
        mav.addObject("esEdicion", true);
        return mav;
    }

    @PostMapping("/actualizar/{idPropiedad}/{idContrato}")
    public ModelAndView actualizar(@PathVariable Long idPropiedad,
                                   @PathVariable Long idContrato,
                                   @RequestParam String estado) {
        propiedadContratoService.actualizar(idPropiedad, idContrato, estado);
        return new ModelAndView("redirect:/propiedad-contrato/all");
    }

    @GetMapping("/eliminar/{idPropiedad}/{idContrato}")
    public ModelAndView eliminar(@PathVariable Long idPropiedad,
                                 @PathVariable Long idContrato) {
        propiedadContratoService.eliminar(idPropiedad, idContrato);
        return new ModelAndView("redirect:/propiedad-contrato/all");
    }
}