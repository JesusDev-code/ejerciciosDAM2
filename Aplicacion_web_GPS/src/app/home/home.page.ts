import { Component, OnInit, OnDestroy } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Geolocation } from '@capacitor/geolocation';

@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  imports: [
    IonicModule,
    FormsModule,
    CommonModule
  ]
})
export class HomePage implements OnInit, OnDestroy {
  location: { latitude: number, longitude: number } | null = null;
  isRealtime = false;
  permissionDenied = false;
  watchId: string | null = null;

  ngOnInit() {}

  onRealtimeToggleChange() {
    if (this.isRealtime) {
      this.watchLocation();
    } else {
      this.stopWatch();
    }
  }

  async updateLocation() {
    try {
      const position = await Geolocation.getCurrentPosition();
      this.location = {
        latitude: position.coords.latitude,
        longitude: position.coords.longitude,
      };
      this.permissionDenied = false;
    } catch (e) {
      this.permissionDenied = true;
    }
  }

  async watchLocation() {
    this.watchId = await Geolocation.watchPosition({}, pos => {
      if (pos && pos.coords) {
        this.location = {
          latitude: pos.coords.latitude,
          longitude: pos.coords.longitude,
        };
        this.permissionDenied = false;
      } else {
        this.permissionDenied = true;
      }
    });
  }

  stopWatch() {
    if (this.watchId) {
      Geolocation.clearWatch({ id: this.watchId });
      this.watchId = null;
    }
    this.location = null;
  }

  ngOnDestroy() {
    this.stopWatch();
  }
}
