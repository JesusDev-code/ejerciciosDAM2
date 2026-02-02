import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router'; // <--- 1. IMPORTAR ESTO
import { IonContent, IonHeader, IonToolbar, IonButton, IonIcon, IonInput, IonItem, IonLabel } from '@ionic/angular/standalone';
import { AuthService, User } from 'src/app/core/services/auth.service';
import { addIcons } from 'ionicons';
import { person, lockClosed, mail, logOut, settings, heart, statsChart, download } from 'ionicons/icons';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    RouterLink, // <--- 2. AÑADIRLO AQUÍ (¡Es la clave!)
    IonContent, IonHeader, IonToolbar, IonButton, IonIcon, IonInput, IonItem, IonLabel
  ]
})
export class ProfilePage implements OnInit {
  private authService = inject(AuthService);
  
  user: User | null = null;
  
  // Datos para el formulario de login/registro
  loginData = {
    username: '',
    email: '',
    password: ''
  };

  constructor() {
    addIcons({ person, lockClosed, mail, logOut, settings, heart, statsChart, download });
  }

  ngOnInit() {
    this.authService.user$.subscribe(u => {
      this.user = u;
    });
  }

  onLogin() {
    if (this.loginData.username && this.loginData.email) {
      // Guardamos el usuario con un avatar por defecto estilo neon
      const newUser: User = {
        ...this.loginData,
        avatar: 'https://i.pravatar.cc/150?u=' + this.loginData.username
      };
      this.authService.login(newUser);
    }
  }

  onLogout() {
    this.authService.logout();
  }
}