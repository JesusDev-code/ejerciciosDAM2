import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';
import { KpiService } from '../metrics/kpi.service';

export type Post = { userId: number; id: number; title: string; body: string; };
export type Comment = { postId: number; id: number; name: string; email: string; body: string; };

@Injectable({ providedIn: 'root' })
export class SocialBadService {
  
  // 1. Variable para Cachear los datos
  private postsCache: Post[] | null = null;

  constructor(private http: HttpClient, private kpi: KpiService) {}

  getPosts(): Observable<Post[]> {
    // 2. Si ya tenemos datos, los devolvemos sin llamar a la API ni incrementar el KPI HTTP
    if (this.postsCache) {
      // No llamamos a kpi.incHttp() aquí porque no es una petición real
      return of(this.postsCache);
    }

    // 3. Si no hay caché, hacemos la petición real
    this.kpi.incHttp(); // Contamos la petición real
    return this.http.get<Post[]>('https://jsonplaceholder.typicode.com/posts').pipe(
      tap(data => {
        this.postsCache = data; // Guardamos en memoria
      })
    );
  }

  getComments(postId: number): Observable<Comment[]> {
    this.kpi.incHttp();
    return this.http.get<Comment[]>(`https://jsonplaceholder.typicode.com/posts/${postId}/comments`);
  }
}