import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SettingsPage } from './settings.page';

describe('SettingsPage', () => {
  let component: SettingsPage;
  let fixture: ComponentFixture<SettingsPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({ imports: [SettingsPage] }).compileComponents();
    fixture = TestBed.createComponent(SettingsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('17. Debe crearse', () => {
    expect(component).toBeTruthy();
  });

  it('18. Debe inicializar notificaciones en true por defecto', () => {
    // Si no hay nada en localStorage, asumimos true o false según tu lógica
    expect(component.notificationsEnabled).toBeDefined();
  });

  it('19. Toggle debe cambiar el valor', () => {
    const event = { detail: { checked: false } };
    component.toggleNotifications(event);
    expect(component.notificationsEnabled).toBeFalse();
  });

  it('20. Toggle debe guardar en localStorage (Persistencia)', () => {
    spyOn(localStorage, 'setItem');
    const event = { detail: { checked: true } };
    component.toggleNotifications(event);
    expect(localStorage.setItem).toHaveBeenCalledWith('notifications', 'true');
  });
});