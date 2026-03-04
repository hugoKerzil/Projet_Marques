<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';

const movies = ref<any[]>([]);
const GATEWAY_URL = "http://localhost:11040";

onMounted(async () => {
  try {
    const response = await fetch(`${GATEWAY_URL}/movies`);
    if (!response.ok) throw new Error(`Erreur serveur: ${response.status}`);
    const data = await response.json();
    movies.value = data;
  } catch (error) {
    console.error("Erreur de connexion :", error);
  }
})

const searchCriteria = ref({
  title: '',
  director: '',
  genre: '',
  actor: ''
})

const filteredMovies = computed(() => {
  return movies.value.filter(movie => {
    return movie.title.toLowerCase().includes(searchCriteria.value.title.toLowerCase()) &&
      movie.director.toLowerCase().includes(searchCriteria.value.director.toLowerCase()) &&
      (searchCriteria.value.genre === '' || movie.genres.some((g: string) => g.toLowerCase().includes(searchCriteria.value.genre.toLowerCase()))) &&
      (searchCriteria.value.actor === '' || movie.actors.some((a: string) => a.toLowerCase().includes(searchCriteria.value.actor.toLowerCase())))
  })
})
</script>

<template>
  <div class="catalog">
    <h1>Catalogue VOD</h1>
    ev
    <div class="filter-bar">
      <div class="filter-group">
        <label>Titre</label>
        <input v-model="searchCriteria.title" type="text" placeholder="Rechercher un film..." />
      </div>
      <div class="filter-group">
        <label>Genre</label>
        <input v-model="searchCriteria.genre" type="text" placeholder="Action, Drame..." />
      </div>
      <div class="filter-group">
        <label>Réalisateur</label>
        <input v-model="searchCriteria.director" type="text" placeholder="Nom du réalisateur..." />
      </div>
      <div class="filter-group">
        <label>Acteur</label>
        <input v-model="searchCriteria.actor" type="text" placeholder="Nom d'un acteur..." />
      </div>
    </div>

    <div class="movie-grid">
      <div v-if="movies.length === 0" class="loading">Chargement des films...</div>

      <div v-else-if="filteredMovies.length === 0" class="no-results">
        Aucun film ne correspond à vos critères.
      </div>

      <div v-for="movie in filteredMovies" :key="movie.id" class="movie-card">
        <div class="poster-container">
          <img
            v-if="movie.posters && movie.posters.length > 0"
            :src="movie.posters[0]"
            alt="Poster"
            class="movie-img"
          />
          <div v-else class="no-poster">Affiche non disponible</div>
        </div>

        <div class="movie-info">
          <h3>{{ movie.title }}</h3>
          <p class="meta">{{ movie.yearCompletion }} • {{ movie.genres?.[0] }}</p>
          <button class="detail-btn" @click="$router.push(`/movie/${movie.id}`)">Détails</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.catalog {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.movie-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 30px;
  margin-top: 20px;
}

.movie-card {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
  transition: transform 0.3s ease;
  display: flex;
  flex-direction: column;
}

.movie-card:hover {
  transform: translateY(-5px);
}

.poster-container {
  width: 100%;
  height: 320px;
  background-color: #2c3e50;
  overflow: hidden;
}

.movie-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.no-poster {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 0.9rem;
}

.movie-info {
  padding: 15px;
  text-align: left;
}

.movie-info h3 {
  margin: 0 0 10px 0;
  font-size: 1.1rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.meta {
  color: #666;
  font-size: 0.85rem;
  margin-bottom: 15px;
}

.detail-btn {
  width: 100%;
  padding: 10px;
  background-color: #42b883;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
}

.detail-btn:hover {
  background-color: #33a06f;
}

.filter-bar {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 15px;
  background: #f8f9fa;
  padding: 20px;
  border-radius: 12px;
  margin-bottom: 30px;
  box-shadow: inset 0 2px 4px rgba(0,0,0,0.05);
}

.filter-group {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.filter-group label {
  font-size: 0.8rem;
  font-weight: bold;
  color: #2c3e50;
  text-transform: uppercase;
}

.filter-group input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 0.9rem;
}

.filter-group input:focus {
  outline: none;
  border-color: #42b883;
  box-shadow: 0 0 0 2px rgba(66, 184, 131, 0.2);
}

.no-results {
  grid-column: 1 / -1;
  text-align: center;
  padding: 40px;
  color: #666;
  font-style: italic;
}
</style>
