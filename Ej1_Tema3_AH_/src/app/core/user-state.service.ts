import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface PerfilExtra {
  domicilio: string;
  edad: number | null;
  curso: string;
  email: string;
  password: string;
}

@Injectable({ providedIn: 'root' })
export class UserStateService {
  private extraSubject = new BehaviorSubject<PerfilExtra>({
    domicilio: '', edad: null, curso: '', email: '', password: ''
  });
  extra$ = this.extraSubject.asObservable();

  updateExtra(patch: Partial<PerfilExtra>) {
    const prev = this.extraSubject.value;
    this.extraSubject.next({ ...prev, ...patch });
  }
}
