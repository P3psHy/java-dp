package fr.etl_off.service.impl;

import fr.etl_off.cache.IngredientCache;
import fr.etl_off.dao.IngredientDao;
import fr.etl_off.model.Ingredient;
import fr.etl_off.service.IngredientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation du service métier pour les ingrédients.
 * Utilise un cache mémoire pour éviter les requêtes SQL répétées.
 */
@Service
@Transactional
public class IngredientServiceImpl extends AbstractGenericService<Ingredient, Long> implements IngredientService {

    private final IngredientDao ingredientDao;
    private final IngredientCache ingredientCache;

    public IngredientServiceImpl(IngredientDao ingredientDao, IngredientCache ingredientCache) {
        super(ingredientDao);
        this.ingredientDao = ingredientDao;
        this.ingredientCache = ingredientCache;
    }

    @Override
    @Transactional
    public synchronized Ingredient getOrCreate(String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        Ingredient cached = ingredientCache.getByNom(nom);
        if (cached != null) {
            return cached;
        }
        Ingredient ingredient = ingredientDao.findByNom(nom)
                .orElseGet(() -> ingredientDao.save(newIngredient(nom)));
        ingredientCache.put(ingredient);
        return ingredient;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findTop(int limit) {
        return ingredientDao.findTopIngredients(limit);
    }

    private Ingredient newIngredient(String nom) {
        Ingredient ingredient = new Ingredient();
        ingredient.setNom(nom);
        return ingredient;
    }
}