import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule]
})
export class LoginPage {
  email = '';
  password = '';
  loading = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  async login() {
    this.loading = true;
    try {
      await this.authService.login(this.email, this.password);
      this.router.navigateByUrl('/home');
    } catch (error: any) {
      alert(`Error: ${error.message}`);
    } finally {
      this.loading = false;
    }
  }
}