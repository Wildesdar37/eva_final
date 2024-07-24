package com.platinum.ctacorriente;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;

import com.platinum.ctacorriente.entities.Cliente;
import com.platinum.ctacorriente.enums.TipoCuenta;
import com.platinum.ctacorriente.forms.CuentaForm;
import com.platinum.ctacorriente.models.CtaCorriente;
import com.platinum.ctacorriente.models.Ejecutivo;
import com.platinum.ctacorriente.models.Persona;
import com.platinum.ctacorriente.models.User;
import com.platinum.ctacorriente.repositories.CtaCorrienteRepository;
import com.platinum.ctacorriente.repositories.EjecutivoRepository;
import com.platinum.ctacorriente.repositories.PersonaRepository;
import com.platinum.ctacorriente.repositories.TransaccionRepository;
import com.platinum.ctacorriente.repositories.UserRepository;
import com.platinum.ctacorriente.services.ClienteService;
import com.platinum.ctacorriente.services.CuentaService;
import com.platinum.ctacorriente.services.EjecutivoService;
import com.platinum.ctacorriente.services.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransaccionControllerTest {

    static MySQLContainer<?> mysql = new MySQLContainer<>(
            "mysql:8.4.0").withDatabaseName("cuentas_clientes").withPassword("admin").withUsername("admin")
            .withExposedPorts(3307, 3306);

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private EjecutivoRepository ejecutivoRepository;

    @Autowired
    private CtaCorrienteRepository ctaCorrienteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EjecutivoService ejecutivoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private MockMvc mockMvc;

    private String cuentaOrigenNumero;
    private String cuentaDestinoNumero;

    @BeforeAll
    static void beforeAll() {
        mysql.start();
        System.out.println("MySQL container started: " + mysql.isRunning());
        System.out.println("JDBC URL: " + mysql.getJdbcUrl());
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
    }

    @BeforeEach
    public void setup() {
        // Inicializa MockMvc con Spring Security
        mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        // Limpia la base de datos antes de cada prueba
        transaccionRepository.deleteAll();
        ctaCorrienteRepository.deleteAll();
        personaRepository.deleteAll();
        ejecutivoRepository.deleteAll();
        userRepository.deleteAll();

        // Crea datos de prueba
        User ejecutivoUser = userService.createUser("ejecutivo_user", "password");
        Ejecutivo ejecutivo = ejecutivoService.createEjecutivo("999999-9", "user_ejecutivo", "tesorreria",
                ejecutivoUser);

        Cliente cliente1 = new Cliente("99999-9", "cliente1", "cliente1", "cliente1@mail.com", "test address", 9999999,
                "cliente1", "cliente1");
        Cliente cliente2 = new Cliente("99999-0", "cliente2", "cliente2", "cliente2@mail.com", "test address", 9999999,
                "cliente2", "cliente2");

        Persona cliente1Register = clienteService.createCliente(cliente1);
        Persona cliente2Register = clienteService.createCliente(cliente2);

        CuentaForm cuenta1 = new CuentaForm();
        cuenta1.setMonto(10000);
        cuenta1.setTipoCuenta(TipoCuenta.AHORROS);
        cuenta1.setRutCliente(cliente1Register.getRut());

        CuentaForm cuenta2 = new CuentaForm();
        cuenta2.setMonto(10000);
        cuenta2.setTipoCuenta(TipoCuenta.AHORROS);
        cuenta2.setRutCliente(cliente2Register.getRut());

        CtaCorriente cuentaOrigen = cuentaService.createCuenta(cuenta1, ejecutivo.getUser().getUsername());
        CtaCorriente cuentaDestino = cuentaService.createCuenta(cuenta2, ejecutivo.getUser().getUsername());

        // Captura los números de cuenta generados
        cuentaOrigenNumero = cuentaOrigen.getNumeroCuenta();
        cuentaDestinoNumero = cuentaDestino.getNumeroCuenta();
    }

    @Test
    @WithMockUser(username = "persona_user", roles = "USER")
    void testRealizarTransferenciaConFondosSuficientes() throws Exception {
        MvcResult result = mockMvc.perform(post("/transferir")
                .param("cuentaOrigen", cuentaOrigenNumero)
                .param("cuentaDestino", cuentaDestinoNumero)
                .param("monto", "1000"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("successMessage"))
                .andExpect(model().attributeDoesNotExist("error"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "persona_user", roles = "USER")
    void testRealizarTransferenciaConFondosInsuficientes() throws Exception {
        MvcResult result = mockMvc.perform(post("/transferir")
                .param("cuentaOrigen", cuentaOrigenNumero)
                .param("cuentaDestino", cuentaDestinoNumero)
                .param("monto", "99999999999999"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeDoesNotExist("successMessage"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

}
