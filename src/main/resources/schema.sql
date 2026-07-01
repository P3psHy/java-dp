CREATE TABLE IF NOT EXISTS marque (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS categorie (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS produit (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255),
    grade VARCHAR(255),
    marque_id BIGINT,
    categorie_id BIGINT,
    CONSTRAINT fk_produit_marque FOREIGN KEY (marque_id) REFERENCES marque(id),
    CONSTRAINT fk_produit_categorie FOREIGN KEY (categorie_id) REFERENCES categorie(id)
);

CREATE TABLE IF NOT EXISTS additif (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) UNIQUE,
    qte_milligrammes DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS allergene (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) UNIQUE NOT NULL,
    qte_milligrammes DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS ingredient (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) UNIQUE NOT NULL,
    qte_milligrammes DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS comp_prod_addi (
    id BIGSERIAL PRIMARY KEY,
    qte_milligrammes DOUBLE PRECISION,
    produit_id BIGINT,
    additif_id BIGINT,
    CONSTRAINT fk_comp_addi_produit FOREIGN KEY (produit_id) REFERENCES produit(id),
    CONSTRAINT fk_comp_addi_additif FOREIGN KEY (additif_id) REFERENCES additif(id)
);

CREATE TABLE IF NOT EXISTS comp_prod_aller (
    id BIGSERIAL PRIMARY KEY,
    qte_milligrammes DOUBLE PRECISION,
    produit_id BIGINT,
    allergene_id BIGINT,
    CONSTRAINT fk_comp_aller_produit FOREIGN KEY (produit_id) REFERENCES produit(id),
    CONSTRAINT fk_comp_aller_allergene FOREIGN KEY (allergene_id) REFERENCES allergene(id)
);

CREATE TABLE IF NOT EXISTS comp_prod_ingr (
    id BIGSERIAL PRIMARY KEY,
    qte_milligrammes DOUBLE PRECISION,
    produit_id BIGINT,
    ingredient_id BIGINT,
    CONSTRAINT fk_comp_ingr_produit FOREIGN KEY (produit_id) REFERENCES produit(id),
    CONSTRAINT fk_comp_ingr_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
);

CREATE TABLE IF NOT EXISTS produit_ingredient (
    produit_id BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL,
    PRIMARY KEY (produit_id, ingredient_id),
    CONSTRAINT fk_pi_produit FOREIGN KEY (produit_id) REFERENCES produit(id),
    CONSTRAINT fk_pi_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
);

CREATE TABLE IF NOT EXISTS produit_allergene (
    produit_id BIGINT NOT NULL,
    allergene_id BIGINT NOT NULL,
    PRIMARY KEY (produit_id, allergene_id),
    CONSTRAINT fk_pa_produit FOREIGN KEY (produit_id) REFERENCES produit(id),
    CONSTRAINT fk_pa_allergene FOREIGN KEY (allergene_id) REFERENCES allergene(id)
);
