package fr.etl_off.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

/**
 * Configuration Spring de l'application ETL-OFF.
 * Active le scan des composants et la gestion déclarative des transactions.
 */
@Configuration
@ComponentScan(basePackages = "fr.etl_off")
@EnableTransactionManagement
public class AppConfig {

    /**
     * Factory JPA basée sur l'unité de persistence {@code default} définie dans
     * {@code META-INF/persistence.xml}.
     *
     * @return la factory JPA Spring
     */
    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("default");
        return factoryBean;
    }

    /**
     * Gestionnaire de transactions JPA pour Spring.
     *
     * @param emf la factory JPA
     * @return le gestionnaire de transactions
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
