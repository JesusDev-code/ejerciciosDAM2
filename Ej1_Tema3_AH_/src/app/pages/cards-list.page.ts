import { Component, computed, signal } from '@angular/core';
import { IonicModule, AlertController } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { DataService, CardItem } from '../services/data.service';

@Component({
  selector: 'app-cards-list',
  standalone: true,
  imports: [CommonModule, IonicModule],
  templateUrl: './cards-list.page.html',
  styleUrls: ['./cards-list.page.scss']
})
export class CardsListPage {
  cat = signal<string>('servicios');
  items = signal<CardItem[]>([]);

  // Recupera la función title()
  title = computed(() => {
    const c = this.cat();
    return c.charAt(0).toUpperCase() + c.slice(1);
  });

  constructor(
    route: ActivatedRoute,
    private data: DataService,
    private alertCtrl: AlertController
  ) {
    route.paramMap.subscribe(pm => {
      const c = (pm.get('cat') || 'servicios').toLowerCase();
      this.cat.set(c);
      this.items.set(this.data.getByCategory(c));
    });
  }

  trackById = (_: number, it: CardItem) => it.id;

  async select(p: CardItem) {
    const alert = await this.alertCtrl.create({ header: p.name, message: p.desc, buttons: ['OK'] });
    await alert.present();
  }
}
