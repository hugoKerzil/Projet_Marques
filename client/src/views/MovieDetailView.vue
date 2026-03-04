<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { ref, onMounted } from 'vue';

const route = useRoute()
const router = useRouter()
const movieId = route.params.id

const movie = ref<any>(null);

onMounted(async () => {
  try {
    const response = await fetch(`http://localhost:8080/movies/${movieId}`);
    if (!response.ok) throw new Error('Film non trouvé');

    const data = await response.json();
    movie.value = data;

    console.log("La connexion avec Gateway bonne !!! Données reçus :", data);
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
        <div class="poster-placeholder">Affiche du film {{ movie.id }}</div>
      </div>

      <div class="info-container">
        <h1>{{ movie.title }}</h1>

        <div class="meta-data">
          <span>{{ movie.year }}</span> •
          <span>{{ movie.duration }} min</span> •
          <span>{{ movie.genres?.join(', ') }}</span> •
          <span class="age-badge">{{ movie.ageLimit }}+</span>
        </div>

        <p class="synopsis">{{ movie.synopsis }}</p>

        <div class="credits">
          <p><strong>Réalisateur :</strong> {{ movie.director }}</p>
          <p><strong>Acteurs :</strong> {{ movie.actors?.join(', ') }}</p>
        </div>

        <button
          class="play-btn"
          :disabled="!movie.openForRent"
        >
          {{ movie.openForRent ? 'Louer le film' : 'Indisponible' }}
        </button>
      </div>
    </div>
  </div>

  <div v-else class="loading">
    Chargement du film...
  </div>
</template>
