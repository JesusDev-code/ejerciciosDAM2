import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonButton } from '@ionic/angular/standalone';

@Component({
  standalone: true,
  selector: 'app-calculadora',
  imports: [CommonModule, IonButton],
  templateUrl: './calculadora.component.html'
})
export class CalculadoraComponent implements OnChanges {
  @Input() valor1: number = 0;
  @Input() valor2: number = 0;
  @Input() operacion: string = '+';
  @Input() trigger: boolean = false;
  @Output() resultado = new EventEmitter<string>();

  ngOnChanges(changes: SimpleChanges) {
    if (changes['trigger']) {
      this.operar();
    }
  }

  operar() {
    let res: number;
    switch (this.operacion) {
      case '+': res = this.valor1 + this.valor2; break;
      case '-': res = this.valor1 - this.valor2; break;
      case '*': res = this.valor1 * this.valor2; break;
      case '/': 
        res = this.valor2 !== 0 ? this.valor1 / this.valor2 : NaN; 
        break;
      default: res = NaN;
    }
    this.resultado.emit(`${this.valor1} ${this.operacion} ${this.valor2} = ${isNaN(res) ? 'Error' : res}`);
  }
}