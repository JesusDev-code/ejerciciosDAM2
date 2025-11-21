import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SpringBootItem, PageResponse } from '../models/springboot-data.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class SpringbootService {
  private apiUrl = 'https://tfg-damm.onrender.com/api/diarios/mis-diarios';

  constructor(
    private http: HttpClient,
    private authService: AuthService // ✅ Inyectado
  ) { }

  /**
   * Obtiene diarios con token automático
   */
  getData(): Observable<PageResponse<SpringBootItem>> {
    return new Observable(observer => {
      this.authService.getToken().then(token => {
        if (!token) {
          observer.error({ message: 'No hay sesión activa' });
          return;
        }

        const headers = new HttpHeaders({
          'Authorization': `Bearer ${token}`
        });

        this.http.get<PageResponse<SpringBootItem>>(this.apiUrl, { headers })
          .subscribe(
            data => observer.next(data),
            error => observer.error(error)
          );
      });
    });
  }
}