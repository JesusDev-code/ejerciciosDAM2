import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { 
  IonContent, IonHeader, IonTitle, IonToolbar, IonGrid, IonRow, IonCol, 
  IonIcon, IonButtons, IonButton 
} from '@ionic/angular/standalone';
import { MovieService, Movie } from 'src/app/core/services/movie.service';
import { addIcons } from 'ionicons';
import { heart, searchOutline, heartDislikeOutline } from 'ionicons/icons';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.page.html',
  styleUrls: ['./favorites.page.scss'],
  standalone: true,
  imports: [
    CommonModule, IonContent, IonHeader, IonTitle, IonToolbar,
    IonGrid, IonRow, IonCol, IonIcon, IonButtons, IonButton
  ]
})
export class FavoritesPage implements OnInit {
  private movieService = inject(MovieService);
  favorites: Movie[] = [];

  constructor() {
    addIcons({ heart, searchOutline, heartDislikeOutline });
  }

  ngOnInit() {
    this.movieService.favorites$.subscribe(favs => {
      this.favorites = favs;
    });
  }

  remove(movie: Movie) {
    // Aquí puedes preguntar antes de borrar o navegar al detalle
    this.movieService.toggleFavorite(movie);
  }
}