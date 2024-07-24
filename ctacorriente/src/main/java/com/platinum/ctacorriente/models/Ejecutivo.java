package com.platinum.ctacorriente.models;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name = "ejecutivos")
public class Ejecutivo {

    @Id
    @Column(nullable = false, unique = true)
    private String rutEjecutivo;

    @Column(nullable = false)
    private String nombre;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(nullable = false)
    private String departamento;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
    public String getRutEjecutivo() {
        return rutEjecutivo;
    }

    public void setRutEjecutivo(String rutEjecutivo) {
        this.rutEjecutivo = rutEjecutivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}

