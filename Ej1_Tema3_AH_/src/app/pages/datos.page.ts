import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonList } from '@ionic/angular/standalone';
import { LocalStorageService } from '../core/local-storage.service';
import { UserStateService } from '../core/user-state.service';

@Component({
  standalone: true,
  selector: 'app-datos',
  imports: [CommonModule, IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonList],
  templateUrl: './datos.page.html'
})
export class DatosPage {
  private storage = inject(LocalStorageService);
  private userState = inject(UserStateService);

  nombre = this.storage.get('nombre');
  apellidos = this.storage.get('apellidos');

  extra$ = this.userState.extra$;
}
