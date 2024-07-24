package com.platinum.ctacorriente.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.platinum.ctacorriente.forms.LoginForm;
import com.platinum.ctacorriente.forms.RegistrationForm;
import com.platinum.ctacorriente.models.Ejecutivo;
import com.platinum.ctacorriente.models.User;
import com.platinum.ctacorriente.services.EjecutivoService;
import com.platinum.ctacorriente.services.UserService;

@Controller
public class EjecutivoController {

  private final EjecutivoService ejecutivoService;
  private final UserService userService;

  public EjecutivoController(EjecutivoService ejecutivoService, UserService userService) {
    this.ejecutivoService = ejecutivoService;
    this.userService = userService;
  }

  @GetMapping("/register")
  public String register(Model model) {
    model.addAttribute("registrationForm", new RegistrationForm());
    return "register";
  }

  @PostMapping("/register")
  public String registerUser(RegistrationForm form) {
    User user = this.userService.createUser(form.getUsername(), form.getPassword());
    this.ejecutivoService.createEjecutivo(form.getRut(), form.getName(), form.getDepartamento(), user);

    return "redirect:/login";
  }

  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("loginForm", new LoginForm());
    return "login";
  }

  @GetMapping("/perfil-ejecutivo")
  public String verPerfil(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    User user = userService.findByUsername(username);
    Ejecutivo ejecutivo = user.getEjecutivo();

    model.addAttribute("user", user);
    model.addAttribute("ejecutivo", ejecutivo);

    return "perfil-ejecutivo";
  }

}
