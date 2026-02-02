# 🎬 Videoclub Neon - Ionic & Angular App

![Ionic](https://img.shields.io/badge/Ionic-7.0-blue?style=flat&logo=ionic)
![Angular](https://img.shields.io/badge/Angular-Standalone-red?style=flat&logo=angular)
![Testing](https://img.shields.io/badge/Coverage-100%25-brightgreen)
![Cypress](https://img.shields.io/badge/E2E-Cypress-green?style=flat&logo=cypress)

Aplicación híbrida desarrollada con **Ionic 7** y **Angular** (Standalone Components) que consume la API de **TMDB**. Este proyecto forma parte de la **Actividad 2 - Tema 5**, enfocada en la implementación de pruebas unitarias (Karma/Jasmine) y pruebas E2E (Cypress).

El diseño cuenta con una estética **"Neon / Glassmorphism"** personalizada.

## 📱 Características Principales

* **Navegación por Tabs:** Inicio, Búsqueda, Favoritos, Estadísticas y Perfil.
* **Consumo de API Real:** Integración con [TheMovieDB (TMDB)](https://www.themoviedb.org/) para obtener tendencias, buscar películas y ver detalles.
* **Gestión de Estado:** Servicio con `BehaviorSubject` para manejar la lista de favoritos en tiempo real.
* **Persistencia:** Guardado de sesión y preferencias (Ajustes) mediante `localStorage`.
* **Diseño UI/UX:** Tema oscuro con acentos neón y efectos de cristal (blur) en la navegación.

---

## 🧪 Testing (Requisito Clave)

El proyecto cuenta con una robusta suite de pruebas que cubre el 100% de los requisitos de la actividad.

### 1. Pruebas Unitarias (Karma + Jasmine)
Se han implementado **28 pruebas unitarias** cubriendo Servicios, Componentes y Rutas.

* **Comando:** `npx ng test`
* **Cobertura:** * `MovieService` (Lógica de negocio y llamadas HTTP).
    * `AuthService` (Lógica de sesión).
    * Componentes de Página (`Home`, `Search`, `Favorites`, etc.).
    * Persistencia en `SettingsPage`.

### 2. Pruebas E2E (Cypress)
Se incluyen **3 flujos completos** deterministas utilizando `cy.intercept` para simular la API (Mocking), asegurando que los tests pasen sin depender de la red.

* **Comando:** `npx cypress open`
* **Flujos cubiertos:**
    1.  **Navegación Principal:** Home ➔ Detalle de Película (Verificación de carga de datos).
    2.  **Gestión de Favoritos:** Añadir a lista ➔ Volver atrás ➔ Verificar en Tab Favoritos.
    3.  **Ajustes y Persistencia:** Cambio de preferencias y verificación tras recarga.

---

## 📱 Despliegue en Android (Capacitor)

El proyecto está configurado para ejecutarse en dispositivos nativos.

1.  **Compilar el proyecto web:**
    ```bash
    ionic build
    ```

2.  **Sincronizar con Android:**
    ```bash
    npx cap sync android
    ```

3.  **Abrir en Android Studio:**
    ```bash
    npx cap open android
    ```
    *Una vez abierto, conectar el dispositivo móvil (con Depuración USB) y pulsar "Run" ▶️.*

---

## 📂 Estructura del Proyecto

```text
src/
├── app/
│   ├── core/
│   │   ├── services/       # MovieService (API), AuthService
│   │   └── interfaces/     # Modelos de datos
│   ├── pages/
│   │   ├── home/           # Carruseles y Banner Principal
│   │   ├── search/         # Buscador en tiempo real
│   │   ├── detail/         # Ficha técnica (fuera de tabs)
│   │   ├── favorites/      # Lista de favoritos (Grid)
│   │   ├── stats/          # Gráficos CSS
│   │   ├── profile/        # Login simulado y menú
│   │   └── settings/       # Ajustes con persistencia
│   └── tabs/               # Configuración de navegación
├── assets/                 # Imágenes y recursos estáticos
└── theme/                  # Variables CSS globales (Neon palette)
cypress/
└── e2e/
    └── videoclub.cy.ts     # Tests de integración completos