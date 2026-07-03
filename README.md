# ETL Open Food Facts — Backend Java

Victor Nourisson
Martin Chanudet

> Projet réalisé en binôme dans le cadre du cours **Optimisation Backend avec Java** (M2).

## Description

ETL (Extract-Transform-Load) en Java qui ingère le fichier CSV Open Food Facts (13 432 produits alimentaires fabriqués en France), nettoie les données et les expose via une API REST Spring Boot.

## Technologies

- Java 21 (Virtual Threads — Project Loom)
- Spring Boot 3.3 (Web, Data JPA)
- Hibernate 6 / Jakarta Persistence 3.1
- PostgreSQL 16
- Docker / Docker Compose
- Maven

## Optimisations mises en place

- **Cache mémoire** (`ConcurrentHashMap`) sur toutes les entités partagées (catégories, marques, ingrédients, allergènes, additifs) pour éviter les requêtes SQL répétées
- **Virtual Threads** (Project Loom, Java 21) via `Executors.newVirtualThreadPerTaskExecutor()` pour paralléliser le traitement des lignes CSV
- **Semaphore** limitant la concurrence à 10 threads actifs simultanément pour ne pas saturer le pool de connexions JPA
- **Pool de connexions** géré par Spring Boot / HikariCP (défaut)

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

## Lancer l'API REST

### 1. S'assurer que les données sont importées

Si ce n'est pas déjà fait, lance l'ETL (voir section [Lancer l'ETL](#lancer-letl)) :

```powershell
.\reset-and-run.ps1
```

### 2. Démarrer l'application Spring Boot

```powershell
mvn spring-boot:run
```

L'API démarre sur **`http://localhost:8082`** (le port 8080 est occupé par Docker/WSL).

### 3. Tester les routes avec Swagger UI

Ouvre dans ton navigateur :

```
http://localhost:8082/swagger-ui/index.html
```

Tu peux y lister tous les endpoints, voir les paramètres et exécuter les requêtes directement.

### 4. Tester avec curl (optionnel)

```powershell
curl "http://localhost:8082/products/top-by-brand?brand=Lagrange&limit=5"
curl "http://localhost:8082/products/top-by-category?category=Sucres&limit=5"
curl "http://localhost:8082/ingredients/top?limit=10"
```

### 5. Arrêter l'application

Appuie sur `Ctrl+C` dans le terminal, ou force la fermeture du processus sur le port 8082 :

```powershell
Get-NetTCPConnection -LocalPort 8082 | ForEach-Object { Stop-Process -Id $_.OwningProcess -Force }
```

### Si le port 8082 est déjà occupé

Tu peux changer le port dans `src/main/resources/application.properties` :

```properties
server.port=8083
```

Puis relance `mvn spring-boot:run`.

---

## Structure du projet

```
src/
├── main/
│   ├── java/fr/etl_off/
│   │   ├── cache/             # Caches mémoire (ConcurrentHashMap)
│   │   ├── controller/        # Controllers REST
│   │   ├── model/             # Entités JPA
│   │   ├── repository/        # Repositories Spring Data JPA
│   │   ├── service/           # Interfaces métier
│   │   │   └── impl/          # Implémentations
│   │   ├── CsvLoader.java     # Point d'entrée ETL
│   │   └── EtlOffApplication.java  # Point d'entrée Spring Boot
│   └── resources/
│       ├── etl/               # Fichier CSV à placer ici
│       └── application.properties
conception/                    # Diagrammes UML et MLD
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
