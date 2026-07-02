package fr.etl_off.cache;

import fr.etl_off.model.Ingredient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IngredientCache {

    private final Map<Long, Ingredient> cacheById = new ConcurrentHashMap<>();
    private final Map<String, Ingredient> cacheByNom = new ConcurrentHashMap<>();

    public Ingredient getById(Long id) {
        return cacheById.get(id);
    }

    public Ingredient getByNom(String nom) {
        return cacheByNom.get(nom);
    }

    public void put(Ingredient ingredient) {
        if (ingredient == null || ingredient.getId() == null) {
            return;
        }
        cacheById.put(ingredient.getId(), ingredient);
        if (ingredient.getNom() != null) {
            cacheByNom.put(ingredient.getNom(), ingredient);
        }
    }

    public void clear() {
        cacheById.clear();
        cacheByNom.clear();
    }
}
