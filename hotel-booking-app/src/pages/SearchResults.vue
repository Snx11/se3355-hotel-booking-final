<template>
  <div class="container">
    <h2>Otel Arama Sonuçları</h2>

    <div v-if="hotels.length === 0" class="no-results">
      <p>Hiç otel bulunamadı.</p>
    </div>

    <div v-else class="hotels">
      <div v-for="hotel in hotels" :key="hotel.id" class="hotel-card">
        <img :src="hotel.imageUrl" alt="otel resmi" class="hotel-image" />
        <div class="hotel-info">
          <router-link :to="`/hotels/${hotel.id}`" class="hotel-name">
            <h3>{{ hotel.name }}</h3>
          </router-link>
          <p>{{ hotel.city }}, {{ hotel.country }}</p>
          <p>⭐ {{ hotel.rating }} / 5</p>

          <!-- Fiyat ve İndirim -->
          <p>Fiyat: {{ getDisplayedPrice(hotel) }}₺ / gece</p>
          <p v-if="hotel.discountPercentage > 0" class="discount-label">
            %{{ hotel.discountPercentage }} indirim
          </p>
          <p v-if="!user" class="login-warning text-danger fw-semibold">
            Üye fiyatı için giriş yapın
          </p>

          <p v-if="hotel.availableWeekend">Hafta sonu müsait ✅</p>
          <p v-else>Hafta sonu uygun değil ❌</p>
        </div>
      </div>
    </div>
  </div>  
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'

interface Hotel {
  id: number
  name: string
  city: string
  country: string
  rating: number
  capacity: number
  basePrice: number
  discountPercentage: number
  availableWeekend: boolean
  imageUrl: string
}

const hotels = ref<Hotel[]>([])
const route = useRoute()
const user = ref<{ firstName?: string; lastName?: string } | null>(null)

onMounted(async () => {
  const savedUser = localStorage.getItem('user')
  if (savedUser) {
    user.value = JSON.parse(savedUser)
  }

  try {
    const city = route.query.city as string
    const from = route.query.checkIn as string
    const to = route.query.checkOut as string
    const guestCount = parseInt(route.query.guests as string)

    const response = await axios.post('http://localhost:8081/api/hotels/search', {
      city,
      from,
      to,
      guestCount
    })

    hotels.value = response.data
  } catch (error) {
    console.error('Otel verileri alınamadı:', error)
  }
})

const getDisplayedPrice = (hotel: Hotel) => {
  let price = hotel.basePrice

  if (hotel.discountPercentage > 0) {
    price = price * (1 - hotel.discountPercentage / 100)
  }

  if (user.value) {
    price = price * 0.9 // %10 üye indirimi
  }

  return price.toFixed(2)
}
</script>

<style scoped>
.container {
  padding: 20px;
}
.hotel-name {
  text-decoration: none;
  color: inherit;
}

.hotel-name:hover {
  text-decoration: underline;
  color: #1f8ef1;
  cursor: pointer;
}

.hotels {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}
.hotel-card {
  width: 300px;
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 10px;
}
.hotel-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 6px;
}
.hotel-info {
  margin-top: 10px;
}
.no-results {
  color: #666;
  font-style: italic;
}
.discount-label {
  background-color: #ffc107;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 0.85rem;
  display: inline-block;
  margin-top: 4px;
}
.login-warning {
  font-size: 0.9rem;
  margin-top: 6px;
}
</style>
