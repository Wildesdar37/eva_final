package com.platinum.ctacorriente.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.platinum.ctacorriente.models.CtaCorriente;
import com.platinum.ctacorriente.repositories.CtaCorrienteRepository;

@Service
public class CtaCorrienteService {

  private final CtaCorrienteRepository ctaCorrienteRepository;

  public CtaCorrienteService(CtaCorrienteRepository ctaCorrienteRepository) {
    this.ctaCorrienteRepository = ctaCorrienteRepository;
  }

  public List<CtaCorriente> findAll() {
    return ctaCorrienteRepository.findAll();
  }

  public CtaCorriente save(CtaCorriente ctaCorriente) {
    return ctaCorrienteRepository.save(ctaCorriente);
  }

  public void deleteAll() {
    ctaCorrienteRepository.deleteAll();
  }

}
