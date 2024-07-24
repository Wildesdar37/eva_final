package com.platinum.ctacorriente.models;

import java.time.LocalDateTime;

import com.platinum.ctacorriente.enums.TipoCuenta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaccion")
public class Transaccion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "rutCliente", referencedColumnName = "rut")
  private Persona cliente;

  @ManyToOne
  @JoinColumn(name = "rutDueno", referencedColumnName = "rut")
  private Persona dueno;

  @ManyToOne
  @JoinColumn(name = "cuentaOrigen", referencedColumnName = "idCuenta")
  private CtaCorriente cuentaOrigen;
  
  @ManyToOne
  @JoinColumn(name = "cuentaDestino", referencedColumnName = "idCuenta")
  private CtaCorriente cuentaDestino;

  @Column(nullable = false)
  private double montoTranferecia;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoCuenta tipoCuenta;

  @Column(nullable = false)
  private LocalDateTime horaTransaccion;

  @PrePersist
  protected void onCreate() {
    horaTransaccion = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getHoraTransaccion() {
    return horaTransaccion;
  }

  public void setHoraTransaccion(LocalDateTime horaTransaccion) {
    this.horaTransaccion = horaTransaccion;
  }

  public void setId(Long id) {
    this.id = id;
  }

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



  public double getMontoTranferecia() {
    return montoTranferecia;
  }

  public void setMontoTranferecia(double montoTranferecia) {
    this.montoTranferecia = montoTranferecia;
  }



  public CtaCorriente getCuentaOrigen() {
    return cuentaOrigen;
  }

  public void setCuentaOrigen(CtaCorriente cuentaOrigen) {
    this.cuentaOrigen = cuentaOrigen;
  }

  public CtaCorriente getCuentaDestino() {
    return cuentaDestino;
  }

  public void setCuentaDestino(CtaCorriente cuentaDestino) {
    this.cuentaDestino = cuentaDestino;
  }

  public TipoCuenta getTipoCuenta() {
    return tipoCuenta;
  }

  public void setTipoCuenta(TipoCuenta tipoCuenta) {
    this.tipoCuenta = tipoCuenta;
  }

}
