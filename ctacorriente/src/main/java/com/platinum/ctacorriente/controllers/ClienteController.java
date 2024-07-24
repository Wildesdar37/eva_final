package com.platinum.ctacorriente.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.platinum.ctacorriente.entities.Cliente;
import com.platinum.ctacorriente.forms.CuentaForm;
import com.platinum.ctacorriente.forms.PersonaForm;
import com.platinum.ctacorriente.forms.TransaccionForm;
import com.platinum.ctacorriente.models.CtaCorriente;
import com.platinum.ctacorriente.models.Transaccion;
import com.platinum.ctacorriente.services.ClienteService;
import com.platinum.ctacorriente.services.CuentaService;
import com.platinum.ctacorriente.services.TransaccionService;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClienteController {

    private final ClienteService clienteService;
    private final CuentaService cuentaService;
    private final TransaccionService transaccionService;

    public ClienteController(ClienteService clienteService, CuentaService cuentaService,
            TransaccionService transaccionService) {
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
        this.transaccionService = transaccionService;

    }

    @GetMapping("/crear-cliente")
    public String crearCliente(Model model) {
        model.addAttribute("personaForm", new PersonaForm());
        return "crear-cliente";
    }

    @PostMapping("/crear-cliente")
    public String crearCliente(PersonaForm form) {
        // Crear objeto User
        Cliente cliente = new Cliente(
                form.getRut(), form.getNombre(), form.getApellido(), form.getEmail(), form.getDireccion(),
                form.getTelefono(), form.getUsername(), form.getPassword());

        this.clienteService.createCliente(cliente);

        return "redirect:/";
    }

    @GetMapping("/cuentas-cliente")
    public String getCuentasCliente(@RequestParam("rutCliente") String rutCliente, Model model) {
        List<CtaCorriente> cuentas = clienteService.cuentasCliente(rutCliente);
        model.addAttribute("cuentas", cuentas);
        return "cuentas-cliente";
    }

    @GetMapping("/perfil")
    public String perfil(Model model) {
        // lógica para mostrar el perfil del usuario
        return "perfil";
    }

    @GetMapping("/register-cta")
    public String mostrarFormularioCuenta(@RequestParam("rutCliente") String rutCliente, Model model) {
        CuentaForm cuentaForm = new CuentaForm();
        cuentaForm.setRutCliente(rutCliente);
        model.addAttribute("cuentaForm", cuentaForm);
        return "register-cta";
    }

    @PostMapping("/register-cta")
    public String crearCuenta(CuentaForm cuentaForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        cuentaService.createCuenta(cuentaForm, username);
        return "redirect:/";
    }

    @GetMapping("/transferencia")
    public String mostrarFormularioTransferencia(Model model) {
        model.addAttribute("transferenciaForm", new TransaccionForm());
        return "transferencia";
    }

    @PostMapping("/transferir")
    public String realizarTransferencia(TransaccionForm form, Model model) {
        try {
            Transaccion transaccion = transaccionService.transferir(form);
            model.addAttribute("successMessage", "Transferencia realizada exitosamente a las " + transaccion.getHoraTransaccion());
            model.addAttribute("transferenciaForm", new TransaccionForm());
            return "transferencia";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("transferenciaForm", new TransaccionForm());
            return "transferencia";
        }
    }
}
