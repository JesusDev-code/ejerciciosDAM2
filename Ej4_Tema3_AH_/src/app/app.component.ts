import { Component } from '@angular/core';
import { IonApp, IonRouterOutlet, IonHeader, IonToolbar, IonTitle, IonContent } from '@ionic/angular/standalone';
import { GpsComponent } from './components/gps/gps.component';
import { SensoresComponent } from './components/sensores/sensores.component';
import { CamaraComponent } from './components/camara/camara.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  template: `
    <ion-app>
      <ion-header>
        <ion-toolbar color="primary">
          <ion-title>Ej4 Tema3 AH</ion-title>
        </ion-toolbar>
      </ion-header>

      <ion-content class="ion-padding">
        <h2 *ngIf="mostrarEureka" class="eureka">¡EUREKA!</h2>

        <app-gps></app-gps>
        <app-sensores (luzBaja)="onLuzBaja()"></app-sensores>
        <app-camara></app-camara>
      </ion-content>
    </ion-app>
  `,
  styles: [`.eureka { color: red; font-size: 2rem; text-align: center; }`],
  standalone: true,
  imports: [
    IonApp,
    IonRouterOutlet,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    CommonModule,
    GpsComponent,
    SensoresComponent,
    CamaraComponent,
  ],
})
export class AppComponent {
  mostrarEureka = false;

  onLuzBaja() {
    this.mostrarEureka = true;
    const audio = new Audio('assets/mimusica.mp3');
    audio.play();
  }
}