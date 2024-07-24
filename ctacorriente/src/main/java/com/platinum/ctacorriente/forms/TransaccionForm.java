package com.platinum.ctacorriente.forms;

public class TransaccionForm {
    private String clienteOrigen;
    private String cuentaOrigen;
    private String clienteDestino;
    private String cuentaDestino;
    private double monto;
    public String getClienteOrigen() {
      return clienteOrigen;
    }
    public void setClienteOrigen(String clienteOrigen) {
      this.clienteOrigen = clienteOrigen;
    }
    public String getCuentaOrigen() {
      return cuentaOrigen;
    }
    public void setCuentaOrigen(String cuentaOrigen) {
      this.cuentaOrigen = cuentaOrigen;
    }
    public String getClienteDestino() {
      return clienteDestino;
    }
    public void setClienteDestino(String clienteDestino) {
      this.clienteDestino = clienteDestino;
    }
    public String getCuentaDestino() {
      return cuentaDestino;
    }
    public void setCuentaDestino(String cuentaDestino) {
      this.cuentaDestino = cuentaDestino;
    }
    public double getMonto() {
      return monto;
    }
    public void setMonto(double monto) {
      this.monto = monto;
    }


}