<script setup lang="ts">
// Données de test (Mocks) pour l'aspect visuel
const movies = [
  { id: 1, title: "Inception", genre: "S-F", year: 2010 },
  { id: 2, title: "Interstellar", genre: "S-F", year: 2014 },
  { id: 3, title: "The Dark Knight", genre: "Action", year: 2008 },
];
</script>

<template>
  <div class="catalog">
    <h1>Catalogue VOD</h1>
    <div class="movie-grid">
      <div v-for="movie in movies" :key="movie.id" class="movie-card">
        <div class="poster-placeholder">Affiche</div>
        <h3>{{ movie.title }}</h3>
        <p>{{ movie.genre }} - {{ movie.year }}</p>
        <button @click="$router.push(`/movie/${movie.id}`)">Détails</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.movie-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
  margin-top: 20px;
}
.movie-card {
  border: 1px solid #ddd;
  padding: 15px;
  border-radius: 8px;
  text-align: center;
}
.poster-placeholder {
  height: 250px;
  background-color: #eee;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
}
</style>
