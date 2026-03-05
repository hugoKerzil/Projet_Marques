<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { ref, onMounted } from 'vue';

const route = useRoute()
const router = useRouter()
const movieId = route.params.id

const movie = ref<any>(null);
const GATEWAY_URL = "http://info-tpsi.univ-brest.fr:11040";

const isAdmin = ref(false);
const directorDetails = ref<any>(null);

onMounted(async () => {
  // 1. Vérification des droits Admin
  const userStr = localStorage.getItem('user');
  if (userStr) {
    const user = JSON.parse(userStr);
    if (user.role === 'ADMIN') {
      isAdmin.value = true;
    }
  }

  try {
    // 2. Récupération du film
    const movieRes = await fetch(`${GATEWAY_URL}/movies/${movieId}`);
    movie.value = await movieRes.json();

    // 3. Appel à l'API externe Artists via la Gateway
    if (movie.value.director) {
      const artistRes = await fetch(`${GATEWAY_URL}/api/artists/search?name=${encodeURIComponent(movie.value.director)}`);
      if (artistRes.ok) {
        directorDetails.value = await artistRes.json();
      }
    }
  } catch (error) {
    console.error("Erreur :", error);
  }
});

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
  <div v-if="movie" class="detail-page">
    <button @click="router.back()" class="btn-back">← Retour</button>

    <div class="content-wrapper">
      <div class="poster-section">
        <img v-if="movie.posters?.length > 0" :src="movie.posters[0]" alt="Poster" class="main-poster" />
        <div v-else class="main-poster placeholder">Aucune affiche</div>
      </div>

      <div class="info-section">
        <div class="title-header">
          <h1 class="movie-title">{{ movie.title }}</h1>
          <button v-if="isAdmin" @click="deleteMovie" class="btn-delete">Supprimer</button>
        </div>

        <div class="tags">
          <span class="tag">{{ movie.yearCompletion }}</span>
          <span class="tag" v-for="genre in movie.genres" :key="genre">{{ genre }}</span>
          <span class="tag age" v-if="movie.minimumAge">{{ movie.minimumAge }}+</span>
        </div>

        <div class="crew-details">
          <div class="crew-item">
            <span class="label">Réalisateur</span>
            <span class="value">{{ movie.director }}</span>

            <div v-if="directorDetails" class="artist-card">
              <p class="artist-bio"><strong>Bio :</strong> {{ directorDetails.biography || 'Aucune biographie disponible.' }}</p>
              <p class="artist-info">Nationalité : {{ directorDetails.nationality }}</p>
            </div>
          </div>

          <div class="crew-item">
            <span class="label">Casting principal</span>
            <span class="value">{{ movie.actors?.join(', ') || 'Non spécifié' }}</span>
          </div>
        </div>

        <div class="action-section">
          <button
            class="btn-rent"
            :class="{ 'disabled': !movie.openForRent }"
            :disabled="!movie.openForRent"
            @click="router.push(`/movie/${movie.id}/payment`)"
          >
            {{ movie.openForRent ? 'Louer ce film - 9.99€' : 'Non disponible à la location' }}
          </button>
        </div>
      </div>
    </div>
  </div>

  <div v-else class="loading">
    Chargement des détails du film...
  </div>
</template>

<style scoped>
.btn-back { background: none; border: none; color: #64748b; cursor: pointer; font-size: 1rem; font-weight: 600; margin-bottom: 20px; padding: 0; }
.btn-back:hover { color: #0f172a; }

.content-wrapper { display: flex; gap: 40px; background: white; padding: 40px; border-radius: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.03); }
@media(max-width: 768px) { .content-wrapper { flex-direction: column; padding: 20px; } }

.poster-section { flex: 0 0 320px; }
.main-poster { width: 100%; border-radius: 12px; box-shadow: 0 15px 35px rgba(0,0,0,0.1); object-fit: cover; }
.placeholder { height: 480px; background: #e2e8f0; display: flex; align-items: center; justify-content: center; color: #94a3b8; }

.info-section { flex: 1; display: flex; flex-direction: column; }
.title-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 15px; }
.movie-title { font-size: 2.8rem; font-weight: 800; color: #0f172a; margin: 0; line-height: 1.1; }
.btn-delete { background: #fee2e2; color: #dc2626; border: none; padding: 8px 16px; border-radius: 6px; font-weight: bold; cursor: pointer; transition: 0.2s; }
.btn-delete:hover { background: #fecaca; }

.tags { display: flex; flex-wrap: wrap; gap: 10px; margin-bottom: 30px; }
.tag { background: #f1f5f9; color: #475569; padding: 6px 12px; border-radius: 20px; font-size: 0.9rem; font-weight: 600; }
.tag.age { background: #1e293b; color: white; }

.crew-details { display: flex; flex-direction: column; gap: 15px; margin-bottom: 40px; }
.crew-item .label { display: block; font-size: 0.85rem; text-transform: uppercase; color: #94a3b8; font-weight: 700; margin-bottom: 4px; }
.crew-item .value { font-size: 1.1rem; color: #334155; font-weight: 500; }

.action-section { margin-top: auto; padding-top: 30px; border-top: 1px solid #e2e8f0; }
.btn-rent { width: 100%; max-width: 350px; background: #3b82f6; color: white; border: none; padding: 16px 24px; border-radius: 12px; font-size: 1.1rem; font-weight: bold; cursor: pointer; transition: 0.2s; box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3); }
.btn-rent:hover:not(.disabled) { background: #2563eb; transform: translateY(-2px); }
.btn-rent.disabled { background: #cbd5e1; box-shadow: none; cursor: not-allowed; color: #64748b; }

.artist-card {
  margin-top: 10px;
  padding: 12px;
  background-color: #f8fafc;
  border-left: 4px solid #3b82f6;
  border-radius: 4px;
  font-size: 0.9rem;
}
.artist-bio { color: #475569; margin-bottom: 5px; line-height: 1.4; }
.artist-info { color: #64748b; font-size: 0.8rem; font-style: italic; }
</style>
