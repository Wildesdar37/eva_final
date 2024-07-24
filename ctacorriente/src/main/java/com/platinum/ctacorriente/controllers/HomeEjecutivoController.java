package com.platinum.ctacorriente.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


import com.platinum.ctacorriente.models.Persona;
import com.platinum.ctacorriente.services.PersonaService;

@Controller
public class HomeEjecutivoController {

  private final PersonaService personaService;

  public HomeEjecutivoController(PersonaService personaService) {
    this.personaService = personaService;
  }

  @GetMapping("/")
  public String home(Model model) {
    List<Persona> clientes = personaService.findAllClients();
    model.addAttribute("clientes", clientes);
    return "home";
  }
}
