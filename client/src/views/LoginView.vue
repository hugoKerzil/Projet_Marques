<template>
  <div class="login-container">
    <h1>Connexion</h1>
    <form @submit.prevent="$router.push('/catalog')">
      <div>
        <label>Pseudo :</label>
        <input type="text" placeholder="Votre pseudo" required />
      </div>
      <div>
        <label>Mot de passe :</label>
        <input type="password" required />
      </div>
      <button type="submit">Se connecter</button>
    </form>
  </div>
</template>

<style scoped>
.login-container {
  max-width: 300px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
form div { margin-bottom: 15px; }
input { width: 100%; padding: 8px; }
button { width: 100%; padding: 10px; cursor: pointer; }
</style>
