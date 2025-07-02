<template>
  <div class="login-container">
    <div class="form-box">
      <h2>Giriş Yap</h2>
      <form @submit.prevent="login">
        <div class="form-group">
          <input v-model="email" type="email" placeholder="E-posta" required />
        </div>

        <div class="form-group">
          <input v-model="password" type="password" placeholder="Şifre" required />
        </div>

        <button type="submit">Giriş</button>
        <p v-if="error" class="error">{{ error }}</p>
      </form>

      <div class="google-login">
        <button class="google-btn" @click="loginWithGoogle">
          <img src="https://www.svgrepo.com/show/475656/google-color.svg" alt="Google" />
          Google ile Giriş Yap
        </button>
      </div>

      <p class="register-link">
        Hesabınız yok mu? <router-link to="/register">Kayıt Ol</router-link>
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import router from '@/router'

const email = ref('')
const password = ref('')
const error = ref('')

const login = async () => {
  try {
    const response = await axios.post('http://localhost:8081/api/auth/login', {
      email: email.value,
      password: password.value
    })

    const user = response.data
    localStorage.setItem('user', JSON.stringify(user))
    router.push('/')
  } catch (err) {
    error.value = 'Giriş başarısız! Bilgileri kontrol et.'
  }
}

// 🔐 Google ile giriş (backend yönlendirmesine göre değişebilir)
const loginWithGoogle = () => {
  window.location.href = 'http://localhost:8081/oauth2/authorization/google'
}

</script>

<style scoped>
.login-container {
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
  text-align: center;
  margin-bottom: 24px;
}

.form-group {
  margin-bottom: 16px;
}

input {
  width: 100%;
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
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

.error {
  color: red;
  margin-top: 12px;
  text-align: center;
}
.google-login {
  margin-top: 20px;
  text-align: center;
}

.google-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  width: 100%;
  padding: 10px;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
}

.google-btn img {
  width: 20px;
  height: 20px;
}

.google-btn:hover {
  background-color: #f5f5f5;
}

</style>
