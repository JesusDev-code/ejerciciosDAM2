import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, RouterOutlet],
})
export class HomePage {
  
  navigationOptions = [
    {
      title: 'Terremotos',
      subtitle: 'API Pública USGS',
      icon: 'globe-outline',
      color: 'primary',
      route: 'earthquakes',
      image: 'https://images.unsplash.com/photo-1614730321146-b6fa6a46bcb4?w=400'
    },
    {
      title: 'Spring Boot API',
      subtitle: 'Tu servidor en Render',
      icon: 'server-outline',
      color: 'secondary',
      route: 'springboot-data',
      image: 'https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=400'
    }
  ];

  constructor(private router: Router) {}

  navigateTo(route: string) {
  console.log('Navegando a:', '/' + route);
  this.router.navigateByUrl('/' + route);
}
}