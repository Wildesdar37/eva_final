package com.platinum.ctacorriente.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platinum.ctacorriente.models.CtaCorriente;
import com.platinum.ctacorriente.models.Ejecutivo;
import com.platinum.ctacorriente.models.Persona;

@Repository
public interface CtaCorrienteRepository extends JpaRepository<CtaCorriente, Integer> {

    CtaCorriente findByIdCuenta(Long idCuenta);
    

    CtaCorriente findByNumeroCuenta(String numeroCuenta);

    List<CtaCorriente> findByPersona(Persona persona);

    List<CtaCorriente> findAllByEjecutivo(Ejecutivo ejecutivo);

    List<CtaCorriente> findAllByPersonaRut(String rut);
}
