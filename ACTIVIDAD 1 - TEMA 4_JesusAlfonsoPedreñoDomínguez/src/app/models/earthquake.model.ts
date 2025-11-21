// Interfaz para la respuesta completa de la API USGS
export interface EarthquakeResponse {
  type: string;
  metadata: Metadata;
  features: Feature[];
}

export interface Metadata {
  generated: number;
  url: string;
  title: string;
  status: number;
  api: string;
  count: number;
}

export interface Feature {
  type: string;
  properties: Properties;
  geometry: Geometry;
  id: string;
}

export interface Properties {
  mag: number;          // Magnitud
  place: string;        // Lugar
  time: number;         // Timestamp
  updated: number;
  tz: number | null;
  url: string;
  detail: string;
  felt: number | null;
  cdi: number | null;
  mmi: number | null;
  alert: string | null;
  status: string;
  tsunami: number;
  sig: number;
  net: string;
  code: string;
  ids: string;
  sources: string;
  types: string;
  nst: number | null;
  dmin: number | null;
  rms: number;
  gap: number | null;
  magType: string;
  type: string;
  title: string;
}

export interface Geometry {
  type: string;
  coordinates: number[]; // [longitud, latitud, profundidad]
}
