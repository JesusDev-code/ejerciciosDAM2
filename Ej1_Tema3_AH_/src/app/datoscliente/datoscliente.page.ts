import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonContent, IonHeader, IonTitle, IonToolbar, IonItem, IonLabel, IonList } from '@ionic/angular/standalone';
import { DataService, Cliente } from '../services/data.service';

@Component({
  selector: 'app-datoscliente',
  templateUrl: './datoscliente.page.html',
  styleUrls: ['./datoscliente.page.scss'],
  standalone: true,
  imports: [IonContent, IonHeader, IonTitle, IonToolbar, IonItem, IonLabel, IonList, CommonModule]
})
export class DatosclientePage implements OnInit {
  clientes: Cliente[] = [];

  constructor(private dataService: DataService) {}

  ngOnInit() {
    this.clientes = this.dataService.getClientes();
  }
}