<template>
  <div class="register-container">
    <div class="form-box">
      <h2>Kayıt Ol</h2>

      <form @submit.prevent="register">
        <div class="form-group">
          <label>Ad</label>
          <input v-model="firstName" type="text" required />
        </div>

        <div class="form-group">
          <label>Soyad</label>
          <input v-model="lastName" type="text" required />
        </div>

        <div class="form-group">
          <label>E-mail</label>
          <input v-model="email" type="email" required />
        </div>

        <div class="form-group">
          <label>Şifre</label>
          <input v-model="password" type="password" required />
        </div>

        <div class="form-group">
          <label>Şifre Tekrar</label>
          <input v-model="passwordConfirm" type="password" required />
        </div>

        <div class="form-group">
          <label>Ülke</label>
          <select v-model="country">
            <option value="Türkiye">Türkiye</option>
            <option value="Almanya">Almanya</option>
            <option value="Fransa">Fransa</option>
          </select>
        </div>

        <div class="form-group">
          <label>Şehir</label>
          <select v-model="city">
            <option value="İstanbul">İstanbul</option>
            <option value="Ankara">Ankara</option>
            <option value="İzmir">İzmir</option>
          </select>
        </div>

        <button type="submit">Kayıt Ol</button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()

const firstName = ref('')
const lastName = ref('')
const email = ref('')
const password = ref('')
const passwordConfirm = ref('')
const country = ref('Türkiye')
const city = ref('İstanbul')

const register = async () => {
  if (password.value !== passwordConfirm.value) {
    alert('Şifreler eşleşmiyor!')
    return
  }

  try {
    await axios.post('http://localhost:8081/api/users', {
      firstName: firstName.value,
      lastName: lastName.value,
      email: email.value,
      password: password.value,
      city: city.value,
      country: country.value
    })

    alert('Kayıt başarılı!')
    router.push('/login')
  } catch (err) {
    alert('Kayıt başarısız!')
    console.error(err)
  }
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f2f2f2;
}

.form-box {
  background: white;
  padding: 32px;
  border-radius: 12px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

h2 {
  margin-bottom: 24px;
  text-align: center;
}

.form-group {
  margin-bottom: 16px;
  display: flex;
  flex-direction: column;
}

input,
select {
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  outline: none;
  font-size: 14px;
}

button {
  width: 100%;
  padding: 12px;
  background-color: #1f8ef1;
  color: white;
  font-weight: bold;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

button:hover {
  background-color: #0f75d3;
}
</style>
