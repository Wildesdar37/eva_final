package com.platinum.ctacorriente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platinum.ctacorriente.models.Transaccion;

public interface TransaccionRepository  extends JpaRepository<Transaccion, Integer> {

}
