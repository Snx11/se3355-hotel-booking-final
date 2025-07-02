import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../pages/HomePage.vue'
import LoginPage from '../pages/LoginPage.vue'
import RegisterPage from '../pages/RegisterPage.vue'
import SearchResults from '../pages/SearchResults.vue'
import HotelDetail from '../pages/HotelDetail.vue'

const routes = [
  { path: '/', component: HomePage },
  { path: '/login', component: LoginPage },
  { path: '/register', component: RegisterPage },
  { path: '/results', component: SearchResults },
  { path: '/hotel/:id', component: HotelDetail },
  {path: '/register', component: RegisterPage},
  {
    path: '/hotels/:id',
    name: 'HotelDetails',
    component: () => import('@/pages/HotelDetail.vue')
  }
  
]

export const router = createRouter({
  history: createWebHistory(),
  routes,
})
export default router
