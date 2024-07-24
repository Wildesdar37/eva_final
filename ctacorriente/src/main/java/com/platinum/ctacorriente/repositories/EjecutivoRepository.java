package com.platinum.ctacorriente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platinum.ctacorriente.models.Ejecutivo;

public interface EjecutivoRepository extends JpaRepository<Ejecutivo, String> {

  Ejecutivo findByRutEjecutivo(String rut);
}
