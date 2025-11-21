import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.page').then(m => m.LoginPage)
  },
  {
    path: 'home',
    loadComponent: () => import('./home/home.page').then(m => m.HomePage),
  },
  {
    path: 'earthquakes',
    loadComponent: () => import('./pages/earthquakes/earthquakes.page').then(m => m.EarthquakesPage)
  },
  {
    path: 'springboot-data',
    loadComponent: () => import('./pages/springboot-data/springboot-data.page').then(m => m.SpringbootDataPage)
  },
  {
    path: '',
    redirectTo: 'login', // ✅ Cambiado: Primero login
    pathMatch: 'full',
  },
];