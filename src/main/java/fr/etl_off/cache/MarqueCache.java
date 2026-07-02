package fr.etl_off.cache;

import fr.etl_off.model.Marque;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MarqueCache {

    private final Map<Long, Marque> cacheById = new ConcurrentHashMap<>();
    private final Map<String, Marque> cacheByNom = new ConcurrentHashMap<>();

    public Marque getById(Long id) {
        return cacheById.get(id);
    }

    public Marque getByNom(String nom) {
        return cacheByNom.get(nom);
    }

    public void put(Marque marque) {
        if (marque == null || marque.getId() == null) {
            return;
        }
        cacheById.put(marque.getId(), marque);
        if (marque.getNom() != null) {
            cacheByNom.put(marque.getNom(), marque);
        }
    }

    public void clear() {
        cacheById.clear();
        cacheByNom.clear();
    }
}
