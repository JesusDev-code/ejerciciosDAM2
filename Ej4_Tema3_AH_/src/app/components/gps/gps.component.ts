import { Component } from '@angular/core';
import { Geolocation } from '@capacitor/geolocation';
import { IonCard, IonCardContent, IonButton , IonLabel} from '@ionic/angular/standalone';
import { DecimalPipe } from '@angular/common';
import { CommonModule } from '@angular/common';
import { IonIcon } from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { locationOutline, cameraOutline } from 'ionicons/icons';


@Component({
  selector: 'app-gps',
  templateUrl: './gps.component.html',
  standalone: true,
  imports: [
    IonCard,
    IonCardContent,
    IonButton,
    IonLabel,
    CommonModule,
    DecimalPipe,
    IonIcon,
  ],
})
export class GpsComponent {
  lat = 0;
  lng = 0;
  coordenadasVisibles = false;
  
   constructor() {
    addIcons({ locationOutline, cameraOutline });
  }

  async getCoordenadas() {
    try {
      const pos = await Geolocation.getCurrentPosition();
      if (pos) {
        this.lat = pos.coords.latitude;
        this.lng = pos.coords.longitude;
        this.coordenadasVisibles = true;
      }
    } catch (err) {
      alert('Permiso de ubicación denegado o error obteniendo las coordenadas');
      this.coordenadasVisibles = false;
    }
  }
}