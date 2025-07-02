<template>
  <div class="container mt-4">
    <!-- HEADER -->
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h1 class="fw-bold page-title">Hotels.com</h1>
      <div v-if="fullName" class="text-success fw-semibold d-flex flex-column align-items-end">
        {{ fullName }}
        <button class="btn btn-sm btn-outline-danger mt-1" @click="logout">Çıkış Yap</button>
      </div>
      <a v-else href="#" class="text-primary fw-semibold" @click.prevent="goToLogin">Giriş Yap</a>
    </div>

    <!-- SEARCH BAR -->
    <form @submit.prevent="onSearch" class="row g-3 mb-4 search-form">
      <div class="col-md-3">
        <input type="text" class="form-control" v-model="city" placeholder="Şehir" />
      </div>
      <div class="col-md-2">
        <input type="date" class="form-control" v-model="checkIn" />
      </div>
      <div class="col-md-2">
        <input type="date" class="form-control" v-model="checkOut" />
      </div>
      <div class="col-md-2">
        <input type="number" min="1" class="form-control" v-model="guests" placeholder="Kişi Sayısı" />
      </div>
      <div class="col-md-3">
        <button class="btn btn-primary w-100">Ara</button>
      </div>
    </form>

    <!-- OTELLER + HARİTA -->
    <div class="row">
      <div class="col-md-4">
        <div class="map-area">
          <h5 class="fw-bold">🗺️ Harita</h5>
          <p class="text-muted mb-2">Otellerin konumu:</p>
          <div id="map" class="leaflet-map"></div>
        </div>
      </div>

      <div class="col-md-8">
        <router-link
          v-for="hotel in hotels"
          :key="hotel.id"
          :to="`/hotels/${hotel.id}`"
          class="text-decoration-none text-dark"
        >
          <div class="card mb-3 hotel-card hover-shadow">
            <div class="row g-0">
              <div class="col-md-4">
                <img :src="hotel.imageUrl" class="img-fluid rounded-start hotel-image" />
              </div>
              <div class="col-md-8">
                <div class="card-body">
                  <h5 class="card-title">{{ hotel.name }}</h5>
                  <p class="card-text">
                    <strong>Şehir:</strong> {{ hotel.city }}<br />
                    <strong>Puan:</strong> <span class="rating">{{ hotel.rating }} ⭐</span><br />
                    <strong>Fiyat:</strong>
                    <span class="price">{{ getDisplayedPrice(hotel) }} TL / gece</span><br />
                    <span v-if="hotel.discountPercentage > 0" class="badge bg-warning text-dark mt-1">
                      %{{ hotel.discountPercentage }} indirim
                    </span><br />
                    <span v-if="!user" class="text-danger small">
                      Üye fiyatı için giriş yapın
                    </span>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'

const router = useRouter()

const city = ref('')
const checkIn = ref('')
const checkOut = ref('')
const guests = ref(2)
const hotels = ref<any[]>([])

const user = ref<{ firstName?: string; lastName?: string } | null>(null)

const fullName = computed(() => {
  if (user.value?.firstName && user.value?.lastName) {
    return `Merhaba ${user.value.firstName} ${user.value.lastName}`
  }
  return null
})

const logout = () => {
  localStorage.removeItem('user')
  user.value = null
  router.push('/')
}

const goToLogin = () => {
  router.push('/login')
}

const onSearch = () => {
  router.push({
    path: '/results',
    query: {
      city: city.value,
      checkIn: checkIn.value,
      checkOut: checkOut.value,
      guests: guests.value.toString(),
    },
  })
}

const getDisplayedPrice = (hotel: any) => {
  let price = hotel.basePrice
  if (hotel.discountPercentage > 0) {
    price *= 1 - hotel.discountPercentage / 100
  }
  if (user.value) {
    price *= 0.9
  }
  return price.toFixed(2)
}

const initMap = () => {
  if (!hotels.value.length) return

  const map = L.map('map')

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors',
  }).addTo(map)

  const bounds: [number, number][] = []

  hotels.value.forEach(hotel => {
    if (hotel.latitude && hotel.longitude) {
      const pos: [number, number] = [hotel.latitude, hotel.longitude]
      bounds.push(pos)
      L.marker(pos).addTo(map).bindPopup(hotel.name)
    }
  })

  if (bounds.length > 0) {
    map.fitBounds(bounds, { padding: [20, 20] })
  } else {
    map.setView([39, 35], 6)
  }
}

const fetchHotels = async () => {
  try {
    const response = await axios.get('http://localhost:8081/api/hotels')
    hotels.value = response.data
    await nextTick()
    initMap()
  } catch (error) {
    console.error('Oteller alınamadı:', error)
  }
}

onMounted(() => {
  const savedUser = localStorage.getItem('user')
  if (savedUser) {
    user.value = JSON.parse(savedUser)
  }
  fetchHotels()
})
</script>

<style scoped>
.map-area {
  border: 2px solid #1e90ff;
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 20px;
}
.leaflet-map {
  height: 300px;
  width: 100%;
  border-radius: 8px;
}
.badge {
  font-size: 0.85rem;
  padding: 5px 10px;
  border-radius: 6px;
}
.hover-shadow:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
  transition: box-shadow 0.2s ease-in-out;
}
</style>
