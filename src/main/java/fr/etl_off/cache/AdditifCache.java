package fr.etl_off.cache;

import fr.etl_off.model.Additif;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AdditifCache {

    private final Map<Long, Additif> cacheById = new ConcurrentHashMap<>();
    private final Map<String, Additif> cacheByNom = new ConcurrentHashMap<>();

    public Additif getById(Long id) {
        return cacheById.get(id);
    }

    public Additif getByNom(String nom) {
        return cacheByNom.get(nom);
    }

    public void put(Additif additif) {
        if (additif == null || additif.getId() == null) {
            return;
        }
        cacheById.put(additif.getId(), additif);
        if (additif.getNom() != null) {
            cacheByNom.put(additif.getNom(), additif);
        }
    }

    public void clear() {
        cacheById.clear();
        cacheByNom.clear();
    }
}
