import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface User {
  username: string;
  email: string;
  password?: string; // En una app real, esto nunca se guarda en texto plano
  avatar?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userSubject = new BehaviorSubject<User | null>(null);
  user$ = this.userSubject.asObservable();

  constructor() {
    this.loadUser();
  }

  // Cargar usuario del localStorage al iniciar
  private loadUser() {
    const storedUser = localStorage.getItem('cineclub_user');
    if (storedUser) {
      this.userSubject.next(JSON.parse(storedUser));
    }
  }

  // Registro / Login simple
  login(user: User) {
    // Simulamos que guardamos el usuario
    localStorage.setItem('cineclub_user', JSON.stringify(user));
    this.userSubject.next(user);
  }

  logout() {
    localStorage.removeItem('cineclub_user');
    this.userSubject.next(null);
  }

  isAuthenticated(): boolean {
    return !!this.userSubject.value;
  }
}