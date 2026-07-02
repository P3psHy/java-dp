package fr.etl_off.cache;

import fr.etl_off.model.Categorie;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CategorieCache {

    private final Map<Long, Categorie> cacheById = new ConcurrentHashMap<>();
    private final Map<String, Categorie> cacheByNom = new ConcurrentHashMap<>();

    public Categorie getById(Long id) {
        return cacheById.get(id);
    }

    public Categorie getByNom(String nom) {
        return cacheByNom.get(nom);
    }

    public void put(Categorie categorie) {
        if (categorie == null || categorie.getId() == null) {
            return;
        }
        cacheById.put(categorie.getId(), categorie);
        if (categorie.getNom() != null) {
            cacheByNom.put(categorie.getNom(), categorie);
        }
    }

    public void clear() {
        cacheById.clear();
        cacheByNom.clear();
    }
}
