package com.platinum.ctacorriente.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.platinum.ctacorriente.models.CtaCorriente;
import com.platinum.ctacorriente.models.Persona;
import com.platinum.ctacorriente.models.User;
import com.platinum.ctacorriente.repositories.PersonaRepository;

@Service
public class PersonaService {

  private final PersonaRepository personaRepository;
  private final UserService userService;
  private final CuentaService cuentaService;

  public PersonaService(PersonaRepository personaRepository, UserService userService, CuentaService cuentaService) {
    this.personaRepository = personaRepository;
    this.userService = userService;
    this.cuentaService = cuentaService;
  }

  public Set<Persona> findAllByEjecutivo(String username) {
    User user = this.userService.findByUsername(username);
    List<CtaCorriente> cuentas = this.cuentaService.findAllByEjecutivo(user.getEjecutivo());
    return cuentas.stream().map(CtaCorriente::getPersona).collect(Collectors.toSet());
  }

  public List<Persona> findAllClients() {
    return this.personaRepository.findAll();
  }

  public Persona save(Persona persona) {
    return personaRepository.save(persona);
  }

  public Persona findByRut(String rut) {
    return personaRepository.findByRut(rut);
  }

  public void deleteAll() {
    this.personaRepository.deleteAll();
  }

}
