import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FavoritesPage } from './favorites.page';
import { MovieService } from 'src/app/core/services/movie.service';
import { of } from 'rxjs';

describe('FavoritesPage', () => {
  let component: FavoritesPage;
  let fixture: ComponentFixture<FavoritesPage>;

  // Mock del servicio para no depender del real
  const mockMovieService = {
    favorites$: of([]), // Simulamos lista vacía
    toggleFavorite: jasmine.createSpy('toggleFavorite')
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FavoritesPage], // Standalone component
      providers: [
        { provide: MovieService, useValue: mockMovieService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(FavoritesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});