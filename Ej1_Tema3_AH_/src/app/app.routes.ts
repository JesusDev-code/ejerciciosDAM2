import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./home/home.page').then(m => m.HomePage), // Home como layout global
    children: [
      { path: '', redirectTo: 'list/servicios', pathMatch: 'full' },
      {
        path: 'list',
        children: [
          { path: 'perfil', loadComponent: () => import('./pages/perfil.page').then(m => m.PerfilPage) },
          { path: 'datos',  loadComponent: () => import('./pages/datos.page').then(m => m.DatosPage) },
          { path: ':cat',   loadComponent: () => import('./pages/cards-list.page').then(m => m.CardsListPage) },
          { path: '', redirectTo: 'servicios', pathMatch: 'full' }
        ]
      },
  
      { path: 'cliente', loadComponent: () => import('./cliente/cliente.page').then( m => m.ClientePage) },
      { path: 'datoscliente', loadComponent: () => import('./datoscliente/datoscliente.page').then( m => m.DatosclientePage) },
      { path: '**', redirectTo: 'list/servicios' }
    ]
  }
];