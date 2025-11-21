import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EarthquakeResponse } from '../models/earthquake.model';

@Injectable({
  providedIn: 'root'
})
export class EarthquakeService {
  // API oficial de USGS (United States Geological Survey)
  private apiUrl = 'https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson';

  constructor(private http: HttpClient) { }

  /**
   * Obtiene los terremotos de la última hora
   */
  getRecentEarthquakes(): Observable<EarthquakeResponse> {
    return this.http.get<EarthquakeResponse>(this.apiUrl);
  }

  /**
   * Obtiene el último terremoto registrado
   */
  getLatestEarthquake(): Observable<EarthquakeResponse> {
    return this.http.get<EarthquakeResponse>(
      'https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson'
    );
  }
}
