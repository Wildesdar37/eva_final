package com.platinum.ctacorriente.entities;

import com.platinum.ctacorriente.enums.TipoCuenta;
import com.platinum.ctacorriente.models.CtaCorriente;
import com.platinum.ctacorriente.models.Persona;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class TransaccionEntity {

  private Persona cliente;
  private Persona dueno;
  private CtaCorriente cuentaOrigen;
  private CtaCorriente cuentaDestino;
  private double montoTransferencia;

  @Enumerated(EnumType.STRING) 
  private TipoCuenta tipoCuenta;
  
  public Persona getCliente() {
    return cliente;
  }
  public void setCliente(Persona cliente) {
    this.cliente = cliente;
  }
  public Persona getDueno() {
    return dueno;
  }
  public void setDueno(Persona dueno) {
    this.dueno = dueno;
  }
  public CtaCorriente getCuentaOrigen() {
    return cuentaOrigen;
  }
  public void setCuentaOrigen(CtaCorriente cuentaOrigen) {
    this.cuentaOrigen = cuentaOrigen;
  }
  
  public double getMontoTransferencia() {
    return montoTransferencia;
  }
  public void setMontoTransferencia(double montoTransferencia) {
    this.montoTransferencia = montoTransferencia;
  }
  public TipoCuenta getTipoCuenta() {
    return tipoCuenta;
  }
  public void setTipoCuenta(TipoCuenta tipoCuenta) {
    this.tipoCuenta = tipoCuenta;
  }
  public CtaCorriente getCuentaDestino() {
    return cuentaDestino;
  }
  public void setCuentaDestino(CtaCorriente cuentaDestino) {
    this.cuentaDestino = cuentaDestino;
  }

  


}
