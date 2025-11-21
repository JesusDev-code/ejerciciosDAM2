import { Injectable } from '@angular/core';
import { initializeApp } from 'firebase/app';
import { 
  getAuth, 
  signInWithEmailAndPassword, 
  onIdTokenChanged, 
  signOut 
} from 'firebase/auth';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private auth;
  private currentToken: string | null = null;

  constructor() {
    const app = initializeApp(environment.firebase);
    this.auth = getAuth(app);
    
    // 🔄 Refresco automático de token cada hora
    onIdTokenChanged(this.auth, async (user) => {
      if (user) {
        this.currentToken = await user.getIdToken();
        localStorage.setItem('firebase_token', this.currentToken);
        console.log('✅ Token renovado automáticamente');
      } else {
        this.currentToken = null;
        localStorage.removeItem('firebase_token');
      }
    });
  }

  /**
   * Login con usuario de prueba
   */
  async login(email: string, password: string): Promise<string> {
    const userCredential = await signInWithEmailAndPassword(this.auth, email, password);
    this.currentToken = await userCredential.user.getIdToken();
    localStorage.setItem('firebase_token', this.currentToken);
    return this.currentToken;
  }

  /**
   * Obtener token actual (se refresca solo)
   */
  async getToken(): Promise<string | null> {
    if (this.auth.currentUser) {
      return await this.auth.currentUser.getIdToken();
    }
    return localStorage.getItem('firebase_token');
  }

  /**
   * Logout
   */
  logout(): void {
    signOut(this.auth);
    this.currentToken = null;
    localStorage.removeItem('firebase_token');
  }
}