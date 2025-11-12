import { Component } from '@angular/core';
import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';
import { IonButton, IonImg, IonCard, IonCardContent } from '@ionic/angular/standalone';
import { CommonModule } from '@angular/common';
import { IonIcon } from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { locationOutline, cameraOutline } from 'ionicons/icons';

@Component({
  selector: 'app-camara',
  templateUrl: './camara.component.html',
  standalone: true,
  imports: [IonButton, IonImg, IonCard, IonCardContent, CommonModule, IonIcon],
})
export class CamaraComponent {
  foto: string | undefined;

   constructor() {
    addIcons({ locationOutline, cameraOutline });
  }

  async tomarFoto() {
    try {
      const image = await Camera.getPhoto({
        quality: 90,
        allowEditing: false,
        resultType: CameraResultType.DataUrl,
        source: CameraSource.Camera,
      });
      this.foto = image.dataUrl;
    } catch (err) {
      alert('Permiso de cámara denegado o error tomando la foto');
    }
  }
}