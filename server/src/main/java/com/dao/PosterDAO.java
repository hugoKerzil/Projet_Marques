package com.dao;

import com.config.MongoDBConfig;
import com.entities.Poster;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class PosterDAO {
    private final MongoCollection<Document> collection;

    public PosterDAO() {
        this.collection = MongoDBConfig.getDatabase().getCollection("posters");
    }

    public void save(Poster poster) {
        Document doc = new Document("id", poster.getId())
                .append("movieId", poster.getMovieId())
                .append("url", poster.getUrl())
                .append("description", poster.getDescription());
        collection.insertOne(doc);
    }

    /**
     * Récupère un poster par son identifiant unique
     */
    public Poster findById(Long id) {
        Document doc = collection.find(eq("id", id)).first();
        return doc != null ? documentToPoster(doc) : null;
    }

    /**
     * Récupère tous les posters associés à un film spécifique [cite: 11]
     */
    public List<Poster> findByMovieId(Long movieId) {
        List<Poster> posters = new ArrayList<>();
        for (Document doc : collection.find(eq("movieId", movieId))) {
            posters.add(documentToPoster(doc));
        }
        return posters;
    }

    /**
     * Met à jour les informations d'un poster existant
     */
    public void update(Poster poster) {
        collection.updateOne(
                eq("id", poster.getId()),
                Updates.combine(
                        Updates.set("movieId", poster.getMovieId()),
                        Updates.set("url", poster.getUrl()),
                        Updates.set("description", poster.getDescription())
                )
        );
    }

    /**
     * Supprime un poster de la base MongoDB
     */
    public boolean delete(Long id) {
        return collection.deleteOne(eq("id", id)).getDeletedCount() > 0;
    }

    /**
     * Méthode utilitaire pour convertir un Document BSON en entité Poster
     * Cela assure le découplage entre la couche données et la couche métier
     */
    private Poster documentToPoster(Document doc) {
        Poster poster = new Poster();
        poster.setId(doc.getLong("id"));
        poster.setMovieId(doc.getLong("movieId"));
        poster.setUrl(doc.getString("url"));
        poster.setDescription(doc.getString("description"));
        return poster;
    }
}