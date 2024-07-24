package com.platinum.ctacorriente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platinum.ctacorriente.models.User;


public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsername(String username);
}
