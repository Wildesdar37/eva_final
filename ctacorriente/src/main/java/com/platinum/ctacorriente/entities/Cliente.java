package com.platinum.ctacorriente.entities;

public class Cliente {

  String rut;
  String nombre;
  String apellido;
  String email;
  String direccion;
  Integer telefono;
  String username;
  String password;

  public Cliente(String rut, String nombre, String apellido, String email, String direccion, Integer telefono, String username, String password) {
    this.rut = rut;
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.direccion = direccion;
    this.telefono = telefono;
    this.username = username;
    this.password = password;
  }

  public void setTelefono(Integer telefono) {
    this.telefono = telefono;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRut() {
    return rut;
  }

  public void setRut(String rut) {
    this.rut = rut;
  }



  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public Integer getTelefono() {
    return telefono;
  }


}
