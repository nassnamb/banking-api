-- create database bank;
-- GRANT ALL ON DATABASE bank TO <username>;

-- Table: CompteBancaire
CREATE TABLE compte_bancaire (
    id BIGSERIAL PRIMARY KEY,
    numero_compte VARCHAR(27) NOT NULL UNIQUE,
    solde DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    creation_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    modification_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

-- Création d'un index pour améliorer les recherches par numéro de compte
CREATE INDEX idx_compte_bancaire_numero ON compte_bancaire(numero_compte);

-- Ajout d'une contrainte pour éviter les soldes négatifs
--ALTER TABLE compte_bancaire ADD CONSTRAINT chk_solde_positif CHECK (solde >= 0);


CREATE TABLE transaction (
    id BIGSERIAL PRIMARY KEY,
    montant DECIMAL(15, 2) NOT NULL,
    type_operation VARCHAR(20) NOT NULL,
    compte_source VARCHAR(27),
    compte_destination VARCHAR(27),
    date_operation TIMESTAMP WITH TIME ZONE NOT NULL,
    description TEXT,
    CONSTRAINT fk_source FOREIGN KEY (compte_source) REFERENCES compte_bancaire(numero_compte),
    CONSTRAINT fk_destination FOREIGN KEY (compte_destination) REFERENCES compte_bancaire(numero_compte)
);

CREATE INDEX idx_transaction_compte_source ON transaction(compte_source);
CREATE INDEX idx_transaction_compte_destination ON transaction(compte_destination);
CREATE INDEX idx_transaction_date ON transaction(date_operation DESC);