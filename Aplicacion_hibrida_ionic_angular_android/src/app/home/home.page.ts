import { Component } from '@angular/core';
import { IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/angular/standalone';
// Añadir imports de los componentes hijos
import { GpsComponent } from '../components/gps/gps.component';
import { SensoresComponent } from '../components/sensores/sensores.component';
import { CamaraComponent } from '../components/camara/camara.component';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: true,
  imports: [IonHeader, IonToolbar, IonTitle, IonContent, GpsComponent, CamaraComponent],
})
export class HomePage {
  constructor() {}
}
