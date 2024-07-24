package com.platinum.ctacorriente;

import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;



@SpringBootTest
@ActiveProfiles("test")
class DatabaseConnectionTest {
  
  @Autowired
  private DataSource dataSource;


  @Test
  void testConnection() throws Exception {
    try (Connection connection = dataSource.getConnection()) {
      assertNotNull(connection);
      System.out.println("Connection successful!");
    }

  }

}
