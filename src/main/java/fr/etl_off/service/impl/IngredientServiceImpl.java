package fr.etl_off.service.impl;

import fr.etl_off.cache.IngredientCache;
import fr.etl_off.repository.IngredientRepository;
import org.springframework.data.domain.PageRequest;
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

    private final IngredientRepository ingredientRepository;
    private final IngredientCache ingredientCache;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientCache ingredientCache) {
        super(ingredientRepository);
        this.ingredientRepository = ingredientRepository;
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
        Ingredient ingredient = ingredientRepository.findByNom(nom)
                .orElseGet(() -> ingredientRepository.save(newIngredient(nom)));
        ingredientCache.put(ingredient);
        return ingredient;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findTop(int limit) {
        return ingredientRepository.findTopIngredients(PageRequest.of(0, limit));
    }

    private Ingredient newIngredient(String nom) {
        Ingredient ingredient = new Ingredient();
        ingredient.setNom(nom);
        return ingredient;
    }
}