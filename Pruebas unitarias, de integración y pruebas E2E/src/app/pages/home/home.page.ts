import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router'; // IMPORTAR ROUTER
import { 
  IonContent, IonHeader, IonTitle, IonToolbar, 
  IonSpinner, IonButton, IonIcon
} from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { play, informationCircleOutline } from 'ionicons/icons';
import { MovieService, Movie } from 'src/app/core/services/movie.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: true,
  imports: [
    CommonModule, IonContent, IonHeader, IonTitle, IonToolbar, 
    IonSpinner, IonButton, IonIcon
  ]
})
export class HomePage implements OnInit {
  private movieService = inject(MovieService);
  private router = inject(Router); // INYECTAR ROUTER
  
  featuredMovie: Movie | null = null;
  trendingMovies: Movie[] = [];
  
  loading = true;

  constructor() {
    addIcons({ play, informationCircleOutline });
  }

  ngOnInit() {
    this.movieService.getMovies().subscribe({
      next: (data: Movie[]) => {
        if (data.length > 0) {
          this.featuredMovie = data[0];
          this.trendingMovies = data.slice(1);
        }
        this.loading = false;
      },
      error: (err: any) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  // Función para navegar al detalle
  goToDetail(movie: Movie) {
    if (movie) {
      this.router.navigate(['/detail', movie.id]);
    }
  }

  playMovie() {
    console.log('Reproduciendo...');
  }
}