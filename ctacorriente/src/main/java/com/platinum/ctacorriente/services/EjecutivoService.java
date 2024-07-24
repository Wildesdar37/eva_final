package com.platinum.ctacorriente.services;

import org.springframework.stereotype.Service;

import com.platinum.ctacorriente.models.Ejecutivo;
import com.platinum.ctacorriente.models.User;
import com.platinum.ctacorriente.repositories.EjecutivoRepository;

@Service
public class EjecutivoService {

  private final EjecutivoRepository ejecutivoRepository;


  public EjecutivoService(EjecutivoRepository ejecutivoRepository) {
    this.ejecutivoRepository = ejecutivoRepository;
  }

  public Ejecutivo createEjecutivo(String rut, String name, String departamento, User user) {
    Ejecutivo ejecutivo = new Ejecutivo();
    ejecutivo.setRutEjecutivo(rut);
    ejecutivo.setNombre(name);
    ejecutivo.setDepartamento(departamento);
    ejecutivo.setUser(user);
    return ejecutivoRepository.save(ejecutivo);
  }

  public void deleteAll() {
    ejecutivoRepository.deleteAll();
  }

}
