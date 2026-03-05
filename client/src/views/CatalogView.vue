<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';

const movies = ref<any[]>([]);
const GATEWAY_URL = "http://info-tpsi.univ-brest.fr:11040";

const isAdmin = ref(false);
const showAddForm = ref(false);

const newMovie = ref({
  title: '',
  yearCompletion: '',
  director: '',
  minimumAge: 0,
  openForRent: true,
  actorsStr: '',
  genresStr: '',
  postersStr: ''
});

onMounted(async () => {
  const userStr = localStorage.getItem('user');
  if (userStr) {
    const user = JSON.parse(userStr);
    if (user.role === 'ADMIN') {
      isAdmin.value = true;
    }
  }

  fetchMovies();
})

const fetchMovies = async () => {
  try {
    const response = await fetch(`${GATEWAY_URL}/movies`);
    if (!response.ok) throw new Error(`Erreur serveur: ${response.status}`);
    const data = await response.json();
    movies.value = data;
  } catch (error) {
    console.error("Erreur de connexion :", error);
  }
};

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

const submitMovie = async () => {
  const payload = {
    title: newMovie.value.title,
    yearCompletion: newMovie.value.yearCompletion,
    director: newMovie.value.director,
    minimumAge: newMovie.value.minimumAge,
    openForRent: newMovie.value.openForRent,
    actors: newMovie.value.actorsStr.split(',').map(s => s.trim()).filter(s => s !== ''),
    genres: newMovie.value.genresStr.split(',').map(s => s.trim()).filter(s => s !== ''),
    posters: newMovie.value.postersStr.split(',').map(s => s.trim()).filter(s => s !== '')
  };

  try {
    const response = await fetch(`${GATEWAY_URL}/movies`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    });

    if (!response.ok) throw new Error("Erreur lors de l'ajout du film");

    await fetchMovies();
    showAddForm.value = false;

    newMovie.value = { title: '', yearCompletion: '', director: '', minimumAge: 0, openForRent: true, actorsStr: '', genresStr: '', postersStr: '' };

  } catch (error) {
    console.error("Erreur :", error);
    alert("Impossible d'ajouter le film.");
  }
};
</script>

<template>
  <div class="catalog-page">
    <div class="page-header">
      <div>
        <h1 class="page-title">Découvrir</h1>
        <p class="page-subtitle">Trouvez votre prochain film préféré</p>
      </div>
      <button v-if="isAdmin" class="btn-admin" @click="showAddForm = !showAddForm">
        {{ showAddForm ? 'Fermer' : '+ Nouveau film' }}
      </button>
    </div>

    <div v-if="showAddForm && isAdmin" class="admin-panel">
    </div>

    <div class="search-section">
      <div class="search-grid">
        <input v-model="searchCriteria.title" type="text" placeholder="Titre du film..." class="search-input" />
        <input v-model="searchCriteria.genre" type="text" placeholder="Genre..." class="search-input" />
        <input v-model="searchCriteria.director" type="text" placeholder="Réalisateur..." class="search-input" />
        <input v-model="searchCriteria.actor" type="text" placeholder="Acteur..." class="search-input" />
      </div>
    </div>

    <div v-if="movies.length === 0" class="empty-state">Chargement du catalogue...</div>
    <div v-else-if="filteredMovies.length === 0" class="empty-state">Aucun film ne correspond à vos critères.</div>

    <div class="movie-grid">
      <div v-for="movie in filteredMovies" :key="movie.id" class="movie-card" @click="$router.push(`/movie/${movie.id}`)">
        <div class="poster-wrapper">
          <img v-if="movie.posters && movie.posters.length > 0" :src="movie.posters[0]" alt="Poster" class="poster-img"/>
          <div v-else class="poster-placeholder">Sans affiche</div>
          <div class="age-badge" v-if="movie.minimumAge">{{ movie.minimumAge }}+</div>
        </div>
        <div class="movie-info">
          <h3 class="movie-title">{{ movie.title }}</h3>
          <p class="movie-meta">{{ movie.yearCompletion }} • {{ movie.genres?.[0] || 'Non classé' }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.page-header { display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 30px; }
.page-title { font-size: 2.5rem; font-weight: 800; color: #0f172a; margin: 0; }
.page-subtitle { color: #64748b; margin: 5px 0 0 0; font-size: 1.1rem; }
.btn-admin { background-color: #1e293b; color: white; padding: 10px 20px; border-radius: 8px; border: none; font-weight: bold; cursor: pointer; }

.search-section { background: white; padding: 20px; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.02); margin-bottom: 30px; }
.search-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; }
.search-input { padding: 12px 15px; border: 1px solid #e2e8f0; border-radius: 8px; background: #f8fafc; font-size: 0.95rem; }
.search-input:focus { outline: none; border-color: #3b82f6; background: white; }

.movie-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); gap: 25px; }
.movie-card { background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 10px rgba(0,0,0,0.04); cursor: pointer; transition: transform 0.2s, box-shadow 0.2s; border: 1px solid #f1f5f9; }
.movie-card:hover { transform: translateY(-5px); box-shadow: 0 12px 20px rgba(0,0,0,0.08); }

.poster-wrapper { position: relative; height: 320px; background-color: #cbd5e1; overflow: hidden; }
.poster-img { width: 100%; height: 100%; object-fit: cover; transition: transform 0.3s; }
.movie-card:hover .poster-img { transform: scale(1.05); }
.poster-placeholder { height: 100%; display: flex; align-items: center; justify-content: center; color: white; font-weight: bold; }
.age-badge { position: absolute; top: 10px; right: 10px; background: rgba(15, 23, 42, 0.8); color: white; padding: 4px 8px; border-radius: 6px; font-size: 0.8rem; font-weight: bold; backdrop-filter: blur(4px); }

.movie-info { padding: 16px; }
.movie-title { margin: 0 0 4px 0; font-size: 1.1rem; font-weight: 700; color: #1e293b; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.movie-meta { margin: 0; font-size: 0.9rem; color: #64748b; }
.empty-state { text-align: center; padding: 50px; color: #64748b; font-size: 1.1rem; }
</style>
