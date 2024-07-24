package com.platinum.ctacorriente.services;

import org.hibernate.TransactionException;
import org.springframework.stereotype.Service;

import com.platinum.ctacorriente.entities.TransaccionEntity;
import com.platinum.ctacorriente.errors.TransferenciaException;
import com.platinum.ctacorriente.forms.TransaccionForm;
import com.platinum.ctacorriente.models.CtaCorriente;
import com.platinum.ctacorriente.models.Transaccion;
import com.platinum.ctacorriente.repositories.CtaCorrienteRepository;
import com.platinum.ctacorriente.repositories.TransaccionRepository;

@Service
public class TransaccionService {

  private final CtaCorrienteRepository ctaCorrienteRepository;
  private final TransaccionRepository transaccionRepository;

  public TransaccionService(CtaCorrienteRepository ctaCorrienteRepository,
      TransaccionRepository transaccionRepository) {
    this.ctaCorrienteRepository = ctaCorrienteRepository;
    this.transaccionRepository = transaccionRepository;
  }

  public Transaccion transferir(TransaccionForm form) {
    CtaCorriente cuentaOrigen = ctaCorrienteRepository.findByNumeroCuenta(form.getCuentaOrigen());

    if (cuentaOrigen == null) {
      throw new TransferenciaException("Cuenta de origen no encontrada");
    }

    CtaCorriente cuentaDestino = ctaCorrienteRepository.findByNumeroCuenta(form.getCuentaDestino());

    if (cuentaDestino == null) {
      throw new TransferenciaException("Cuenta de destino no encontrada");
    }

    if (cuentaOrigen.getMonto() < form.getMonto()) {
      throw new TransferenciaException("Fondos insuficientes en la cuenta origen");
    }

    cuentaOrigen.setMonto(cuentaOrigen.getMonto() - form.getMonto());
    cuentaDestino.setMonto(cuentaDestino.getMonto() + form.getMonto());

    ctaCorrienteRepository.save(cuentaOrigen);
    ctaCorrienteRepository.save(cuentaDestino);

    TransaccionEntity transaccionEntity = new TransaccionEntity();
    transaccionEntity.setCliente(cuentaDestino.getPersona());
    transaccionEntity.setDueno(cuentaOrigen.getPersona());
    transaccionEntity.setCuentaOrigen(cuentaOrigen);
    transaccionEntity.setCuentaDestino(cuentaDestino);
    transaccionEntity.setMontoTransferencia(form.getMonto());
    transaccionEntity.setTipoCuenta(cuentaDestino.getTipoCuenta());

    return this.save(transaccionEntity);
  }

  public Transaccion save(TransaccionEntity transaccionEntity) {
    Transaccion transaction = new Transaccion();
    transaction.setCliente(transaccionEntity.getCliente());
    transaction.setDueno(transaccionEntity.getDueno());
    transaction.setCuentaOrigen(transaccionEntity.getCuentaOrigen());
    transaction.setCuentaDestino(transaccionEntity.getCuentaDestino());
    transaction.setMontoTranferecia(transaccionEntity.getMontoTransferencia());
    transaction.setTipoCuenta(transaccionEntity.getTipoCuenta());
    transaction.setHoraTransaccion(java.time.LocalDateTime.now());
    try {
      return this.transaccionRepository.save(transaction);
    } catch (Exception e) {
      System.out.println(e);
      throw new TransactionException("Error al guardar la transacción", e);
    }
  }

  public void deleteAll() {
    transaccionRepository.deleteAll();
  }
}