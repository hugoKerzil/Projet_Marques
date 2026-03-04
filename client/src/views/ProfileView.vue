<template>
  <div class="profile-container">
    <header class="profile-header">
      <div class="avatar">{{ userPseudo.charAt(0).toUpperCase() }}</div>
      <h1>Tableau de bord de <span class="highlight">{{ userPseudo }}</span></h1>
      <p class="status-badge">Membre Actif</p>
    </header>

    <section class="rentals-section">
      <h2>Mes réservations en cours ({{ activeRentals.length }}/3)</h2>

      <div v-if="activeRentals.length > 0" class="rentals-list">
        <div v-for="movie in activeRentals" :key="movie.id" class="rental-card">
          <div class="rental-info">
            <h3>{{ movie.title }}</h3>
            <p>Loué le : {{ movie.date }}</p>
          </div>
          <button class="return-btn" @click="returnMovie(movie.id)">
            Terminer la location
          </button>
        </div>
      </div>

      <div v-else class="empty-state">
        <p>Vous n'avez aucune réservation en cours.</p>
        <button @click="$router.push('/catalog')" class="go-catalog">Parcourir le catalogue</button>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';

const userPseudo = ref('Utilisateur_Demo');

const activeRentals = ref([
  { id: 101, title: 'Inception', date: '04/03/2026' },
  { id: 102, title: 'Interstellar', date: '04/03/2026' }
]);

const returnMovie = (id: number) => {
  if (confirm("Voulez-vous vraiment terminer la location de ce film ?")) {
    activeRentals.value = activeRentals.value.filter(m => m.id !== id);
  }
};

onMounted(() => {
  const savedPseudo = localStorage.getItem('user_pseudo');
  if (savedPseudo) userPseudo.value = savedPseudo;
});
</script>

<style scoped>
.profile-container {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.profile-header {
  text-align: center;
  margin-bottom: 50px;
}

.avatar {
  width: 80px;
  height: 80px;
  background: #42b883;
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
  font-weight: bold;
  margin: 0 auto 15px;
}

.highlight {
  color: #42b883;
}

.status-badge {
  display: inline-block;
  background: #e8f5e9;
  color: #2e7d32;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: bold;
}

.rentals-section h2 {
  border-bottom: 2px solid #eee;
  padding-bottom: 10px;
  margin-bottom: 20px;
}

.rentals-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.rental-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.rental-info h3 {
  margin: 0;
  color: #2c3e50;
}

.rental-info p {
  margin: 5px 0 0;
  color: #7f8c8d;
  font-size: 0.9rem;
}

.return-btn {
  background: #ff5252;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.3s;
}

.return-btn:hover {
  background: #ff1744;
}

.empty-state {
  text-align: center;
  padding: 40px;
  background: #f9f9f9;
  border-radius: 10px;
}

.go-catalog {
  background: #42b883;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 6px;
  margin-top: 15px;
  cursor: pointer;
}
</style>
