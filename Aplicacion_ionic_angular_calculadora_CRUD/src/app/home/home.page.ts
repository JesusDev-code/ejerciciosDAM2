import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterOutlet } from '@angular/router';
import {
  IonHeader, IonToolbar, IonTitle, IonButtons, IonButton,
  IonContent, IonItem, IonLabel, IonInput, IonRouterOutlet, IonFooter,
  IonSelect, IonSelectOption
} from '@ionic/angular/standalone';
import { CounterComponent } from '../components/counter/counter.component';
import { CalculadoraComponent } from '../calculadora/calculadora.component';

@Component({
  standalone: true,
  selector: 'app-home',
  imports: [
    CommonModule, FormsModule, RouterLink, RouterOutlet,
    IonHeader, IonToolbar, IonTitle, IonButtons, IonButton,
    IonContent, IonItem, IonLabel, IonInput, IonRouterOutlet,
    CounterComponent, CalculadoraComponent, IonFooter,
    IonSelect, IonSelectOption
  ],
  templateUrl: './home.page.html'
})
export class HomePage {
  parentCount = 0;
  milestoneMsg = '';

  valor1: number = 0;
  valor2: number = 0;
  operacion: string = '+';
  resultado: string = '';
  trigger: boolean = false;

  incrementFromParent() {
    this.parentCount++;
  }

  onMilestoneReached(n: number) {
    this.milestoneMsg = `Ha llegado al ${n}`;
    setTimeout(() => this.milestoneMsg = '', 3000);
  }

  calcular() {
    this.trigger = !this.trigger;
  }

  manejarResultado(res: string) {
    this.resultado = res;
  }
}