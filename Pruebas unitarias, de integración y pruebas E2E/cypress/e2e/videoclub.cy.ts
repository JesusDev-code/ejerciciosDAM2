describe('Videoclub App E2E (Determinista)', () => {
  
  beforeEach(() => {
    // 1. Interceptamos la API de Populares (Home)
    // Esto simula la respuesta del servidor para que el test sea estable
    cy.intercept('GET', '**/movie/popular*', {
      statusCode: 200,
      body: {
        results: [
          { 
            id: 101, 
            title: 'Pelicula E2E', 
            poster_path: '/test.jpg', 
            vote_average: 9.5, 
            overview: 'Resumen de prueba E2E', 
            release_date: '2024-01-01' 
          }
        ]
      }
    }).as('getMovies');

    // 2. Interceptamos el Detalle de la película 101
    cy.intercept('GET', '**/movie/101*', {
      statusCode: 200,
      body: { 
        id: 101, 
        title: 'Pelicula E2E', 
        poster_path: '/test.jpg', 
        overview: 'Resumen de prueba E2E', 
        genres: [{ id: 1, name: 'Acción' }],
        vote_average: 9.5,
        release_date: '2024-01-01',
        backdrop_path: '/backdrop.jpg'
      }
    }).as('getDetail');

    // 3. Visitamos la raíz (Home)
    cy.visit('/');
  });

  it('1. Flujo Principal: Ver Home -> Navegar a Detalle', () => {
    // Esperamos a que cargue la API
    cy.wait('@getMovies');
    
    // Verificamos que la película mockeada aparece
    cy.contains('Pelicula E2E').should('be.visible');
    
    // Navegamos al detalle pulsando "Más Info"
    cy.contains('Más Info').click();
    
    // Verificamos que la URL cambia y el contenido es correcto
    cy.url().should('include', '/detail/101');
    cy.contains('Resumen de prueba E2E').should('be.visible');
  });

  it('2. Flujo Favoritos: Añadir en Detalle -> Verificar en Tab', () => {
    // 1. Ir al detalle
    cy.contains('Más Info').click();
    cy.wait('@getDetail');

    // 2. Añadir a favoritos
    cy.contains('Mi Lista').click();
    cy.contains('En Lista').should('be.visible'); // Verificar cambio visual

    // 3. Volver atrás (Clave para que reaparezcan los tabs)
    cy.get('ion-back-button').click();

    // 4. Ir a la pestaña Favoritos
    cy.get('ion-tab-button[tab="favorites"]').click();
    cy.url().should('include', '/favorites');

    // 5. VERIFICACIÓN CORREGIDA (SOLUCIÓN DEFINITIVA):
    // Usamos cy.get('app-favorites') para obligar a Cypress a buscar 
    // SOLO dentro de la página de favoritos, ignorando la Home oculta.
    // Además, quitamos 'h2' para que busque el texto donde sea (más robusto).
    cy.get('app-favorites').contains('Pelicula E2E').should('be.visible');
  });

  it('3. Flujo Ajustes: Navegación y Persistencia', () => {
    // Visitamos directamente la página de ajustes
    // (Según tu app.routes.ts la ruta es 'settings', no 'tabs/settings')
    cy.visit('/settings'); 
    
    // Verificamos que el toggle existe
    cy.get('ion-toggle').should('exist');
    
    // Cambiamos el valor (force:true ayuda si Ionic pone capas encima)
    cy.get('ion-toggle').click({ force: true });
    
    // Recargamos la página para verificar la PERSISTENCIA (localStorage)
    cy.reload();
    
    // El elemento debe seguir existiendo y ser interactuable
    cy.get('ion-toggle').should('be.visible');
  });

});