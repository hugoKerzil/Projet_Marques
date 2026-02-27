<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

// On récupère l'ID depuis l'URL (ex: /movie/1)
const movieId = route.params.id

// Données fictives pour simuler un film provenant d'une API
const movie = {
  id: movieId,
  title: "Inception",
  synopsis: "Un voleur spécialisé dans l'extraction de secrets par les rêves se voit offrir une chance de retrouver sa vie passée en accomplissant une tâche impossible : l'inception.",
  year: 2010,
  director: "Christopher Nolan",
  actors: ["Leonardo DiCaprio", "Marion Cotillard", "Cillian Murphy"],
  genres: ["Science-Fiction", "Action"],
  ageLimit: 12,
  duration: "2h 28min"
}
</script>

<template>
  <div class="movie-detail">
    <button @click="router.back()" class="back-btn">← Retour au catalogue</button>

    <div class="content-layout">
      <div class="poster-container">
        <div class="poster-placeholder">Affiche du film {{ movieId }}</div>
      </div>

      <div class="info-container">
        <h1>{{ movie.title }}</h1>

        <div class="meta-data">
          <span>{{ movie.year }}</span> •
          <span>{{ movie.duration }}</span> •
          <span>{{ movie.genres.join(', ') }}</span> •
          <span class="age-badge">{{ movie.ageLimit }}+</span>
        </div>

        <p class="synopsis">{{ movie.synopsis }}</p>

        <div class="credits">
          <p><strong>Réalisateur :</strong> {{ movie.director }}</p>
          <p><strong>Acteurs :</strong> {{ movie.actors.join(', ') }}</p>
        </div>

        <button class="play-btn">Regarder le film</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.movie-detail {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.back-btn {
  background: none;
  border: none;
  color: #646cff;
  cursor: pointer;
  margin-bottom: 20px;
  font-weight: bold;
}

.content-layout {
  display: flex;
  gap: 40px;
}

.poster-placeholder {
  width: 300px;
  height: 450px;
  background-color: #2a2a2a;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
}

.info-container {
  flex: 1;
}

.meta-data {
  margin: 10px 0;
  color: #888;
}

.age-badge {
  background: #444;
  color: white;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 0.8em;
}

.synopsis {
  margin: 20px 0;
  line-height: 1.6;
  font-size: 1.1em;
}

.credits {
  border-top: 1px solid #333;
  padding-top: 20px;
  margin-bottom: 30px;
}

.play-btn {
  background-color: #e50914;
  color: white;
  border: none;
  padding: 12px 30px;
  border-radius: 4px;
  font-size: 1.2em;
  font-weight: bold;
  cursor: pointer;
}

.play-btn:hover {
  background-color: #b20710;
}

@media (max-width: 768px) {
  .content-layout {
    flex-direction: column;
    align-items: center;
  }
}
</style>
