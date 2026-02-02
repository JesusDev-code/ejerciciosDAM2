import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { 
  IonContent, IonHeader, IonTitle, IonToolbar, IonButtons, IonBackButton,
  IonList, IonListHeader, IonItem, IonLabel, IonIcon, IonToggle, IonNote 
} from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { notificationsOutline, cloudDownloadOutline, helpCircleOutline, chevronForwardOutline } from 'ionicons/icons';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.page.html',
  styleUrls: ['./settings.page.scss'],
  standalone: true,
  imports: [
    CommonModule, FormsModule, IonContent, IonHeader, IonTitle, IonToolbar, 
    IonButtons, IonBackButton, IonList, IonListHeader, IonItem, IonLabel, 
    IonIcon, IonToggle, IonNote
  ]
})
export class SettingsPage implements OnInit {
  notificationsEnabled = true;

  constructor() {
    addIcons({ notificationsOutline, cloudDownloadOutline, helpCircleOutline, chevronForwardOutline });
  }

  ngOnInit() {
    // Cargar estado guardado (Requisito de estado/persistencia)
    const saved = localStorage.getItem('notifications');
    if (saved !== null) {
      this.notificationsEnabled = JSON.parse(saved);
    }
  }

  toggleNotifications(event: any) {
    this.notificationsEnabled = event.detail.checked;
    localStorage.setItem('notifications', JSON.stringify(this.notificationsEnabled));
  }
}