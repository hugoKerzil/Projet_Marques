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
        url: "https://static.wikia.nocookie.net/cinematheque/images/9/9f/Inception_-_Poster_%280%29.jpg/revision/latest?cb=20230412164019&path-prefix=fr",
        description: "Affiche principale d'Inception"
    },
    {
        _class: "com.entities.Poster",
        id: NumberLong(2),
        movieId: NumberLong(2),
        url: "https://fr.web.img6.acsta.net/pictures/14/09/24/12/08/158828.jpg",
        description: "Affiche du film Interstellar"
    },
    {
        _class: "com.entities.Poster",
        id: NumberLong(3),
        movieId: NumberLong(3),
        url: "https://fr.web.img2.acsta.net/medias/nmedia/18/63/97/89/18949761.jpg",
        description: "Affiche officielle de The Dark Knight"
    }
]);