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
  <div class="catalog">
    <div class="header-section">
      <h1>Catalogue VOD</h1>
      <button v-if="isAdmin" class="admin-add-btn" @click="showAddForm = !showAddForm">
        {{ showAddForm ? 'Fermer le formulaire' : '+ Ajouter un film' }}
      </button>
    </div>

    <div v-if="showAddForm && isAdmin" class="admin-form-container">
      <h3>Ajouter un nouveau film (Mode Admin)</h3>
      <form @submit.prevent="submitMovie" class="admin-form">
        <div class="form-row">
          <input v-model="newMovie.title" placeholder="Titre du film" required />
          <input v-model="newMovie.director" placeholder="Réalisateur" required />
        </div>
        <div class="form-row">
          <input v-model="newMovie.yearCompletion" placeholder="Année (ex: 2010)" required />
          <input v-model.number="newMovie.minimumAge" type="number" placeholder="Âge minimum" required />
        </div>
        <div class="form-row">
          <input v-model="newMovie.genresStr" placeholder="Genres (séparés par des virgules)" />
          <input v-model="newMovie.actorsStr" placeholder="Acteurs (séparés par des virgules)" />
        </div>
        <div class="form-row">
          <input v-model="newMovie.postersStr" placeholder="URL de l'affiche" style="width: 100%;" />
        </div>
        <div class="form-row checkbox-row">
          <label>
            <input type="checkbox" v-model="newMovie.openForRent" />
            Disponible à la location
          </label>
        </div>
        <button type="submit" class="submit-btn">Enregistrer le film</button>
      </form>
    </div>

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
.catalog { padding: 20px; max-width: 1200px; margin: 0 auto; }
.header-section { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.admin-add-btn { background-color: #2c3e50; color: white; border: none; padding: 10px 20px; border-radius: 6px; cursor: pointer; font-weight: bold; }
.admin-form-container { background-color: #e8f4f8; padding: 20px; border-radius: 8px; margin-bottom: 20px; border: 1px solid #bce0ee; }
.admin-form { display: flex; flex-direction: column; gap: 15px; margin-top: 15px; }
.form-row { display: flex; gap: 15px; }
.form-row input { flex: 1; padding: 10px; border: 1px solid #ccc; border-radius: 4px; }
.checkbox-row { align-items: center; }
.submit-btn { background-color: #42b883; color: white; border: none; padding: 12px; border-radius: 6px; cursor: pointer; font-weight: bold; }
.movie-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); gap: 30px; margin-top: 20px; }
.movie-card { background: #fff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.1); display: flex; flex-direction: column; }
.poster-container { width: 100%; height: 320px; background-color: #2c3e50; overflow: hidden; }
.movie-img { width: 100%; height: 100%; object-fit: cover; display: block; }
.no-poster { height: 100%; display: flex; align-items: center; justify-content: center; color: white; font-size: 0.9rem; }
.movie-info { padding: 15px; text-align: left; }
.movie-info h3 { margin: 0 0 10px 0; font-size: 1.1rem; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.meta { color: #666; font-size: 0.85rem; margin-bottom: 15px; }
.detail-btn { width: 100%; padding: 10px; background-color: #42b883; color: white; border: none; border-radius: 6px; cursor: pointer; font-weight: bold; }
.filter-bar { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; background: #f8f9fa; padding: 20px; border-radius: 12px; margin-bottom: 30px; }
.filter-group { display: flex; flex-direction: column; gap: 5px; }
.filter-group label { font-size: 0.8rem; font-weight: bold; color: #2c3e50; text-transform: uppercase; }
.filter-group input { padding: 10px; border: 1px solid #ddd; border-radius: 6px; font-size: 0.9rem; }
</style>
