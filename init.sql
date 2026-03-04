CREATE DATABASE IF NOT EXISTS vodd_db;
USE vodd_db;

-- Création de la table 'compte' (Authen.java)
CREATE TABLE IF NOT EXISTS compte (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      pseudo VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
    );

-- Création de la table 'location' (Rental.java)
CREATE TABLE IF NOT EXISTS location (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY
);

-- Création de la table 'paiement' (Payment.java)
CREATE TABLE IF NOT EXISTS paiement (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        type VARCHAR(255),
    status VARCHAR(255),
    amount DOUBLE,
    location_id BIGINT NOT NULL,
    CONSTRAINT fk_paiement_location FOREIGN KEY (location_id) REFERENCES location(id)
    );

-- Création de la table 'movie' (Movie.java)
CREATE TABLE IF NOT EXISTS movie (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     title VARCHAR(255),
    year_completion VARCHAR(255),
    director VARCHAR(255),
    minimum_age BIGINT,
    open_for_rent BOOLEAN
    );

-- Tables pour les @ElementCollection de Movie.java
CREATE TABLE IF NOT EXISTS movie_actors (
                                            movie_id INT NOT NULL,
                                            actors VARCHAR(255),
    CONSTRAINT fk_movie_actors FOREIGN KEY (movie_id) REFERENCES movie(id)
    );

CREATE TABLE IF NOT EXISTS movie_genres (
                                            movie_id INT NOT NULL,
                                            genres VARCHAR(255),
    CONSTRAINT fk_movie_genres FOREIGN KEY (movie_id) REFERENCES movie(id)
    );

CREATE TABLE IF NOT EXISTS movie_posters (
                                             movie_id INT NOT NULL,
                                             posters VARCHAR(255),
    CONSTRAINT fk_movie_posters FOREIGN KEY (movie_id) REFERENCES movie(id)
    );

-- -----------------------------------------------------
-- Nettoyage de la BDD
-- -----------------------------------------------------
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE movie_actors;
TRUNCATE TABLE movie_genres;
TRUNCATE TABLE movie_posters;
TRUNCATE TABLE paiement;
TRUNCATE TABLE location;
TRUNCATE TABLE movie;
TRUNCATE TABLE compte;
SET FOREIGN_KEY_CHECKS = 1;

-- -----------------------------------------------------
-- Insertion des Comptes (Utilisateurs & Admins)
-- -----------------------------------------------------
-- Note : Respecte la structure détectée dans vos fichiers
INSERT INTO compte (pseudo, password, role) VALUES
                                                ('admin', 'admin123', 'ADMIN'),
                                                ('user1', 'user123', 'USER');

-- -----------------------------------------------------
-- Insertion des Films
-- -----------------------------------------------------
-- Utilisation des colonnes exactes détectées par Hibernate
INSERT INTO movie (id, title, year_completion, director, minimum_age, open_for_rent) VALUES
                                                                                         (1, 'Inception', '2010', 'Christopher Nolan', 12, TRUE),
                                                                                         (2, 'Interstellar', '2014', 'Christopher Nolan', 10, TRUE),
                                                                                         (3, 'The Dark Knight', '2008', 'Christopher Nolan', 13, FALSE);

-- -----------------------------------------------------
-- Insertion des Détails des Films (Tables de collection)
-- -----------------------------------------------------
-- Acteurs
INSERT INTO movie_actors (movie_id, actors) VALUES
                                                (1, 'Leonardo DiCaprio'), (1, 'Elliot Page'), (1, 'Tom Hardy'),
                                                (2, 'Matthew McConaughey'), (2, 'Anne Hathaway'),
                                                (3, 'Christian Bale'), (3, 'Heath Ledger');

-- Genres
INSERT INTO movie_genres (movie_id, genres) VALUES
                                                (1, 'Sci-Fi'), (1, 'Action'),
                                                (2, 'Sci-Fi'), (2, 'Drame'),
                                                (3, 'Action'), (3, 'Crime');

-- Postiers (Liens vers les images)
-- Ajout basé sur la table créée par Hibernate dans vos logs
INSERT INTO movie_posters (movie_id, posters) VALUES
                                                  (1, 'https://static.wikia.nocookie.net/cinematheque/images/9/9f/Inception_-_Poster_%280%29.jpg/revision/latest?cb=20230412164019&path-prefix=fr'),
                                                  (2, 'https://fr.web.img6.acsta.net/pictures/14/09/24/12/08/158828.jpg'),
                                                  (3, 'https://fr.web.img2.acsta.net/medias/nmedia/18/63/97/89/18949761.jpg');

-- -----------------------------------------------------
-- Insertion des Locations et Paiements
-- -----------------------------------------------------
-- On crée une location pour le film 1 liée à l'utilisateur 1 (si votre table le permet)
INSERT INTO location (id) VALUES (1);

-- Le paiement lié à la location 1
INSERT INTO paiement (type, status, amount, location_id) VALUES
    ('CB', 'VALIDE', 4.99, 1);