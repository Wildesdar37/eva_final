package com.platinum.ctacorriente.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.platinum.ctacorriente.models.Persona;
import com.platinum.ctacorriente.models.User;




public interface PersonaRepository extends JpaRepository<Persona, String> {

  Persona findByUser(User user);
  Persona findByRut(String rut);

}
