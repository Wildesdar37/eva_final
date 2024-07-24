Feature: Login functionality

Scenario: Ingreso de nombre de usuario con datos almacenados en BD
    Given el usuario navega a la página de login
    When ingresa el nombre de usuario correcto
    Then el sistema debe permitir el acceso

Scenario: Ingreso de nombre de usuario y password con datos erróneos
    Given el usuario navega a la página de login
    When ingresa el nombre de usuario y password incorrectos
    Then el sistema debe mostrar un mensaje de error

Scenario: Registro de hora transaccion
    Given el usuario ha iniciado sesión
    When realiza una transacción
    Then el sistema debe registrar la hora de la transacción
