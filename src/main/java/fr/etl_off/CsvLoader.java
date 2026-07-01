package fr.etl_off;

import fr.etl_off.model.*;
import fr.etl_off.model.Comp.CompProdAddi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvLoader {

    private static final String CSV_PATH = "/etl/open-food-facts.csv";
    private static final String SEPARATOR = "\\|";

    private final EntityManagerFactory emf;

    public CsvLoader(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void load() throws Exception {
        InputStream is = getClass().getResourceAsStream(CSV_PATH);
        if (is == null) {
            throw new IllegalStateException("CSV not found on classpath: " + CSV_PATH);
        }

        Map<String, Categorie> categories = new HashMap<>();
        Map<String, Marque> marques = new HashMap<>();
        Map<String, Additif> additifs = new HashMap<>();
        Map<String, Allergene> allergenes = new HashMap<>();
        Map<String, Ingredient> ingredients = new HashMap<>();

        EntityManager em = emf.createEntityManager();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line = reader.readLine(); // skip header
            int lineNum = 1;

            while ((line = reader.readLine()) != null) {
                lineNum++;
                String[] cols = line.split(SEPARATOR, -1);
                if (cols.length < 30) continue;

                String catNom      = trim(cols[0]);
                String marqueNom   = trim(cols[1]);
                String prodNom     = trim(cols[2]);
                String grade       = trim(cols[3]);
                String ingrsRaw    = trim(cols[4]);
                String allerRaw    = trim(cols[28]);
                String addiRaw     = trim(cols[29]);

                em.getTransaction().begin();
                try {
                    Categorie cat = getOrCreate(em, categories, catNom, Categorie.class);
                    Marque marq  = getOrCreate(em, marques, marqueNom, Marque.class);

                    Produit produit = new Produit();
                    produit.setNom(prodNom);
                    produit.setGrade(grade.isEmpty() ? null : grade);
                    produit.setCategorie(cat);
                    produit.setMarque(marq);

                    List<Ingredient> prodIngredients = new ArrayList<>();
                    if (!ingrsRaw.isEmpty()) {
                        for (String part : ingrsRaw.split(",")) {
                            String nom = sanitize(part);
                            if (!nom.isEmpty()) {
                                prodIngredients.add(getOrCreate(em, ingredients, nom, Ingredient.class));
                            }
                        }
                    }
                    produit.setIngredients(prodIngredients);

                    List<Allergene> prodAllergenes = new ArrayList<>();
                    if (!allerRaw.isEmpty()) {
                        for (String part : allerRaw.split(",")) {
                            String nom = sanitize(part);
                            if (!nom.isEmpty()) {
                                prodAllergenes.add(getOrCreate(em, allergenes, nom, Allergene.class));
                            }
                        }
                    }
                    produit.setAllergenes(prodAllergenes);

                    em.persist(produit);

                    if (!addiRaw.isEmpty()) {
                        for (String part : addiRaw.split(",")) {
                            String nom = sanitize(part);
                            if (!nom.isEmpty()) {
                                Additif additif = getOrCreate(em, additifs, nom, Additif.class);
                                CompProdAddi link = new CompProdAddi();
                                link.setProduit(produit);
                                link.setAdditif(additif);
                                link.setQteMilligrammes(0);
                                em.persist(link);
                            }
                        }
                    }

                    em.getTransaction().commit();
                } catch (Exception e) {
                    if (em.getTransaction().isActive()) em.getTransaction().rollback();
                    System.err.println("Skipping line " + lineNum + ": " + e.getMessage());
                }
            }
        } finally {
            em.close();
        }

        System.out.println("CSV import done.");
    }

    @SuppressWarnings("unchecked")
    private <T> T getOrCreate(EntityManager em, Map<String, T> cache, String nom, Class<T> clazz) throws Exception {
        if (nom == null || nom.isEmpty()) return null;
        T entity = cache.get(nom);
        if (entity != null) return entity;

        String jpql = "SELECT e FROM " + clazz.getSimpleName() + " e WHERE e.nom = :nom";
        TypedQuery<T> q = (TypedQuery<T>) em.createQuery(jpql, clazz).setParameter("nom", nom);
        List<T> results = q.getResultList();
        if (!results.isEmpty()) {
            entity = results.get(0);
        } else {
            entity = clazz.getDeclaredConstructor().newInstance();
            clazz.getMethod("setNom", String.class).invoke(entity, nom);
            em.persist(entity);
            em.flush();
        }
        cache.put(nom, entity);
        return entity;
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }

    private String sanitize(String s) {
        return s == null ? "" : s.replaceAll("_", "").trim();
    }

    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        try {
            new CsvLoader(emf).load();
        } finally {
            emf.close();
        }
    }
}
