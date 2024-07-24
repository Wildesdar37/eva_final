package com.platinum.ctacorriente.models;


import com.platinum.ctacorriente.enums.TipoCuenta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "cta_corriente")
public class CtaCorriente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idCuenta;

  @Column(nullable = false, unique = true, updatable = false)
  private String numeroCuenta;



  @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

  @ManyToOne
  @JoinColumn(name = "rut_cliente", referencedColumnName = "rut")
  private Persona persona;

  @Column(nullable = false)
  private double monto;

  @ManyToOne
  @JoinColumn(name = "rut_ejecutivo", referencedColumnName = "rutEjecutivo")
  private Ejecutivo ejecutivo;
  
  
    public TipoCuenta getTipoCuenta() {
    return tipoCuenta;
  }

  public void setTipoCuenta(TipoCuenta tipoCuenta) {
    this.tipoCuenta = tipoCuenta;
  }
  public String getNumeroCuenta() {
    return numeroCuenta;
  }

  public void setNumeroCuenta(String numeroCuenta) {
    this.numeroCuenta = numeroCuenta;
  }


  public Long getIdCuenta() {
    return idCuenta;
  }

  public void setIdCuenta(Long idCuenta) {
    this.idCuenta = idCuenta;
  }

  public Persona getPersona() {
    return persona;
  }

  public void setPersona(Persona persona) {
    this.persona = persona;
  }

  public double getMonto() {
    return monto;
  }

  public void setMonto(double monto) {
    this.monto = monto;
  }

  public Ejecutivo getEjecutivo() {
    return ejecutivo;
  }

  public void setEjecutivo(Ejecutivo ejecutivo) {
    this.ejecutivo = ejecutivo;
  }

}

