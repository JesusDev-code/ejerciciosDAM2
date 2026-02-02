import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DetailPage } from './detail.page';
import { MovieService } from 'src/app/core/services/movie.service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

describe('DetailPage', () => {
  let component: DetailPage;
  let fixture: ComponentFixture<DetailPage>;

  const mockMovieService = {
    getMovieDetail: () => of({ id: 1, title: 'Test Movie' }),
    isFavorite: () => false
  };

  const mockActivatedRoute = {
    snapshot: {
      paramMap: {
        get: () => '1' // Simulamos que estamos visitando la peli ID 1
      }
    }
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailPage],
      providers: [
        { provide: MovieService, useValue: mockMovieService },
        { provide: ActivatedRoute, useValue: mockActivatedRoute }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DetailPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});