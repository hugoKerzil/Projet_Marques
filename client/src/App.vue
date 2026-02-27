<template>
  <header>
    <nav>
      <RouterLink to="/catalog">Catalogue</RouterLink>
      <RouterLink to="/">Connexion</RouterLink>
    </nav>
  </header>

  <main>
    <RouterView />
  </main>
</template>

<style scoped>
nav { display: flex; gap: 20px; padding: 1rem; background: #1a1a1a; color: white; }
nav a { color: white; text-decoration: none; font-weight: bold; }
main { padding: 20px; }
</style>
