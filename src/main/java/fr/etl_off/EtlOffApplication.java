package fr.etl_off;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot.
 * Démarre le contexte Spring et expose l'API REST.
 */
@SpringBootApplication
public class EtlOffApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtlOffApplication.class, args);
    }
}
