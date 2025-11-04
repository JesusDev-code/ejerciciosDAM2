import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class LocalStorageService {
  get(key: string): string | null { try { return localStorage.getItem(key); } catch { return null; } }
  set(key: string, value: string)   { try { localStorage.setItem(key, value); } catch {} }
  remove(key: string)               { try { localStorage.removeItem(key); } catch {} }
}
