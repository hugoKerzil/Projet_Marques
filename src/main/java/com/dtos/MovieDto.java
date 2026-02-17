package com.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.util.List;

@Data
public class MovieDto {

    private int id;

    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    @NotBlank(message = "L'année de réalisation est obligatoire")
    private String anneeRealisation;

    @NotBlank(message = "Le nom du réalisateur")
    private String realisateur;

    private List<String> acteurs;

    private List<String> genres;

    @Min(value = 0, message = "L'âge minimum ne peut pas être inférieur à 0 an")
    private int ageMinimum;

    private String affiche;

    private boolean estOuvertALaLocation;

    // --- Constructeurs ---

    public MovieDto() {
    }

    // --- Getters et Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnneeRealisation() {
        return anneeRealisation;
    }

    public void setAnneeRealisation(String anneeRealisation) {
        this.anneeRealisation = anneeRealisation;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public List<String> getActeurs() {
        return acteurs;
    }

    public void setActeurs(List<String> acteurs) {
        this.acteurs = acteurs;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getAgeMinimum() {
        return ageMinimum;
    }

    public void setAgeMinimum(int ageMinimum) {
        this.ageMinimum = ageMinimum;
    }

    public String getAffiche() {
        return affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    // Pour un booléen primitif, la convention est "is" au lieu de "get"
    public boolean isEstOuvertALaLocation() {
        return estOuvertALaLocation;
    }

    public void setEstOuvertALaLocation(boolean estOuvertALaLocation) {
        this.estOuvertALaLocation = estOuvertALaLocation;
    }
}
