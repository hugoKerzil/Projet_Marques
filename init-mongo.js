// Sélection de la base de données
db = db.getSiblingDB('postersdb');

// Création explicite de la collection
db.createCollection('poster');

// Insertion de données de test correspondant à Poster.java
db.poster.insertMany([
    {
        _class: "com.entities.Poster",
        id: NumberLong(1),
        movieId: NumberLong(1),
        url: "https://example.com/posters/inception.jpg",
        description: "Affiche principale d'Inception"
    },
    {
        _class: "com.entities.Poster",
        id: NumberLong(2),
        movieId: NumberLong(2),
        url: "https://example.com/posters/interstellar.jpg",
        description: "Affiche du film Interstellar"
    }
]);