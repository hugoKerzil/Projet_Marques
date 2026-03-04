<script setup lang="ts">
import { ref, onMounted } from 'vue';

const movies = ref<any[]>([]);
const GATEWAY_URL = "http://info-tpsi.univ-brest.fr:11040";

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
</script>

<template>
  <div class="catalog">
    <h1>Catalogue VOD</h1>
    <div class="movie-grid">
      <div v-if="movies.length === 0" class="loading">Chargement des films...</div>

      <div v-for="movie in movies" :key="movie.id" class="movie-card">
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
</style>
