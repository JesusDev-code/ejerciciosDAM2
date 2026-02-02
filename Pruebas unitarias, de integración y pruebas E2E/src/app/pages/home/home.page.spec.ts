import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomePage } from './home.page';
import { provideHttpClient } from '@angular/common/http';
import { MovieService } from 'src/app/core/services/movie.service';
import { of } from 'rxjs';
import { Router } from '@angular/router';

describe('HomePage', () => {
  let component: HomePage;
  let fixture: ComponentFixture<HomePage>;
  let routerSpy = jasmine.createSpyObj('Router', ['navigate']);
  let serviceSpy = jasmine.createSpyObj('MovieService', ['getMovies']);

  beforeEach(async () => {
    serviceSpy.getMovies.and.returnValue(of([{ id: 1, title: 'Test Movie', poster: 'test.jpg' }]));

    await TestBed.configureTestingModule({
      imports: [HomePage],
      providers: [
        provideHttpClient(),
        { provide: MovieService, useValue: serviceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(HomePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('11. Debe crearse', () => {
    expect(component).toBeTruthy();
  });

  it('12. Debe cargar películas al iniciar', () => {
    expect(component.featuredMovie?.title).toBe('Test Movie');
    expect(component.loading).toBeFalse();
  });

  it('13. Debe renderizar el título NEON', () => {
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('ion-title').textContent).toContain('NEON');
  });

  it('14. Debe llamar al router al pulsar Más Info', () => {
    component.featuredMovie = { id: 99 } as any;
    component.goToDetail(component.featuredMovie!);
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/detail', 99]);
  });

  it('15. No debe navegar si la película es nula', () => {
    routerSpy.navigate.calls.reset();
    component.goToDetail(null as any);
    expect(routerSpy.navigate).not.toHaveBeenCalled();
  });

  it('16. playMovie solo debe hacer log (sin errores)', () => {
    spyOn(console, 'log');
    component.playMovie();
    expect(console.log).toHaveBeenCalled();
  });
});