package fr.etl_off.service.impl;

import fr.etl_off.dao.IngredientDao;
import fr.etl_off.model.Ingredient;
import fr.etl_off.service.IngredientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation du service métier pour les ingrédients.
 */
@Service
@Transactional
public class IngredientServiceImpl extends AbstractGenericService<Ingredient, Long> implements IngredientService {

    private final IngredientDao ingredientDao;

    public IngredientServiceImpl(IngredientDao ingredientDao) {
        super(ingredientDao);
        this.ingredientDao = ingredientDao;
    }

    @Override
    @Transactional
    public Ingredient getOrCreate(String nom) {
        if (nom == null || nom.isEmpty()) {
            return null;
        }
        return ingredientDao.findByNom(nom)
                .orElseGet(() -> ingredientDao.save(newIngredient(nom)));
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
