import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule, LoadingController, AlertController } from '@ionic/angular';
import { SpringbootService } from '../../services/springboot';
import { SpringBootItem, PageResponse } from '../../models/springboot-data.model';

@Component({
  selector: 'app-springboot-data',
  templateUrl: './springboot-data.page.html',
  styleUrls: ['./springboot-data.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule],
  providers: [SpringbootService, LoadingController, AlertController]
})
export class SpringbootDataPage implements OnInit {
  items: SpringBootItem[] = [];
  loading = false;
  totalElements = 0;

  constructor(
    private springbootService: SpringbootService,
    private loadingCtrl: LoadingController,
    private alertCtrl: AlertController
  ) { }

  ngOnInit() {
    // Opcional: Auto-cargar al entrar
    // this.loadData();
  }

  async loadData() {
    const loading = await this.loadingCtrl.create({
      message: 'Cargando diarios...',
      spinner: 'circles'
    });
    await loading.present();

    this.springbootService.getData().subscribe({
      next: (response: PageResponse<SpringBootItem>) => {
        console.log('✅ RESPUESTA API:', response);
        
        this.items = response.content || [];
        this.totalElements = response.totalElements || 0;
        
        console.log('📦 DIARIOS CARGADOS:', this.items);
        
        if (this.items.length === 0) {
          this.showAlert('Sin diarios', 'No tienes diarios creados aún');
        }
        
        loading.dismiss();
      },
      error: (error) => {
        console.error('❌ ERROR HTTP:', error);
        this.showAlert('Error de conexión', 
          `Error: ${error.message}\nStatus: ${error.status}`);
        loading.dismiss();
      }
    });
  }

  async refreshData(event: any) {
    this.loadData(); // Reutiliza la lógica
    event.target.complete();
  }

  async showAlert(header: string, message: string) {
    const alert = await this.alertCtrl.create({
      header,
      message,
      buttons: ['OK']
    });
    await alert.present();
  }
}