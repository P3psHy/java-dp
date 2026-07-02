package fr.etl_off.service;

import fr.etl_off.model.Additif;
import fr.etl_off.model.Allergene;
import fr.etl_off.model.Categorie;
import fr.etl_off.model.Ingredient;
import fr.etl_off.model.Marque;
import fr.etl_off.model.Produit;

import java.util.List;

/**
 * Service d'importation responsable de l'ETL (Extract-Transform-Load)
 * du fichier Open Food Facts.
 */
public interface CsvImportService {

    /**
     * Importe un fichier CSV depuis le classpath et persiste les données nettoyées.
     *
     * @param csvPath   le chemin du fichier sur le classpath
     * @param separator le séparateur de colonnes (regex)
     * @return le nombre de lignes traitées avec succès
     * @throws Exception en cas d'erreur d'entrée/sortie ou de persistence
     */
    int importCsv(String csvPath, String separator) throws Exception;

    /**
     * Construit un produit à partir d'une ligne CSV déjà découpée.
     *
     * @param cols les colonnes de la ligne CSV
     * @return le produit assemblé
     */
    Produit buildProduit(String[] cols);

    /**
     * Nettoie et découpe une chaîne brute en une liste de libellés normalisés.
     *
     * @param raw la chaîne brute (ingrédients, allergènes, additifs)
     * @return la liste des libellés nettoyés
     */
    List<String> splitAndClean(String raw);
}
