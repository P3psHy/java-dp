package fr.etl_off;

import fr.etl_off.service.CsvImportService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Point d'entrée de l'ETL. Démarre un contexte Spring Boot (sans serveur web) et lance l'import CSV.
 */
public class CsvLoader {

    private static final String CSV_PATH = "/etl/open-food-facts.csv";
    private static final String SEPARATOR = "\\|";

    public static void main(String[] args) throws Exception {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(EtlOffApplication.class)
                .web(WebApplicationType.NONE)
                .run(args)) {
            CsvImportService importService = context.getBean(CsvImportService.class);
            importService.importCsv(CSV_PATH, SEPARATOR);
        }
    }
}
