import { defineConfig } from 'cypress';

export default defineConfig({
  e2e: {
    baseUrl: 'http://localhost:8100', // <--- ESTO ES LO IMPORTANTE
    supportFile: false, // Desactivamos esto si no lo usas para evitar otros errores
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});