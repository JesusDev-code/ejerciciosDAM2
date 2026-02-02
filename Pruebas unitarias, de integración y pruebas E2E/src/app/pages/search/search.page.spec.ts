import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SearchPage } from './search.page';
import { MovieService } from 'src/app/core/services/movie.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';

describe('SearchPage', () => {
  let component: SearchPage;
  let fixture: ComponentFixture<SearchPage>;

  const mockMovieService = {
    searchMovies: () => of([])
  };

  const mockRouter = {
    navigate: jasmine.createSpy('navigate')
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchPage],
      providers: [
        { provide: MovieService, useValue: mockMovieService },
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(SearchPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});