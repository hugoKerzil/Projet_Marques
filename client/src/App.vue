<script setup lang="ts">
</script>

<template>
  <header class="top-navbar">
    <div class="nav-container">
      <div class="logo">🎬 VOD Platform</div>
      <nav class="nav-links">
        <RouterLink to="/catalog">Catalogue</RouterLink>
        <RouterLink to="/profile">Profil</RouterLink>
        <RouterLink to="/" class="btn-login">Connexion</RouterLink>
      </nav>
    </div>
  </header>

  <main class="main-layout">
    <RouterView />
  </main>
</template>

<style scoped>
.top-navbar {
  background-color: #ffffff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  position: sticky;
  top: 0;
  z-index: 1000;
}
.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.logo {
  font-size: 1.5rem;
  font-weight: 800;
  color: #1e293b;
  letter-spacing: -0.5px;
}
.nav-links {
  display: flex;
  gap: 20px;
  align-items: center;
}
.nav-links a {
  color: #64748b;
  font-weight: 600;
  padding: 0.5rem 1rem;
  border-radius: 6px;
}
.nav-links a:hover {
  color: #0f172a;
  background-color: #f1f5f9;
}
.nav-links a.router-link-exact-active {
  color: #3b82f6;
}
.nav-links a.btn-login {
  background-color: #3b82f6;
  color: white;
}
.nav-links a.btn-login:hover {
  background-color: #2563eb;
}
.main-layout {
  flex: 1;
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
  box-sizing: border-box;
}
</style>
