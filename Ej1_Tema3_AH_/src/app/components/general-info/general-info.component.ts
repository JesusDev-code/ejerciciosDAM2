import { Component, OnDestroy, OnInit, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';

@Component({
  selector: 'app-general-info',
  standalone: true,
  imports: [CommonModule, IonicModule],
  templateUrl: './general-info.component.html',
  styleUrls: ['./general-info.component.scss']
})
export class GeneralInfoComponent implements OnInit, OnDestroy {
  // estado
  private timer: any;
  now = signal(new Date());

  // formateo en español con Intl (sin tocar la locale global)
  formatted = computed(() =>
    new Intl.DateTimeFormat('es-ES', {
      weekday: 'long', day: 'numeric', month: 'long', year: 'numeric',
      hour: '2-digit', minute: '2-digit', second: '2-digit'
    }).format(this.now())
  );

  ngOnInit() {
    this.timer = setInterval(() => this.now.set(new Date()), 1000);
  }
  ngOnDestroy() {
    clearInterval(this.timer);
  }
}

