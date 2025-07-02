import { createApp } from 'vue'
import App from './App.vue'
import { createPinia } from 'pinia'
import { router } from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
//import VueGoogleMaps from '@fawmi/vue-google-maps'

const app = createApp(App)

app.use(createPinia())
app.use(router)

//app.use(VueGoogleMaps, {
//  load: {
  //  key: 'AIzaSyCgYEWbRAmwB9CGr_lD-8-44pOEVym1vRo',
 // },
//})

app.mount('#app')
