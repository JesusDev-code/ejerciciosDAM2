import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  IonHeader, IonToolbar, IonTitle, IonContent,
  IonItem, IonInput, IonList, IonLabel, IonButton, IonNote, IonSpinner,
  IonInfiniteScroll, IonInfiniteScrollContent, InfiniteScrollCustomEvent // Importamos Infinite Scroll
} from '@ionic/angular/standalone';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs'; // Importante para gestionar suscripciones

import { SocialBadService, Post } from '../../core/services/social-bad.service';
import { KpiService } from '../../core/metrics/kpi.service';

@Component({
  standalone: true,
  selector: 'app-home',
  imports: [
    CommonModule,
    IonHeader, IonToolbar, IonTitle, IonContent,
    IonItem, IonInput, IonList, IonLabel, IonButton, IonNote, IonSpinner,
    IonInfiniteScroll, IonInfiniteScrollContent // Añadimos a imports
  ],
  templateUrl: './home.page.html',
})
export class HomePage implements OnInit, OnDestroy {
  query = '';
  loading = false;
  error = '';

  // ESTRATEGIA DE PAGINACIÓN E INFINITE SCROLL
  allPosts: Post[] = [];       // Todos los datos (1000)
  displayedPosts: Post[] = []; // Solo los que se ven (24, 48...)
  pageSize = 24;               // Objetivo KPI: 24
  currentPage = 0;

  // GESTIÓN DE SUSCRIPCIONES
  private sub: Subscription | null = null;

  constructor(
    private api: SocialBadService,
    private router: Router,
    public kpi: KpiService
  ) {}

  ngOnInit() {
    // KPI arranque
    const navStart = (performance.getEntriesByType('navigation')[0] as any)?.startTime ?? 0;
    this.kpi.setStartupMs(Math.round(performance.now() - navStart));

    this.loading = true;

    // Petición ÚNICA y optimizada
    this.kpi.incSub(); // Contamos la suscripción
    this.sub = this.api.getPosts().subscribe({
      next: data => {
        // Mantenemos la lógica de inflar x10 para tener datos suficientes para scroll
        const big: Post[] = [];
        for (let i = 0; i < 10; i++) big.push(...data); // 1000 items
        
        this.allPosts = big;
        
        // En lugar de mostrar todo, cargamos la primera página
        this.loadNextPage();
        
        this.loading = false;
      },
      error: () => {
        this.error = 'Error cargando posts';
        this.loading = false;
      }
    });

    // Eliminada la segunda petición duplicada intencional
  }

  // Lógica para Infinite Scroll
  loadNextPage(event?: InfiniteScrollCustomEvent) {
    const start = this.currentPage * this.pageSize;
    const end = start + this.pageSize;
    
    // Si estamos filtrando, usamos filteredPosts (lógica simplificada para este ejercicio: paginamos todo)
    // Para simplificar: paginamos sobre lo que haya en allPosts filtrado
    
    const nextBatch = this.allPosts.slice(start, end);
    this.displayedPosts = [...this.displayedPosts, ...nextBatch];
    this.currentPage++;

    // Actualizamos KPI de renderizado (Ahora será 24, 48... no 1000)
    this.kpi.setRenderItems(this.displayedPosts.length);

    if (event) {
      event.target.complete();
      if (this.displayedPosts.length >= this.allPosts.length) {
        event.target.disabled = true;
      }
    }
  }

  onInput(ev: any) {
    const t0 = performance.now();
    const value = (ev?.target?.value ?? '').toString();
    this.query = value;

    // ELIMINADO: Bucle dummy pesado (for 90000)
    
    // OPTIMIZACIÓN: Filtrado local, sin peticiones HTTP
    const q = value.toLowerCase();
    
    if (!q) {
      // Si borran, reiniciamos vista normal
      this.currentPage = 0;
      this.displayedPosts = [];
      this.loadNextPage(); // Recarga primera página de 24
    } else {
      // Si escriben, filtramos en memoria (sin llamar a la API)
      const matches = this.allPosts.filter(p => 
        (p.title + ' ' + p.body).toLowerCase().includes(q)
      );
      // Mostramos resultados (podríamos paginar también, pero mostramos los encontrados)
      this.displayedPosts = matches.slice(0, 50); // Límite de seguridad
      this.kpi.setRenderItems(this.displayedPosts.length);
    }

    this.kpi.addInputSample(performance.now() - t0);
  }

  prettyTitle(p: Post): string {
    // ELIMINADO: Bucle dummy pesado (for 7000)
    // Retorno directo y rápido
    return `[${p.id}] ${p.title.toUpperCase()}`;
  }

  openPost(p: Post) {
    this.router.navigate(['/detail'], { queryParams: { id: p.id, title: p.title } });
  }

  goSettings() {
    this.router.navigate(['/settings']);
  }

  // IMPORTANTE: Liberar memoria
  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  // Función trackBy para optimizar el DOM
  trackByFn(index: number, item: Post) {
    return item.id;
  }
}