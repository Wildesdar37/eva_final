package com.platinum.ctacorriente.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.platinum.ctacorriente.entities.Cliente;
import com.platinum.ctacorriente.models.CtaCorriente;
import com.platinum.ctacorriente.models.Persona;
import com.platinum.ctacorriente.models.User;
import com.platinum.ctacorriente.repositories.CtaCorrienteRepository;

@Service
public class ClienteService {

  private final UserService userService;
  private final PersonaService personaService;
  private final CtaCorrienteRepository ctaCorrienteRepository;
  private final PasswordEncoder passwordEncoder;

  public ClienteService(UserService userService, PersonaService personaService,
      CtaCorrienteRepository ctaCorrienteRepository, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.personaService = personaService;
    this.ctaCorrienteRepository = ctaCorrienteRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public Persona createCliente(Cliente cliente) {

    User user = this.userService.createUser(cliente.getUsername(), this.passwordEncoder.encode(cliente.getPassword()));

    Persona persona = new Persona();
    persona.setRut(cliente.getRut());
    persona.setNombre(cliente.getNombre());
    persona.setApellido(cliente.getApellido());
    persona.setEmail(cliente.getEmail());
    persona.setDireccion(cliente.getDireccion());
    persona.setTelefono(cliente.getTelefono());
    persona.setUser(user);

    return this.personaService.save(persona);

  }

  public List<CtaCorriente> cuentasCliente(String rutCliente) {
    Persona persona = this.personaService.findByRut(rutCliente);
    return this.ctaCorrienteRepository.findByPersona(persona);
  }

}
