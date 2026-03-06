<template>
  <div class="profile-container">
    <header class="profile-header">
      <div class="avatar">{{ userPseudo ? userPseudo.charAt(0).toUpperCase() : '?' }}</div>
      <h1>Tableau de bord de <span class="highlight">{{ userPseudo }}</span></h1>
      <p class="status-badge">Membre Actif</p>
    </header>

    <section class="rentals-section">
      <div v-if="isLoading" class="loading">Chargement de vos réservations...</div>

      <div v-else>
        <h2>Mes réservations en cours ({{ activeRentals.length }}/3)</h2>
        <p v-if="errorMessage" class="error-msg">{{ errorMessage }}</p>

        <div v-if="activeRentals.length > 0" class="rentals-list">
          <div v-for="rental in activeRentals" :key="rental.id" class="rental-card">
            <div class="rental-info">
              <h3>{{ rental.movieTitle || 'Film N°' + rental.movieId }}</h3>
              <p>Loué le : {{ new Date(rental.rentalDate).toLocaleDateString() }}</p>
            </div>
            <button class="return-btn" @click="returnMovie(rental.id)">
              Terminer la location
            </button>
          </div>
        </div>

        <div v-else class="empty-state">
          <p>Vous n'avez aucune réservation en cours.</p>
          <button @click="$router.push('/catalog')" class="go-catalog">Parcourir le catalogue</button>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const userPseudo = ref('');
const userId = ref<number | null>(null);
const activeRentals = ref<any[]>([]);
const isLoading = ref(true);
const errorMessage = ref('');

const fetchRentals = async () => {
  try {
    const response = await fetch(`/api/reservations/user/${userId.value}`);

    if (!response.ok) throw new Error(`Erreur serveur: ${response.status}`);

    const data = await response.json();
    activeRentals.value = data;
  } catch (error) {
    console.error("Erreur lors de la récupération :", error);
    errorMessage.value = "Impossible de charger vos locations actuelles.";
  } finally {
    isLoading.value = false;
  }
};

const returnMovie = async (rentalId: number) => {
  if (!confirm("Voulez-vous vraiment terminer la location de ce film ?")) return;

  try {
    const response = await fetch(`/api/reservations/${rentalId}`, {
      method: 'DELETE',
    });

    if (!response.ok) throw new Error("Erreur lors du retour du film");

    activeRentals.value = activeRentals.value.filter(r => r.id !== rentalId);
    alert("Location terminée avec succès !");
  } catch (error) {
    console.error("Erreur restitution :", error);
    alert("Erreur lors de la restitution du film.");
  }
};

onMounted(() => {
  const savedUser = localStorage.getItem('user');

  if (savedUser) {
    const user = JSON.parse(savedUser);
    userPseudo.value = user.pseudo;
    userId.value = user.id;

    fetchRentals();
  } else {
    router.push('/login');
  }
});
</script>

<style scoped>
.profile-container { max-width: 800px; margin: 40px auto; padding: 20px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
.profile-header { text-align: center; margin-bottom: 50px; }
.avatar { width: 80px; height: 80px; background: #42b883; color: white; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 2rem; font-weight: bold; margin: 0 auto 15px; }
.highlight { color: #42b883; }
.status-badge { display: inline-block; background: #e8f5e9; color: #2e7d32; padding: 5px 15px; border-radius: 20px; font-size: 0.85rem; font-weight: bold; }
.rentals-section h2 { border-bottom: 2px solid #eee; padding-bottom: 10px; margin-bottom: 20px; }
.rentals-list { display: flex; flex-direction: column; gap: 15px; }
.rental-card { display: flex; justify-content: space-between; align-items: center; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
.rental-info h3 { margin: 0; color: #2c3e50; }
.rental-info p { margin: 5px 0 0; color: #7f8c8d; font-size: 0.9rem; }
.return-btn { background: #ff5252; color: white; border: none; padding: 10px 20px; border-radius: 6px; cursor: pointer; transition: background 0.3s; }
.return-btn:hover { background: #ff1744; }
.empty-state { text-align: center; padding: 40px; background: #f9f9f9; border-radius: 10px; }
.go-catalog { background: #42b883; color: white; border: none; padding: 10px 20px; border-radius: 6px; margin-top: 15px; cursor: pointer; }
.loading { text-align: center; font-size: 1.2rem; color: #666; padding: 40px; }
.error-msg { color: #d32f2f; background: #ffebee; padding: 10px; border-radius: 4px; text-align: center; margin-bottom: 15px; }
</style>
