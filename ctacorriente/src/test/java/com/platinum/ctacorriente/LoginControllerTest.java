package com.platinum.ctacorriente;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;

import com.platinum.ctacorriente.repositories.CtaCorrienteRepository;
import com.platinum.ctacorriente.repositories.EjecutivoRepository;
import com.platinum.ctacorriente.repositories.PersonaRepository;
import com.platinum.ctacorriente.repositories.TransaccionRepository;
import com.platinum.ctacorriente.repositories.UserRepository;
import com.platinum.ctacorriente.services.UserService;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@SpringJUnitConfig
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.26")
            .withDatabaseName("cuentas_clientes")
            .withUsername("admin")
            .withPassword("admin");

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private CtaCorrienteRepository ctaCorrienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private EjecutivoRepository ejecutivoRepository;

    @Autowired
    private UserService userService;

    @BeforeAll
    static void beforeAll() {
        mysql.start();
    }

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
    }

    @BeforeEach
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        // Limpia la base de datos antes de cada prueba
        transaccionRepository.deleteAll();
        ctaCorrienteRepository.deleteAll();
        personaRepository.deleteAll();
        ejecutivoRepository.deleteAll();
        userRepository.deleteAll();

        // Crea un usuario de prueba
        userService.createUser("testuser", "123456");
    }

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testuser", password = "123456", roles = "USER")
    void testLoginWithValidUser() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("testuser")
                .password("123456"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    void testLoginWithInvalidUser() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("invaliduser")
                .password("invalidpassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> assertTrue(result.getResponse().getRedirectedUrl().contains("error=true")))
                .andReturn();
    }
}
