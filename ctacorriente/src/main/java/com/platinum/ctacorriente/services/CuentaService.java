package com.platinum.ctacorriente.services;

import org.springframework.stereotype.Service;

import com.platinum.ctacorriente.forms.CuentaForm;
import com.platinum.ctacorriente.models.CtaCorriente;
import com.platinum.ctacorriente.models.Ejecutivo;
import com.platinum.ctacorriente.models.Persona;
import com.platinum.ctacorriente.models.User;
import com.platinum.ctacorriente.repositories.CtaCorrienteRepository;
import com.platinum.ctacorriente.repositories.PersonaRepository;
import com.platinum.ctacorriente.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CuentaService {

    private final CtaCorrienteRepository ctaCorrienteRepository;
    private final UserRepository userRepository;
    private final PersonaRepository personaRepository;

    public CuentaService(CtaCorrienteRepository ctaCorrienteRepository, PersonaRepository personaRepository,
            UserRepository userRepository) {
        this.ctaCorrienteRepository = ctaCorrienteRepository;
        this.personaRepository = personaRepository;
        this.userRepository = userRepository;
    }

    public CtaCorriente createCuenta(CuentaForm cuentaForm, String username) {
        Persona cliente = personaRepository.findById(cuentaForm.getRutCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        User user = userRepository.findByUsername(username);
        Ejecutivo ejecutivo = user.getEjecutivo();

        CtaCorriente cuenta = new CtaCorriente();
        cuenta.setPersona(cliente);
        cuenta.setMonto(cuentaForm.getMonto());
        cuenta.setTipoCuenta(cuentaForm.getTipoCuenta());
        cuenta.setNumeroCuenta(UUID.randomUUID().toString());
        cuenta.setEjecutivo(ejecutivo);

        return ctaCorrienteRepository.save(cuenta);
    }

    public List<CtaCorriente> findAllByEjecutivo(Ejecutivo ejecutivo) {
        return ctaCorrienteRepository.findAllByEjecutivo(ejecutivo);
    }

    public List<CtaCorriente> findAllByPersonaRut(String rutCliente) {
        return ctaCorrienteRepository.findAllByPersonaRut(rutCliente);
    }

}