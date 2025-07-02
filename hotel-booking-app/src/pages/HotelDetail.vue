<template>
  <div class="container" v-if="hotel">
    <h2>{{ hotel.name }}</h2>
    <img :src="hotel.imageUrl" class="main-img" />

    <p><strong>Konum:</strong> {{ hotel.city }}, {{ hotel.country }}</p>

    <p><strong>Fiyat:</strong> {{ getDisplayedPrice(hotel) }}₺ / gece</p>

    <p v-if="hotel.discountPercentage > 0" class="discount-label">
      %{{ hotel.discountPercentage }} indirim
    </p>

    <p v-if="!user" class="login-warning text-danger">
      Üye fiyatı için giriş yapın
    </p>

    <p><strong>Puan:</strong> ⭐ {{ hotel.rating }} / 5</p>

    <h4>Özellikler:</h4>
    <ul>
      <li v-for="(amenity, index) in hotel.amenities" :key="index">✔️ {{ amenity }}</li>
    </ul>

    <h3>Konum</h3>
    <p>Otel harita üzerinde burada yer alıyor:</p>
    <div id="map" class="map-area" ref="mapContainer"></div>

    <div v-if="hotel.comments?.length">
      <RatingChart :scores="hotel.comments.map(c => c.score).filter(s => s !== null)" />
      <h4>{{ hotel.comments.length }} Yorum</h4>
      <div v-for="comment in hotel.comments" :key="comment.id" class="comment-box">
        <p><strong>{{ comment.authorName || 'Anonim' }}:</strong> {{ comment.content || 'Yorum yok.' }}</p>
        <p v-if="comment.score !== null">Servis Puanı: {{ comment.score }}/10</p>
        <p v-else>Servis Puanı belirtilmemiş.</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch, nextTick } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import type { Map as LeafletMap } from 'leaflet'
import RatingChart from '@/components/RatingChart.vue'

const route = useRoute()
const hotel = ref<any>(null)
const user = ref<{ firstName?: string; lastName?: string } | null>(null)
const mapInstance = ref<LeafletMap | null>(null)
const mapContainer = ref<HTMLElement | null>(null)

const loadHotel = async (id: string) => {
  try {
    const response = await axios.get(`http://localhost:8081/api/hotels/${id}`)
    hotel.value = response.data
  } catch (err) {
    console.error('Otel detayı alınamadı', err)
  }
}

const initMap = () => {
  if (!hotel.value || !mapContainer.value) return

  const { latitude, longitude, name } = hotel.value
  const position: [number, number] = [latitude, longitude]

  if (mapInstance.value) {
    mapInstance.value.remove()
  }

  const map = L.map(mapContainer.value).setView(position, 13)

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
  }).addTo(map)

  L.marker(position).addTo(map).bindPopup(name).openPopup()

  mapInstance.value = map

  setTimeout(() => {
    map.invalidateSize()
  }, 500)
}

onMounted(async () => {
  const savedUser = localStorage.getItem('user')
  if (savedUser) {
    user.value = JSON.parse(savedUser)
  }

  await loadHotel(route.params.id as string)
  await nextTick()
  setTimeout(() => {
    initMap()
  }, 300)
})

watch(() => route.params.id, async (newId) => {
  await loadHotel(newId as string)
  await nextTick()
  initMap()
})

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
</script>

<style scoped>
.container {
  padding: 24px;
}
.main-img {
  width: 100%;
  max-height: 300px;
  object-fit: cover;
  margin-bottom: 20px;
  border-radius: 8px;
}
ul {
  padding-left: 16px;
}
.comment-box {
  background: #f7f7f7;
  padding: 12px;
  margin-bottom: 10px;
  border-radius: 8px;
}
.discount-label {
  background-color: #ffc107;
  padding: 4px 8px;
  border-radius: 6px;
  font-size: 0.85rem;
  display: inline-block;
  margin-bottom: 8px;
}
.login-warning {
  font-size: 0.9rem;
  margin-bottom: 10px;
}
.map-area {
  width: 100%;
  height: 300px;
  margin: 20px 0;
  border-radius: 8px;
  border: 2px solid #007bff;
  z-index: 0;
}
</style>
