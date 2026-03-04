import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'login', component: () => import('../views/LoginView.vue') },
    { path: '/catalog', name: 'catalog', component: () => import('../views/CatalogView.vue') },
    { path: '/movie/:id', name: 'movie-detail', component: () => import('../views/MovieDetailView.vue') },
    { path: '/movie/:id/payment', name: 'payment', component: () => import('../views/PaymentView.vue') },
    { path: '/profile', name: 'profile', component: () => import('../views/ProfileView.vue') }
  ]
})

export default router
