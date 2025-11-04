import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonHeader, IonToolbar, IonTitle, IonContent, IonItem, IonLabel, IonInput, IonButton, IonNote
} from '@ionic/angular/standalone';
import { DataService } from '../services/data.service';

export interface Cliente {
  nombre: string;
  apellido: string;
  email: string;
  nacionalidad: string;
}

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.page.html',
  styleUrls: ['./cliente.page.scss'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    IonHeader, IonToolbar, IonTitle,
    IonContent, IonItem, IonLabel, IonInput, IonButton, IonNote
  ]
})
export class ClientePage {
  cliente: Cliente = {
    nombre: '',
    apellido: '',
    email: '',
    nacionalidad: ''
  };

  emailValido: boolean = true;

  constructor(private dataService: DataService, private router: Router) {}

  validarEmail() {
    this.emailValido = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.cliente.email);
  }

  guardarCliente() {
    this.validarEmail();
    if (!this.emailValido) {
      alert('El email no es válido');
      return;
    }
    this.dataService.saveCliente(this.cliente);
    this.router.navigate(['/datoscliente']);


    this.cliente = {
      nombre: '',
      apellido: '',
      email: '',
      nacionalidad: ''
    };
    this.emailValido = true;
  }
}