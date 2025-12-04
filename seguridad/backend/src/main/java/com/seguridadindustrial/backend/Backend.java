package com.seguridadindustrial.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Backend {

    // Logger principal de la aplicación
    private static final Logger logger = LoggerFactory.getLogger(Backend.class);

    public static void main(String[] args) {
        SpringApplication.run(Backend.class, args);
        logger.info("✅ Backend de Seguridad Industrial ejecutándose en puerto 8081");
    }
}
