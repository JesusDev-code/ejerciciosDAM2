import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  // Variable para guardar datos y no pedirlos dos veces
  private datosCache: any[] | null = null;

  constructor(private http: HttpClient) { }

  getUsuarios(): Observable<any[]> {
    // 1. Si ya hay datos en memoria, devuélvelos (CACHÉ)
    if (this.datosCache) {
      console.log('⚡ Recuperando desde caché (Ahorrando petición HTTP)');
      return of(this.datosCache);
    }

    // 2. Si no, hacemos la petición a la API falsa
    const url = 'https://jsonplaceholder.typicode.com/photos';
    
    return this.http.get<any[]>(url).pipe(
      tap(datos => {
        console.log('🌐 Petición HTTP realizada');
        // Guardamos solo 1000 para simular la carga pesada
        this.datosCache = datos.slice(0, 1000); 
      })
    );
  }
}