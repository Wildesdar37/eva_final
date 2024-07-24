package com.platinum.ctacorriente.forms;

import com.platinum.ctacorriente.enums.TipoCuenta;

public class CuentaForm {
    private String rutCliente;
    private double monto;
    private TipoCuenta tipoCuenta;


    // Getters y setters
    public String getRutCliente() {
        return rutCliente;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente = rutCliente;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
}
