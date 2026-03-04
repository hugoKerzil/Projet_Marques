import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },

  server: {
    proxy: {
      '/api/movies': {
        target: 'http://info-tpsi.univ-brest.fr:11041',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/movies/, '')
      },
      '/api/authentifications': {
        target: 'http://info-tpsi.univ-brest.fr:11040',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/authentifications/, '')
      },
      '/api/posters': {
        target: 'http://info-tpsi.univ-brest.fr:11043',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/posters/, '')
      },
      '/api/payments': {
        target: 'http://info-tpsi.univ-brest.fr:11044',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/payments/, '')
      },
      '/api/reservations': {
        target: 'http://info-tpsi.univ-brest.fr:11082',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/reservations/, '')
      },
      '/api/users': {
        target: 'http://info-tpsi.univ-brest.fr:11081',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/users/, '')
      },
      '/api/scrappings': {
        target: 'http://info-tpsi.univ-brest.fr:11063',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/scrappings/, '')
      },
      '/api/reviews': {
        target: 'http://info-tpsi.univ-brest.fr:11083',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/reviews/, '')
      },
      '/api/artists': {
        target: 'http://info-tpsi.univ-brest.fr:11065',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api\/artists/, '')
      }

      '/reservation': {
        target: 'http://info-tpsi.univ-brest.fr:11082',
        changeOrigin: true
      }
    }
  }
})
