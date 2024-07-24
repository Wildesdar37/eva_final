package com.platinum.ctacorriente.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.platinum.ctacorriente.services.UserService;
import com.platinum.ctacorriente.entities.Cliente;
import com.platinum.ctacorriente.enums.TipoCuenta;
import com.platinum.ctacorriente.forms.CuentaForm;
import com.platinum.ctacorriente.models.CtaCorriente;
import com.platinum.ctacorriente.models.Ejecutivo;
import com.platinum.ctacorriente.models.Persona;
import com.platinum.ctacorriente.models.User;
import com.platinum.ctacorriente.services.ClienteService;
import com.platinum.ctacorriente.services.CtaCorrienteService;
import com.platinum.ctacorriente.services.CuentaService;
import com.platinum.ctacorriente.services.EjecutivoService;
import com.platinum.ctacorriente.services.PersonaService;
import com.platinum.ctacorriente.services.TransaccionService;

import java.time.Duration;

@CucumberContextConfiguration
@SpringBootTest
public class LoginSteps {

    WebDriver driver;
    WebDriverWait wait;

    @Autowired
    UserService userService;

    @Autowired
    EjecutivoService ejecutivoService;

    @Autowired
    PersonaService personaService;

    @Autowired
    CtaCorrienteService ctaCorrienteService;

    @Autowired
    TransaccionService transaccionService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    CuentaService cuentaService;

    private String cuentaOrigenNumero;
    private String cuentaDestinoNumero;

    @Before
    public void setUp() {
        // WebDriverManager.chromedriver().clearDriverCache().setup();

        ChromeOptions options = new ChromeOptions();
        options.setBinary("/usr/bin/google-chrome"); // Asegúrate de que esta ruta es correcta
        // options.addArguments("--headless"); // Quita esta línea si no quieres correr
        // en modo headless
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu"); // Si tu sistema no tiene GPU
        options.addArguments("--remote-debugging-port=9222"); // Utiliza un puerto disponible
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-logging");
        options.addArguments("--log-level=3");
        options.addArguments("--output=/dev/null");
        // open screen big
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options); // Usa las opciones al crear el driver
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        this.transaccionService.deleteAll();
        this.ctaCorrienteService.deleteAll();
        this.personaService.deleteAll();
        this.ejecutivoService.deleteAll();
        this.userService.deleteAll();

        User user = this.userService.createUser("cucumber-user", "cucumber-password");
        Ejecutivo ejecutivo = this.ejecutivoService.createEjecutivo("999999-9", "cucumber-user", "cucumber-deparment",
                user);

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

    @After
    public void cleanUp() {
        this.transaccionService.deleteAll();
        this.ctaCorrienteService.deleteAll();
        this.personaService.deleteAll();
        this.ejecutivoService.deleteAll();
        this.userService.deleteAll();

        if (driver != null) {
            driver.quit();
        }
    }

    @Given("el usuario navega a la página de login")
    public void el_usuario_navega_a_la_pagina_de_login() {
        driver.get("http://localhost:8001/login");
        try {
            Thread.sleep(1); // Espera 500 milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("ingresa el nombre de usuario correcto")
    public void ingresa_el_nombre_de_usuario_correcto() {
        driver.findElement(By.id("username")).sendKeys("cucumber-user");
        driver.findElement(By.id("password")).sendKeys("cucumber-password");
        driver.findElement(By.id("loginButton")).click();
        try {
            Thread.sleep(1); // Espera 500 milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("el sistema debe permitir el acceso")
    public void el_sistema_debe_permitir_el_acceso() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-message")));
        assertTrue(driver.findElement(By.id("header-message")).isDisplayed());
        try {
            Thread.sleep(1); // Espera 500 milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("ingresa el nombre de usuario y password incorrectos")
    public void ingresa_el_nombre_de_usuario_y_password_incorrectos() {
        driver.findElement(By.id("username")).sendKeys("wrongUsername");
        driver.findElement(By.id("password")).sendKeys("wrongPassword");
        driver.findElement(By.id("loginButton")).click();
        try {
            Thread.sleep(1); // Espera 500 milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("el sistema debe mostrar un mensaje de error")
    public void el_sistema_debe_mostrar_un_mensaje_de_error() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        assertTrue(driver.findElement(By.id("error")).isDisplayed());
        try {
            Thread.sleep(1); // Espera 500 milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Given("el usuario ha iniciado sesión")
    public void el_usuario_ha_iniciado_sesion() {
        el_usuario_navega_a_la_pagina_de_login();
        ingresa_el_nombre_de_usuario_correcto();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-message")));
        try {
            Thread.sleep(1); // Espera 500 milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("realiza una transacción")
    public void realiza_una_transaccion() {
        el_usuario_ha_iniciado_sesion();
        driver.findElement(By.id("crearTransaccion")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cuentaOrigen")));
        driver.findElement(By.id("cuentaOrigen")).sendKeys(cuentaOrigenNumero);
        driver.findElement(By.id("cuentaDestino")).sendKeys(cuentaDestinoNumero);
        driver.findElement(By.id("monto")).sendKeys("100");
        driver.findElement(By.id("transaccionButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")));
        try {
            Thread.sleep(1); // Espera 500 milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("el sistema debe registrar la hora de la transacción")
    public void el_sistema_debe_registrar_la_hora_de_la_transaccion() {
        String timestamp = driver.findElement(By.id("successMessage")).getText();
        assertTrue(!timestamp.isEmpty());
        try {
            Thread.sleep(1); // Espera 500 milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
