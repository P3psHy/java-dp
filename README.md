# ETL Open Food Facts — Backend Java

> Projet réalisé en binôme dans le cadre du cours **Optimisation Backend avec Java** (M2).

## Description

ETL (Extract-Transform-Load) en Java qui ingère le fichier CSV Open Food Facts (13 432 produits alimentaires fabriqués en France), nettoie les données et les expose via une API REST Spring Boot.

## Technologies

- Java 21 (Virtual Threads — Project Loom)
- Spring Framework 5.3 (Context, ORM, WebMVC)
- Hibernate 5.6 / JPA 2.2
- PostgreSQL 16
- Docker / Docker Compose
- Maven

## Optimisations mises en place

- **Cache mémoire** (`ConcurrentHashMap`) sur toutes les entités partagées (catégories, marques, ingrédients, allergènes, additifs) pour éviter les requêtes SQL répétées
- **Virtual Threads** (Project Loom, Java 21) via `Executors.newVirtualThreadPerTaskExecutor()` pour paralléliser le traitement des lignes CSV
- **Semaphore** limitant la concurrence à 10 threads actifs simultanément pour ne pas saturer le pool de connexions JPA
- **Pool de connexions** configuré à 20 connexions dans `persistence.xml`

---

## Prérequis

- Java 21+
- Maven 3.8+
- Docker Desktop

---

## Initialisation de Docker

### 1. Démarrer le container PostgreSQL

```powershell
docker-compose up -d
```

Vérifie que le container tourne :

```powershell
docker ps
```

Tu dois voir `java-dp-postgres` avec le port `5433->5432`.

### 2. Connexion à la base (optionnel)

```powershell
docker exec -it java-dp-postgres psql -U postgres -d TON_DB
```

Commandes psql utiles :
```sql
\dt                          -- liste les tables
SELECT COUNT(*) FROM produit;
\q                           -- quitter
```

### 3. Arrêter le container

```powershell
docker-compose down
```

---

## Lancer l'ETL

### Placer le fichier CSV

Le fichier `open-food-facts.csv` doit être placé dans :

```
src/main/resources/etl/open-food-facts.csv
```

### Lancement simple

```powershell
mvn compile exec:java
```

### Lancement avec remise à zéro de la base (recommandé)

Ce script vide complètement la base puis relance l'ETL :

```powershell
.\reset-and-run.ps1
```

Ou manuellement :

```powershell
# 1. Vider la base
docker exec java-dp-postgres psql -U postgres -d TON_DB -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public; GRANT ALL ON SCHEMA public TO postgres;"

# 2. Lancer l'ETL
mvn compile exec:java
```

### Rapport de performance

À la fin du traitement, l'ETL affiche automatiquement :

```
=== ETL Performance Report ===
  Products processed : 13XXX
  Lines skipped      : X
  Total time         : XXXX ms (X.XX s)
  CPU time (main)    : XXXX ms
  Heap used          : XX MB
  Active threads     : X
===============================
```

---

## Structure du projet

```
src/
├── main/
│   ├── java/fr/etl_off/
│   │   ├── cache/          # Caches mémoire (ConcurrentHashMap)
│   │   ├── config/         # Configuration Spring
│   │   ├── dao/            # Pattern DAO (une DAO par entité)
│   │   ├── model/          # Entités JPA
│   │   ├── service/        # Interfaces métier
│   │   │   └── impl/       # Implémentations
│   │   └── CsvLoader.java  # Point d'entrée ETL
│   └── resources/
│       ├── etl/            # Fichier CSV à placer ici
│       └── META-INF/
│           └── persistence.xml
conception/                 # Diagrammes UML et MLD
```

---

## API REST

| Endpoint | Description |
|---|---|
| `GET /products/top-by-brand?brand=X&limit=N` | Top N produits d'une marque |
| `GET /products/top-by-category?category=X&limit=N` | Top N produits d'une catégorie |
| `GET /products/top-by-brand-category?brand=X&category=Y&limit=N` | Top N produits par marque et catégorie |
| `GET /ingredients/top?limit=N` | N ingrédients les plus courants |
| `GET /allergens/top?limit=N` | N allergènes les plus courants |
| `GET /additives/top?limit=N` | N additifs les plus courants |
