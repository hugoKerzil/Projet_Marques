<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const isLoginMode = ref(true);
const pseudo = ref('');
const password = ref('');
const errorMessage = ref('');

// URL de ta Gateway sur le serveur UBO
const GATEWAY_URL = "http://info-tpsi.univ-brest.fr:11040";

const toggleMode = () => {
  isLoginMode.value = !isLoginMode.value;
  errorMessage.value = '';
  password.value = '';
};

const handleSubmit = async () => {
  errorMessage.value = '';

  const endpoint = isLoginMode.value ? '/auth/login' : '/auth/register';
  const url = `${GATEWAY_URL}${endpoint}`;

  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        pseudo: pseudo.value,
        password: password.value
      })
    });

    if (!response.ok) {
      // On essaye de récupérer le message d'erreur du backend
      const errorText = await response.text();
      throw new Error(errorText || 'Identifiants incorrects ou erreur serveur');
    }

    const data = await response.json();
    console.log("Succès :", data);

    // Stockage de l'utilisateur (on peut stocker le pseudo ou le token)
    localStorage.setItem('user', JSON.stringify(data));

    router.push('/catalog');

  } catch (error: any) {
    errorMessage.value = error.message;
    console.error("Erreur d'authentification :", error);
  }
};
</script>

<template>
  <div class="login-wrapper">
    <div class="login-card">
      <h2>{{ isLoginMode ? 'Bienvenue' : 'Créer un compte' }}</h2>
      <p class="subtitle">{{ isLoginMode ? 'Connectez-vous pour accéder au catalogue' : 'Rejoignez-nous dès aujourd\'hui' }}</p>

      <form @submit.prevent="handleSubmit" class="login-form">
        <div class="input-group">
          <label>Pseudo</label>
          <input v-model="pseudo" type="text" placeholder="Entrez votre pseudo" required />
        </div>
        <div class="input-group">
          <label>Mot de passe</label>
          <input v-model="password" type="password" placeholder="••••••••" required />
        </div>

        <div v-if="errorMessage" class="error-banner">{{ errorMessage }}</div>

        <button type="submit" class="submit-btn">{{ isLoginMode ? 'Se connecter' : 'S\'inscrire' }}</button>
      </form>

      <div class="toggle-section">
        {{ isLoginMode ? "Pas encore de compte ?" : "Déjà un compte ?" }}
        <a href="#" @click.prevent="toggleMode">{{ isLoginMode ? "Créer un compte" : "Se connecter" }}</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-wrapper { display: flex; justify-content: center; align-items: center; min-height: 70vh; }
.login-card { background: white; padding: 40px; border-radius: 16px; box-shadow: 0 10px 25px rgba(0,0,0,0.05); width: 100%; max-width: 400px; }
.login-card h2 { margin: 0 0 5px 0; color: #0f172a; text-align: center; font-size: 1.8rem; font-weight: 700; }
.subtitle { text-align: center; color: #64748b; margin-bottom: 30px; font-size: 0.95rem; }
.input-group { margin-bottom: 20px; }
.input-group label { display: block; margin-bottom: 8px; font-weight: 600; color: #334155; font-size: 0.9rem; }
.input-group input { width: 100%; padding: 12px; border: 1px solid #cbd5e1; border-radius: 8px; font-size: 1rem; background-color: #f8fafc; transition: border-color 0.2s; box-sizing: border-box; }
.input-group input:focus { outline: none; border-color: #3b82f6; background-color: #fff; box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1); }
.submit-btn { width: 100%; padding: 14px; background-color: #3b82f6; color: white; border: none; border-radius: 8px; font-size: 1rem; font-weight: bold; cursor: pointer; margin-top: 10px; }
.submit-btn:hover { background-color: #2563eb; transform: translateY(-1px); }
.error-banner { background-color: #fef2f2; color: #dc2626; padding: 10px; border-radius: 6px; border: 1px solid #fecaca; text-align: center; margin-bottom: 15px; font-size: 0.9rem; }
.toggle-section { margin-top: 25px; text-align: center; color: #64748b; font-size: 0.9rem; }
.toggle-section a { color: #3b82f6; font-weight: 600; margin-left: 5px; }
</style>
