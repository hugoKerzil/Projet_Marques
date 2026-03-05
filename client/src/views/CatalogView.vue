<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

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
    const title = (movie.title || '').toLowerCase();
    const director = (movie.director || '').toLowerCase();

    return title.includes(searchCriteria.value.title.toLowerCase()) &&
      director.includes(searchCriteria.value.director.toLowerCase()) &&
      (searchCriteria.value.genre === '' || (movie.genres || []).some((g: string) => g.toLowerCase().includes(searchCriteria.value.genre.toLowerCase()))) &&
      (searchCriteria.value.actor === '' || (movie.actors || []).some((a: string) => a.toLowerCase().includes(searchCriteria.value.actor.toLowerCase())))
  })
})

const submitMovie = async () => {
  const payload = {
    title: newMovie.value.title,
    yearCompletion: newMovie.value.yearCompletion,
    director: newMovie.value.director,
    minimumAge: newMovie.value.minimumAge,
    openForRent: newMovie.value.openForRent,
    actors: newMovie.value.actorsStr.split(',').map((s: string) => s.trim()).filter((s: string) => s !== ''),
    genres: newMovie.value.genresStr.split(',').map((s: string) => s.trim()).filter((s: string) => s !== ''),
    posters: newMovie.value.postersStr.split(',').map((s: string) => s.trim()).filter((s: string) => s !== '')
  };

  try {
    const response = await fetch(`${GATEWAY_URL}/movies`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
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

const toggleRentStatus = async (movie: any) => {
  const originalStatus = movie.openForRent;
  movie.openForRent = !originalStatus;

  try {
    const response = await fetch(`${GATEWAY_URL}/movies/${movie.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(movie)
    });

    if (!response.ok) throw new Error("Erreur de mise à jour");
  } catch (error) {
    console.error("Erreur :", error);
    movie.openForRent = originalStatus;
    alert("Impossible de modifier le statut du film.");
  }
};

const goToDetail = (movie: any) => {
  router.push(`/movie/${movie.id}`);
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
        {{ showAddForm ? 'Fermer le formulaire' : '+ Nouveau film' }}
      </button>
    </div>

    <div v-if="showAddForm && isAdmin" class="admin-form-container">
      <h3>Ajouter un nouveau film (Mode Admin)</h3>
      <form @submit.prevent="submitMovie" class="admin-form">
        <div class="form-row">
          <input v-model="newMovie.title" placeholder="Titre du film" required class="search-input" />
          <input v-model="newMovie.director" placeholder="Réalisateur" required class="search-input" />
        </div>
        <div class="form-row">
          <input v-model="newMovie.yearCompletion" placeholder="Année (ex: 2010)" required class="search-input" />
          <input v-model.number="newMovie.minimumAge" type="number" placeholder="Âge minimum" required class="search-input" />
        </div>
        <div class="form-row">
          <input v-model="newMovie.genresStr" placeholder="Genres (séparés par virgules)" class="search-input" />
          <input v-model="newMovie.actorsStr" placeholder="Acteurs (séparés par virgules)" class="search-input" />
        </div>
        <div class="form-row">
          <input v-model="newMovie.postersStr" placeholder="URL de l'affiche" style="width: 100%;" class="search-input" />
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
      <div v-for="movie in filteredMovies" :key="movie.id"
           class="movie-card"
           :class="{ 'unavailable-card': !movie.openForRent && !isAdmin }"
           @click="goToDetail(movie)">

        <div class="poster-wrapper">
          <img v-if="movie.posters && movie.posters.length > 0" :src="movie.posters[0]" alt="Poster" class="poster-img"/>
          <div v-else class="poster-placeholder">Sans affiche</div>
          <div class="age-badge" v-if="movie.minimumAge">{{ movie.minimumAge }}+</div>

          <div v-if="!movie.openForRent && !isAdmin" class="unavailable-overlay">Indisponible à la location</div>
        </div>

        <div class="movie-info">
          <h3 class="movie-title">{{ movie.title }}</h3>
          <p class="movie-meta">{{ movie.yearCompletion }} • {{ movie.genres?.[0] || 'Non classé' }}</p>

          <div v-if="isAdmin" class="admin-actions" @click.stop>
            <label class="switch">
              <input type="checkbox" :checked="movie.openForRent" @change="toggleRentStatus(movie)" />
              <span class="slider round"></span>
            </label>
            <span class="status-label" :class="{ 'status-off': !movie.openForRent }">
              {{ movie.openForRent ? 'En ligne' : 'Désactivé' }}
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.catalog-page { padding: 20px; max-width: 1200px; margin: 0 auto; }
.page-header { display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 30px; }
.page-title { font-size: 2.5rem; font-weight: 800; color: #0f172a; margin: 0; }
.page-subtitle { color: #64748b; margin: 5px 0 0 0; font-size: 1.1rem; }
.btn-admin { background-color: #1e293b; color: white; padding: 10px 20px; border-radius: 8px; border: none; font-weight: bold; cursor: pointer; transition: 0.2s; }
.btn-admin:hover { background-color: #0f172a; transform: translateY(-2px); }

.admin-form-container { background-color: #ffffff; padding: 25px; border-radius: 12px; margin-bottom: 30px; border: 1px solid #e2e8f0; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
.admin-form-container h3 { color: #2c3e50; margin-bottom: 15px; margin-top: 0; }
.admin-form { display: flex; flex-direction: column; gap: 15px; }
.form-row { display: flex; gap: 15px; }
.form-row input { flex: 1; }
.checkbox-row { align-items: center; font-weight: 600; color: #4a5568; }
.submit-btn { background-color: #42b883; color: white; border: none; padding: 14px; border-radius: 8px; cursor: pointer; font-weight: bold; transition: all 0.3s; }
.submit-btn:hover { background-color: #3aa876; }

.search-section { background: white; padding: 20px; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.02); margin-bottom: 30px; border: 1px solid #f1f5f9; }
.search-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; }
.search-input { padding: 12px 15px; border: 1px solid #e2e8f0; border-radius: 8px; background: #f8fafc; font-size: 0.95rem; width: 100%; box-sizing: border-box; }
.search-input:focus { outline: none; border-color: #3b82f6; background: white; box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1); }

.movie-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); gap: 25px; }
.movie-card { background: white; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 10px rgba(0,0,0,0.04); cursor: pointer; transition: transform 0.2s, box-shadow 0.2s; border: 1px solid #f1f5f9; display: flex; flex-direction: column; }
.movie-card:hover { transform: translateY(-5px); box-shadow: 0 12px 20px rgba(0,0,0,0.08); }

.poster-wrapper { position: relative; height: 320px; background-color: #cbd5e1; overflow: hidden; }
.poster-img { width: 100%; height: 100%; object-fit: cover; transition: transform 0.3s; }
.movie-card:hover .poster-img { transform: scale(1.05); }
.poster-placeholder { height: 100%; display: flex; align-items: center; justify-content: center; color: white; font-weight: bold; background: linear-gradient(135deg, #2c3e50, #4ca1af); }
.age-badge { position: absolute; top: 10px; right: 10px; background: rgba(15, 23, 42, 0.8); color: white; padding: 4px 8px; border-radius: 6px; font-size: 0.8rem; font-weight: bold; backdrop-filter: blur(4px); }

.movie-info { padding: 16px; display: flex; flex-direction: column; flex: 1; }
.movie-title { margin: 0 0 4px 0; font-size: 1.1rem; font-weight: 700; color: #1e293b; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.movie-meta { margin: 0; font-size: 0.9rem; color: #64748b; }
.empty-state { text-align: center; padding: 50px; color: #64748b; font-size: 1.1rem; width: 100%; grid-column: 1 / -1; }

.unavailable-card { opacity: 0.8; cursor: pointer; }
.unavailable-card:hover { transform: translateY(-5px) !important; box-shadow: 0 12px 20px rgba(0,0,0,0.08) !important; }
.unavailable-card .poster-img { filter: grayscale(100%); }
.unavailable-overlay { position: absolute; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0,0,0,0.5); color: white; display: flex; align-items: center; justify-content: center; font-size: 1.2rem; font-weight: bold; z-index: 10; backdrop-filter: grayscale(80%); pointer-events: none; text-align: center; padding: 10px; }

.admin-actions { display: flex; align-items: center; gap: 10px; margin-top: auto; padding-top: 15px; border-top: 1px solid #e2e8f0; }
.status-label { font-size: 0.85rem; font-weight: bold; color: #3b82f6; }
.status-off { color: #dc2626; }
.switch { position: relative; display: inline-block; width: 44px; height: 24px; }
.switch input { opacity: 0; width: 0; height: 0; }
.slider { position: absolute; cursor: pointer; top: 0; left: 0; right: 0; bottom: 0; background-color: #cbd5e1; transition: .4s; border-radius: 24px; }
.slider:before { position: absolute; content: ""; height: 18px; width: 18px; left: 3px; bottom: 3px; background-color: white; transition: .4s; border-radius: 50%; }
input:checked + .slider { background-color: #3b82f6; }
input:checked + .slider:before { transform: translateX(20px); }
</style>
