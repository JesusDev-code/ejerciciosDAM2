import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { IonContent, IonHeader, IonToolbar, IonButtons, IonBackButton, IonIcon, IonButton, IonSpinner } from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { star, calendar, time, playCircle, heart, heartOutline, shareSocialOutline, play, add, checkmark, arrowBack } from 'ionicons/icons';
import { MovieService, Movie } from 'src/app/core/services/movie.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.page.html',
  styleUrls: ['./detail.page.scss'],
  standalone: true,
  imports: [CommonModule, IonContent, IonHeader, IonToolbar, IonButtons, IonBackButton, IonIcon, IonButton, IonSpinner]
})
export class DetailPage implements OnInit {
  private route = inject(ActivatedRoute);
  private movieService = inject(MovieService);
  
  movie: Movie | null = null;
  isFav = false;

  constructor() {
    // Registramos todos los iconos necesarios
    addIcons({ star, calendar, time, playCircle, heart, heartOutline, shareSocialOutline, play, add, checkmark, arrowBack });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.movieService.getMovieDetail(id).subscribe(data => {
        this.movie = data;
        if (this.movie) {
          this.checkFavorite();
        }
      });
    }
  }

  checkFavorite() {
    if (this.movie) {
      this.isFav = this.movieService.isFavorite(this.movie.id);
    }
  }

  toggleFavorite() {
    if (this.movie) {
      this.movieService.toggleFavorite(this.movie);
      this.isFav = !this.isFav; // Actualizamos el icono visualmente
    }
  }
}