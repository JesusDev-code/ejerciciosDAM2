import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { 
  IonContent, IonHeader, IonToolbar, IonSearchbar, 
  IonGrid, IonRow, IonCol, IonSpinner, IonIcon 
} from '@ionic/angular/standalone';
import { MovieService, Movie } from 'src/app/core/services/movie.service';
import { addIcons } from 'ionicons';
import { searchOutline, filmOutline } from 'ionicons/icons';

@Component({
  selector: 'app-search',
  templateUrl: './search.page.html',
  styleUrls: ['./search.page.scss'],
  standalone: true,
  imports: [
    CommonModule, FormsModule, IonContent, IonHeader, IonToolbar, 
    IonSearchbar, IonGrid, IonRow, IonCol, IonSpinner, IonIcon
  ]
})
export class SearchPage implements OnInit {
  private movieService = inject(MovieService);
  private router = inject(Router);

  results: Movie[] = [];
  isSearching = false;

  constructor() {
    addIcons({ searchOutline, filmOutline });
  }

  ngOnInit() {}

  handleInput(event: any) {
    const query = event.target.value;
    
    if (query && query.trim() !== '') {
      this.isSearching = true;
      
      // Ahora searchMovies EXISTE en el servicio, y tipamos la respuesta
      this.movieService.searchMovies(query).subscribe((movies: Movie[]) => {
        this.results = movies;
        this.isSearching = false;
      });
    } else {
      this.results = []; // Limpiar si borra el texto
      this.isSearching = false;
    }
  }

  goToDetail(movie: Movie) {
    this.router.navigate(['/detail', movie.id]);
  }
}