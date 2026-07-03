package fr.etl_off.service.impl;

import fr.etl_off.model.Additif;
import fr.etl_off.model.Allergene;
import fr.etl_off.model.Categorie;
import fr.etl_off.model.Ingredient;
import fr.etl_off.model.Marque;
import fr.etl_off.model.Produit;
import fr.etl_off.service.AdditifService;
import fr.etl_off.service.AllergeneService;
import fr.etl_off.service.CategorieService;
import fr.etl_off.service.CsvImportService;
import fr.etl_off.service.IngredientService;
import fr.etl_off.service.MarqueService;
import fr.etl_off.service.ProduitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Impl�mentation du service d'importation CSV.
 * Utilise les Virtual Threads (Project Loom) pour parall�liser le traitement des lignes.
 * Un semaphore limite la concurrence pour ne pas saturer le pool de connexions JPA.
 */
@Service
public class CsvImportServiceImpl implements CsvImportService {

    private static final int MIN_COLUMNS = 30;
    private static final int IDX_CATEGORIE = 0;
    private static final int IDX_MARQUE = 1;
    private static final int IDX_NOM = 2;
    private static final int IDX_GRADE = 3;
    private static final int IDX_INGREDIENTS = 4;
    private static final int IDX_ALLERGENES = 28;
    private static final int IDX_ADDITIFS = 29;

    /** Limite le nombre de threads actifs simultan�ment pour ne pas d�passer le pool de connexions. */
    private static final int MAX_CONCURRENT = 10;

    private final ProduitService produitService;
    private final CategorieService categorieService;
    private final MarqueService marqueService;
    private final IngredientService ingredientService;
    private final AllergeneService allergeneService;
    private final AdditifService additifService;

    public CsvImportServiceImpl(ProduitService produitService,
                                CategorieService categorieService,
                                MarqueService marqueService,
                                IngredientService ingredientService,
                                AllergeneService allergeneService,
                                AdditifService additifService) {
        this.produitService = produitService;
        this.categorieService = categorieService;
        this.marqueService = marqueService;
        this.ingredientService = ingredientService;
        this.allergeneService = allergeneService;
        this.additifService = additifService;
    }

    @Override
    public int importCsv(String csvPath, String separator) throws Exception {
        long startTime = System.currentTimeMillis();
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        long cpuStart = threadBean.getCurrentThreadCpuTime();

        AtomicInteger processed = new AtomicInteger(0);
        AtomicInteger skipped = new AtomicInteger(0);
        Semaphore semaphore = new Semaphore(MAX_CONCURRENT);
        List<java.util.concurrent.Future<?>> futures = new ArrayList<>();

        try (InputStream is = openStream(csvPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            reader.readLine(); // skip header
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                final String currentLine = line;
                final int currentNum = lineNum;
                semaphore.acquire();
                futures.add(executor.submit(() -> {
                    try {
                        String[] cols = currentLine.split(separator, -1);
                        if (cols.length < MIN_COLUMNS) {
                            skipped.incrementAndGet();
                            return;
                        }
                        Produit produit = buildProduit(cols);
                        produitService.create(produit);
                        processed.incrementAndGet();
                    } catch (Exception e) {
                        skipped.incrementAndGet();
                        System.err.println("Skipping line " + currentNum + ": " + e.getMessage());
                    } finally {
                        semaphore.release();
                    }
                }));
            }
        }

        long elapsed = System.currentTimeMillis() - startTime;
        long cpuMs = (threadBean.getCurrentThreadCpuTime() - cpuStart) / 1_000_000;
        Runtime rt = Runtime.getRuntime();
        long usedMemMb = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);

        System.out.printf("=== ETL Performance Report ===%n");
        System.out.printf("  Products processed : %d%n", processed.get());
        System.out.printf("  Lines skipped      : %d%n", skipped.get());
        System.out.printf("  Total time         : %d ms (%.2f s)%n", elapsed, elapsed / 1000.0);
        System.out.printf("  CPU time (main)    : %d ms%n", cpuMs);
        System.out.printf("  Heap used          : %d MB%n", usedMemMb);
        System.out.printf("  Active threads     : %d%n", Thread.activeCount());
        System.out.printf("===============================%n");

        return processed.get();
    }

    private InputStream openStream(String csvPath) throws IOException {
        InputStream is = getClass().getResourceAsStream(csvPath);
        if (is == null) {
            throw new IOException("CSV not found on classpath: " + csvPath);
        }
        return is;
    }

    @Override
    @Transactional
    public Produit buildProduit(String[] cols) {
        String catNom = clean(cols[IDX_CATEGORIE]);
        String marqueNom = clean(cols[IDX_MARQUE]);
        String prodNom = clean(cols[IDX_NOM]);
        String grade = clean(cols[IDX_GRADE]);

        Categorie categorie = categorieService.getOrCreate(catNom);
        Marque marque = marqueService.getOrCreate(marqueNom);

        Produit produit = new Produit();
        produit.setNom(prodNom);
        produit.setGrade(grade.isEmpty() ? null : grade);
        produit.setCategorie(categorie);
        produit.setMarque(marque);

        produit.setIngredients(resolveIngredients(splitAndClean(cols[IDX_INGREDIENTS])));
        produit.setAllergenes(resolveAllergenes(splitAndClean(cols[IDX_ALLERGENES])));
        produit.setAdditifs(resolveAdditifs(splitAndClean(cols[IDX_ADDITIFS])));

        return produit;
    }

    private List<Ingredient> resolveIngredients(List<String> noms) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (String nom : noms) {
            Ingredient ingredient = ingredientService.getOrCreate(nom);
            if (ingredient != null) {
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }

    private List<Allergene> resolveAllergenes(List<String> noms) {
        List<Allergene> allergenes = new ArrayList<>();
        for (String nom : noms) {
            Allergene allergene = allergeneService.getOrCreate(nom);
            if (allergene != null) {
                allergenes.add(allergene);
            }
        }
        return allergenes;
    }

    private List<Additif> resolveAdditifs(List<String> noms) {
        List<Additif> additifs = new ArrayList<>();
        for (String nom : noms) {
            Additif additif = additifService.getOrCreate(nom);
            if (additif != null) {
                additifs.add(additif);
            }
        }
        return additifs;
    }

    @Override
    public List<String> splitAndClean(String raw) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isBlank(raw)) {
            return result;
        }
        for (String part : raw.split("[,;|\\-]")) {
            String cleaned = sanitize(part);
            if (!cleaned.isEmpty()) {
                result.add(cleaned);
            }
        }
        return result;
    }

    /**
     * Nettoie une cha�ne de caract�res : trim, retrait des underscores, ast�risques,
     * des contenus entre parenth�ses et des pourcentages.
     *
     * @param input la cha�ne brute
     * @return la cha�ne nettoy�e
     */
    private String sanitize(String input) {
        if (input == null) {
            return "";
        }
        return input
                .replaceAll("\\([^)]*\\)", "")
                .replaceAll("\\d+\\s*%", "")
                .replaceAll("[%*_]", "")
                .trim();
    }

    private String clean(String value) {
        return StringUtils.trimToEmpty(value);
    }
}
