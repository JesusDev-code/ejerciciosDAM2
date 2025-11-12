import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Motion } from '@capacitor/motion';
import { CapacitorFlash } from '@capgo/capacitor-flash';
import { IonCard, IonCardContent } from '@ionic/angular/standalone';

@Component({
  selector: 'app-sensores',
  template: `
    <ion-card>
      <ion-card-content>
        <strong>Sensores</strong><br />
        Acelerómetro: X={{ acc.x.toFixed(2) }} Y={{ acc.y.toFixed(2) }} Z={{ acc.z.toFixed(2) }}<br />
        Humedad: {{ hum }} %<br />
        Luz: {{ lux }} lux
      </ion-card-content>
    </ion-card>
  `,
  standalone: true,
  imports: [IonCard, IonCardContent],
})
export class SensoresComponent implements OnInit {
  acc = { x: 0, y: 0, z: 0 };
  hum = 0;
  lux = 1000;
  @Output() luzBaja = new EventEmitter<void>();

  async ngOnInit() {
    Motion.addListener('accel', (event) => {
      this.acc = event.accelerationIncludingGravity ?? { x: 0, y: 0, z: 0 };
    });

    setInterval(() => (this.hum = 40 + Math.random() * 20), 2000);

    setInterval(() => {
      this.lux = 50 + Math.random() * 100;
      if (this.lux < 60) {
        this.luzBaja.emit();
        CapacitorFlash.switchOn({}); // ← Así sí funciona (objeto vacío)
      }
    }, 1500);
  }
}
