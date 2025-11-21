import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule, LoadingController, AlertController } from '@ionic/angular';
import { EarthquakeService } from '../../services/earthquake';
import { Feature } from '../../models/earthquake.model';

@Component({
  selector: 'app-earthquakes',
  templateUrl: './earthquakes.page.html',
  styleUrls: ['./earthquakes.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule],
   providers: [EarthquakeService, LoadingController, AlertController]
})
export class EarthquakesPage implements OnInit {
  latestEarthquake: Feature | null = null;
  loading = false;

  constructor(
    private earthquakeService: EarthquakeService,
    private loadingCtrl: LoadingController,
    private alertCtrl: AlertController
  ) { }

  ngOnInit() {
    console.log('EarthquakesPage initialized');
  }

  /**
   * Obtiene el último terremoto registrado
   */
  async getLatestEarthquake() {
    const loading = await this.loadingCtrl.create({
      message: 'Obteniendo datos de terremotos...',
      spinner: 'crescent'
    });
    await loading.present();

    this.earthquakeService.getLatestEarthquake().subscribe({
      next: (response) => {
        if (response.features && response.features.length > 0) {
          // El primer elemento es el más reciente
          this.latestEarthquake = response.features[0];
          console.log('Último terremoto:', this.latestEarthquake);
        } else {
          this.showAlert('Sin datos', 'No se encontraron terremotos recientes');
        }
        loading.dismiss();
      },
      error: (error) => {
        console.error('Error al obtener terremotos:', error);
        this.showAlert('Error', 'No se pudo conectar con la API de terremotos');
        loading.dismiss();
      }
    });
  }

  /**
   * Formatea la fecha del timestamp
   */
  formatDate(timestamp: number): string {
    return new Date(timestamp).toLocaleString('es-ES');
  }

  /**
   * Obtiene las coordenadas formateadas
   */
  getCoordinates(): string {
    if (!this.latestEarthquake) return '';
    const coords = this.latestEarthquake.geometry.coordinates;
    return `Lat: ${coords[1].toFixed(3)}°, Lon: ${coords[0].toFixed(3)}°`;
  }

  /**
   * Obtiene la profundidad del terremoto
   */
  getDepth(): string {
    if (!this.latestEarthquake) return '';
    return `${this.latestEarthquake.geometry.coordinates[2].toFixed(1)} km`;
  }

  /**
   * Muestra una alerta
   */
  async showAlert(header: string, message: string) {
    const alert = await this.alertCtrl.create({
      header,
      message,
      buttons: ['OK']
    });
    await alert.present();
  }
}
