package fr.etl_off.cache;

import fr.etl_off.model.Allergene;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AllergeneCache {

    private final Map<Long, Allergene> cacheById = new ConcurrentHashMap<>();
    private final Map<String, Allergene> cacheByNom = new ConcurrentHashMap<>();

    public Allergene getById(Long id) {
        return cacheById.get(id);
    }

    public Allergene getByNom(String nom) {
        return cacheByNom.get(nom);
    }

    public void put(Allergene allergene) {
        if (allergene == null || allergene.getId() == null) {
            return;
        }
        cacheById.put(allergene.getId(), allergene);
        if (allergene.getNom() != null) {
            cacheByNom.put(allergene.getNom(), allergene);
        }
    }

    public void clear() {
        cacheById.clear();
        cacheByNom.clear();
    }
}
