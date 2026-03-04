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

    /**
     * <p>Method for saving a poster in the MongoDB database</p>
     */
    public void save(Poster poster) {
        if (poster.getId() == null) {
            poster.setId(System.currentTimeMillis());
        }

        Document doc = new Document("id", poster.getId())
                .append("movieId", poster.getMovieId())
                .append("url", poster.getUrl())
                .append("description", poster.getDescription());
        collection.insertOne(doc);
    }

    /**
     * <p>Method for retrieving a poster by its unique identifier</p>
     * @return Poster
     */
    public Poster findById(Long id) {
        Document doc = collection.find(eq("id", id)).first();
        return doc != null ? documentToPoster(doc) : null;
    }

    /**
     * <p>Method for retrieving all posters associated with a specific movie</p>
     * @return List<Poster>
     */
    public List<Poster> findByMovieId(Long movieId) {
        List<Poster> posters = new ArrayList<>();
        for (Document doc : collection.find(eq("movieId", movieId))) {
            posters.add(documentToPoster(doc));
        }
        return posters;
    }

    /**
     * <p>Method for updating information on an existing poster</p>
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
     * <p>Method for deleting a poster from the MongoDB database</p>
     * @return boolean
     */
    public boolean delete(Long id) {
        return collection.deleteOne(eq("id", id)).getDeletedCount() > 0;
    }

    /**
     * <p>Utility method for converting a BSON document into a Poster entity</p>
     * @return Poster
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