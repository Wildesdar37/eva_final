package com.platinum.ctacorriente.dto;

public class CtaCorrienteDTO {
  private Long idCuenta;
  private String numeroCuenta;

  public CtaCorrienteDTO(Long idCuenta, String numeroCuenta) {
    this.idCuenta = idCuenta;
    this.numeroCuenta = numeroCuenta;
  }

  public Long getIdCuenta() {
    return idCuenta;
  }

  public void setIdCuenta(Long idCuenta) {
    this.idCuenta = idCuenta;
  }

  public String getNumeroCuenta() {
    return numeroCuenta;
  }

  public void setNumeroCuenta(String numeroCuenta) {
    this.numeroCuenta = numeroCuenta;
  }

}
