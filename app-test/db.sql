-- Créer la base de données
#CREATE DATABASE framework_db;

-- Créer un utilisateur
CREATE USER framework_user WITH PASSWORD 'framework123';

-- Donner les droits
GRANT ALL PRIVILEGES ON DATABASE framework_db TO framework_user;

-- Se connecter à la BDD
\c framework_db

-- Créer la table users
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insérer des données de test
INSERT INTO users (name, email) VALUES 
    ('Alice', 'alice@mail.com'),
    ('Bob', 'bob@mail.com'),
    ('Charlie', 'charlie@mail.com');

-- Vérifier
SELECT * FROM users;