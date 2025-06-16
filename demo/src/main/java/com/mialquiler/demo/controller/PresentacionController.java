package com.mialquiler.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PresentacionController {

    @GetMapping("/presentacion")
    public ModelAndView mostrarPresentacion() {
        return new ModelAndView("presentacion");
    }
}
