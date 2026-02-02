import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

export interface Movie {
  id: number;
  title: string;
  poster: string;
  overview: string;
  release_date: string;
  vote_average: number;
  backdrop?: string;
  genres?: { id: number; name: string }[];
}

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private http = inject(HttpClient);

  // --- TU API KEY (Verifica que sea la correcta) ---
  private apiKey = '063420501d42b80a52bc179185ae1559'; 
  
  private baseUrl = 'https://api.themoviedb.org/3';
  private imageBase = 'https://image.tmdb.org/t/p/w500'; 
  private imageBackdrop = 'https://image.tmdb.org/t/p/original'; 

  // Estado para favoritos
  private _favorites = new BehaviorSubject<Movie[]>([]);
  public favorites$ = this._favorites.asObservable();

  constructor() { }

  /**
   * Obtiene películas populares
   */
  getMovies(): Observable<Movie[]> {
    const url = `${this.baseUrl}/movie/popular?api_key=${this.apiKey}&language=es-ES&page=1`;

    return this.http.get<any>(url).pipe(
      map(response => {
        return response.results.map((m: any) => ({
          id: m.id,
          title: m.title,
          poster: m.poster_path ? this.imageBase + m.poster_path : 'assets/no-image.png',
          overview: m.overview,
          release_date: m.release_date,
          vote_average: Math.round(m.vote_average * 10) / 10
        }));
      }),
      catchError(error => {
        console.error('Error API:', error);
        return of([]);
      })
    );
  }

  /**
   * Obtiene el detalle de una película
   */
  getMovieDetail(id: string): Observable<Movie | null> {
    const url = `${this.baseUrl}/movie/${id}?api_key=${this.apiKey}&language=es-ES`;
    
    return this.http.get<any>(url).pipe(
      map(m => ({
        id: m.id,
        title: m.title,
        backdrop: m.backdrop_path ? this.imageBackdrop + m.backdrop_path : '',
        poster: m.poster_path ? this.imageBase + m.poster_path : 'assets/no-image.png',
        overview: m.overview,
        release_date: m.release_date,
        vote_average: Math.round(m.vote_average * 10) / 10,
        genres: m.genres 
      })),
      catchError(error => {
        console.error('Error Detalle:', error);
        return of(null);
      })
    );
  }

  /**
   * --- ESTA ES LA FUNCIÓN QUE FALTABA Y DABA ERROR ---
   * Busca películas por texto
   */
  searchMovies(query: string): Observable<Movie[]> {
    const url = `${this.baseUrl}/search/movie?api_key=${this.apiKey}&language=es-ES&query=${query}&page=1&include_adult=false`;

    return this.http.get<any>(url).pipe(
      map(response => {
        return response.results.map((m: any) => ({
          id: m.id,
          title: m.title,
          poster: m.poster_path ? this.imageBase + m.poster_path : 'assets/no-image.png',
          overview: m.overview,
          release_date: m.release_date,
          vote_average: Math.round(m.vote_average * 10) / 10
        }));
      }),
      catchError(error => {
        console.error('Error buscando:', error);
        return of([]);
      })
    );
  }

  // --- Favoritos ---
  toggleFavorite(movie: Movie) {
    const currentFavs = this._favorites.value;
    const exists = currentFavs.find(m => m.id === movie.id);

    if (exists) {
      this._favorites.next(currentFavs.filter(m => m.id !== movie.id));
    } else {
      this._favorites.next([...currentFavs, movie]);
    }
  }

  isFavorite(id: number): boolean {
    return !!this._favorites.value.find(m => m.id === id);
  }
}