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
  <div class="login-container">
    <h1>{{ isLoginMode ? 'Connexion' : 'Créer un compte' }}</h1>

    <form @submit.prevent="handleSubmit">
      <div>
        <label>Pseudo :</label>
        <input v-model="pseudo" type="text" placeholder="Votre pseudo" required />
      </div>
      <div>
        <label>Mot de passe :</label>
        <input v-model="password" type="password" placeholder="Votre mot de passe" required />
      </div>

      <p v-if="errorMessage" class="error-msg">{{ errorMessage }}</p>

      <button type="submit">{{ isLoginMode ? 'Se connecter' : 'S\'inscrire' }}</button>
    </form>

    <p class="toggle-text">
      {{ isLoginMode ? "Pas encore de compte ?" : "Déjà un compte ?" }}
      <a href="#" @click.prevent="toggleMode">
        {{ isLoginMode ? "Créer un compte" : "Se connecter" }}
      </a>
    </p>
  </div>
</template>

<style scoped>
.login-container {
  max-width: 350px;
  margin: 50px auto;
  padding: 30px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  background-color: white;
}
form div {
  margin-bottom: 15px;
}
label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}
input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}
button {
  width: 100%;
  padding: 12px;
  cursor: pointer;
  background-color: #646cff;
  color: white;
  border: none;
  border-radius: 4px;
  font-weight: bold;
  margin-top: 10px;
}
button:hover {
  background-color: #535bf2;
}
.error-msg {
  color: #d32f2f;
  font-size: 0.9em;
  margin: 10px 0;
  text-align: center;
}
.toggle-text {
  margin-top: 20px;
  font-size: 0.9em;
  text-align: center;
}
.toggle-text a {
  color: #646cff;
  text-decoration: none;
  font-weight: bold;
}
</style>
