package fr.etl_off.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration Spring MVC.
 * Active le support @RestController et la sérialisation JSON via Jackson.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "fr.etl_off.controller")
public class WebConfig implements WebMvcConfigurer {
}
