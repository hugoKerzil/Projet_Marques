<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { ref, onMounted } from 'vue';

const route = useRoute()
const router = useRouter()
const movieId = route.params.id

const movie = ref<any>(null);

const GATEWAY_URL = "http://movie-api:8081";

onMounted(async () => {
  try {
    const response = await fetch(`${GATEWAY_URL}/movies/${movieId}`);
    if (!response.ok) throw new Error('Film non trouvé');

    const data = await response.json();
    movie.value = data;

  } catch (error) {
    console.error("Erreur détaillée :", error);
  }
})
</script>

<template>
  <div v-if="movie" class="movie-detail">
    <button @click="router.back()" class="back-btn">← Retour au catalogue</button>

    <div class="content-layout">
      <div class="poster-container">
        <img v-if="movie.posters && movie.posters.length > 0"
             :src="movie.posters[0]"
             :alt="movie.title"
             class="movie-poster" />
        <div v-else class="poster-placeholder">Affiche non disponible</div>
      </div>

      <div class="info-container">
        <h1>{{ movie.title }}</h1>

        <div class="meta-data">
          <span>{{ movie.yearCompletion }}</span> •
          <span>{{ movie.genres?.join(', ') }}</span> •
          <span class="age-badge">{{ movie.minimumAge }}+</span>
        </div>

        <p class="synopsis">Réalisé par {{ movie.director }}.</p>

        <div class="credits">
          <p><strong>Réalisateur :</strong> {{ movie.director }}</p>
          <p><strong>Acteurs :</strong> {{ movie.actors?.join(', ') }}</p>
        </div>

        <button
          class="play-btn"
          :class="{ 'disabled-btn': !movie.openForRent }"
          :disabled="!movie.openForRent"
          @click="router.push(`/movie/${movie.id}/payment`)"
        >
          {{ movie.openForRent ? 'Louer le film' : 'Indisponible actuellement' }}
        </button>
      </div>
    </div>
  </div>

  <div v-else class="loading">
    Chargement du film...
  </div>
</template>

<style scoped>
.movie-poster {
  width: 100%;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0,0,0,0.3);
}
.disabled-btn {
  background-color: #666 !important;
  cursor: not-allowed;
}
</style>
