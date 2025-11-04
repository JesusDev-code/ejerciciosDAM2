import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonCard, IonCardHeader, IonCardTitle, IonCardContent, IonBadge } from '@ionic/angular/standalone';

@Component({
  standalone: true,
  selector: 'app-counter',
  imports: [CommonModule, IonCard, IonCardHeader, IonCardTitle, IonCardContent, IonBadge],
  templateUrl: './counter.component.html'
})
export class CounterComponent {
  @Input() count = 0;                          // valor que llega del padre
  @Output() milestone = new EventEmitter<number>(); // emite cuando llega a 10, 20, ...

  ngOnChanges(changes: SimpleChanges) {
    if ('count' in changes) {
      const v = changes['count'].currentValue as number;
      if (v > 0 && v % 10 === 0) {
        this.milestone.emit(v);
      }
    }
  }
}
