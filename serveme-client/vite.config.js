import { defineConfig, loadEnv } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd())

  return {
    plugins: [react()],
    server: {
      port: 22800,
      host: true,
      // Proxy para requisições relativas (/api/...)
      // A URL definitiva vem de VITE_API_BASE_URL no .env
      proxy: {
        '/api': {
          target: env.VITE_API_BASE_URL,
          changeOrigin: true,
        },
      },
    },
  }
})

// host: true` faz o Vite escutar em `0.0.0.0`, expondo para toda a rede local.