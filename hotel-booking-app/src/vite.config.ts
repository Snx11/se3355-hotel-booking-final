import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  optimizeDeps: {
    include: ['fast-deep-equal'],
  },
  build: {
    commonjsOptions: {
      transformMixedEsModules: true, // 💥 bu satır kritik
    },
  },
})
