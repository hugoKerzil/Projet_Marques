<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { ref, onMounted } from 'vue';

const route = useRoute()
const router = useRouter()
const movieId = route.params.id

const movie = ref<any>(null);
const GATEWAY_URL = "http://info-tpsi.univ-brest.fr:11040";

const isAdmin = ref(false);

onMounted(async () => {
  const userStr = localStorage.getItem('user');
  if (userStr) {
    const user = JSON.parse(userStr);
    if (user.role === 'ADMIN') {
      isAdmin.value = true;
    }
  }

  try {
    const response = await fetch(`${GATEWAY_URL}/movies/${movieId}`);
    if (!response.ok) throw new Error('Film non trouvé');
    const data = await response.json();
    movie.value = data;
  } catch (error) {
    console.error("Erreur détaillée :", error);
  }
})

const deleteMovie = async () => {
  if (!confirm("Attention : Êtes-vous sûr de vouloir supprimer définitivement ce film du catalogue ?")) {
    return;
  }

  try {
    const response = await fetch(`${GATEWAY_URL}/movies/${movieId}`, {
      method: 'DELETE'
    });

    if (!response.ok) throw new Error('Erreur lors de la suppression');

    router.push('/catalog');

  } catch (error) {
    console.error("Erreur :", error);
    alert("Impossible de supprimer le film.");
  }
};
</script>

<template>
  <div v-if="movie" class="movie-detail">
    <div class="header-actions">
      <button @click="router.back()" class="back-btn">← Retour au catalogue</button>

      <button v-if="isAdmin" @click="deleteMovie" class="delete-btn">
        Supprimer le film
      </button>
    </div>

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
.header-actions { display: flex; justify-content: space-between; margin-bottom: 20px; }
.back-btn { background: none; border: none; color: #2c3e50; cursor: pointer; font-size: 1rem; font-weight: bold; }
.delete-btn { background-color: #dc3545; color: white; border: none; padding: 8px 16px; border-radius: 6px; cursor: pointer; font-weight: bold; }
.delete-btn:hover { background-color: #c82333; }
.content-layout { display: flex; gap: 30px; }
.poster-container { flex: 0 0 300px; }
.movie-poster { width: 100%; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.3); }
.info-container { flex: 1; }
.meta-data { margin-bottom: 20px; color: #666; }
.credits { margin-bottom: 30px; }
.play-btn { background-color: #42b883; color: white; border: none; padding: 12px 24px; border-radius: 6px; font-size: 1.1rem; cursor: pointer; font-weight: bold; }
.disabled-btn { background-color: #666 !important; cursor: not-allowed; }
</style>
