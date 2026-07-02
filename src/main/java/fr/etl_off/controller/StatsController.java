package fr.etl_off.controller;

import fr.etl_off.service.AdditifService;
import fr.etl_off.service.AllergeneService;
import fr.etl_off.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller REST exposant les endpoints statistiques
 * (ingrédients, allergènes et additifs les plus courants).
 */
@RestController
public class StatsController {

    private final IngredientService ingredientService;
    private final AllergeneService allergeneService;
    private final AdditifService additifService;

    public StatsController(IngredientService ingredientService,
                           AllergeneService allergeneService,
                           AdditifService additifService) {
        this.ingredientService = ingredientService;
        this.allergeneService = allergeneService;
        this.additifService = additifService;
    }

    /**
     * Retourne les N ingrédients les plus courants.
     *
     * @param limit le nombre de résultats souhaités
     * @return la liste des ingrédients avec leur nombre d'occurrences
     */
    @GetMapping("/ingredients/top")
    public ResponseEntity<List<Object[]>> topIngredients(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ingredientService.findTop(limit));
    }

    /**
     * Retourne les N allergènes les plus courants.
     *
     * @param limit le nombre de résultats souhaités
     * @return la liste des allergènes avec leur nombre d'occurrences
     */
    @GetMapping("/allergens/top")
    public ResponseEntity<List<Object[]>> topAllergenes(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(allergeneService.findTop(limit));
    }

    /**
     * Retourne les N additifs les plus courants.
     *
     * @param limit le nombre de résultats souhaités
     * @return la liste des additifs avec leur nombre d'occurrences
     */
    @GetMapping("/additives/top")
    public ResponseEntity<List<Object[]>> topAdditifs(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(additifService.findTop(limit));
    }
}
