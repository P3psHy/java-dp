package fr.etl_off;

import fr.etl_off.config.AppConfig;
import fr.etl_off.service.CsvImportService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Point d'entrée de l'ETL. Démarre le contexte Spring et lance l'import CSV.
 */
public class CsvLoader {

    private static final String CSV_PATH = "/etl/open-food-facts.csv";
    private static final String SEPARATOR = "\\|";

    public static void main(String[] args) throws Exception {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            CsvImportService importService = context.getBean(CsvImportService.class);
            importService.importCsv(CSV_PATH, SEPARATOR);
        }
    }
}
