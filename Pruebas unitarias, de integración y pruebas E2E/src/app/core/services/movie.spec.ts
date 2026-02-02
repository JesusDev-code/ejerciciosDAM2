import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { MovieService, Movie } from './movie.service';

describe('MovieService', () => {
  let service: MovieService;
  let httpMock: HttpTestingController;

  const mockMovie: Movie = { id: 1, title: 'Test', poster: 'img.jpg', overview: 'desc', release_date: '2024', vote_average: 10 };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MovieService, provideHttpClient(), provideHttpClientTesting()]
    });
    service = TestBed.inject(MovieService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => { httpMock.verify(); });

  it('1. Debe crearse el servicio', () => {
    expect(service).toBeTruthy();
  });

  it('2. Debe iniciar sin favoritos', (done) => {
    service.favorites$.subscribe(favs => {
      expect(favs.length).toBe(0);
      done();
    });
  });

  it('3. toggleFavorite debe AÑADIR una película', () => {
    service.toggleFavorite(mockMovie);
    expect(service.isFavorite(1)).toBeTrue();
  });

  it('4. toggleFavorite debe QUITAR si ya existe', () => {
    service.toggleFavorite(mockMovie); // Añadir
    service.toggleFavorite(mockMovie); // Quitar
    expect(service.isFavorite(1)).toBeFalse();
  });

  it('5. getMovies debe hacer petición GET a la API', () => {
    service.getMovies().subscribe(movies => {
      expect(movies.length).toBe(1);
      expect(movies[0].title).toBe('Dune');
    });
    const req = httpMock.expectOne(r => r.url.includes('/movie/popular'));
    expect(req.request.method).toBe('GET');
    req.flush({ results: [{ id: 1, title: 'Dune', poster_path: '/dune.jpg' }] });
  });

  it('6. getMovies debe manejar errores devolviendo array vacío', () => {
    service.getMovies().subscribe(movies => expect(movies.length).toBe(0));
    const req = httpMock.expectOne(r => r.url.includes('/movie/popular'));
    req.flush('Error', { status: 500, statusText: 'Error' });
  });

  it('7. searchMovies debe buscar por texto', () => {
    service.searchMovies('Batman').subscribe(m => expect(m.length).toBe(1));
    const req = httpMock.expectOne(r => r.url.includes('/search/movie') && r.url.includes('Batman'));
    req.flush({ results: [{ id: 2, title: 'Batman' }] });
  });

  it('8. searchMovies debe manejar errores', () => {
    service.searchMovies('Error').subscribe(m => expect(m.length).toBe(0));
    const req = httpMock.expectOne(r => r.url.includes('/search/movie'));
    req.flush('Error', { status: 500, statusText: 'Error' });
  });

  it('9. getMovieDetail debe traer detalles', () => {
    service.getMovieDetail('100').subscribe(m => expect(m?.title).toBe('Detalle'));
    const req = httpMock.expectOne(r => r.url.includes('/movie/100'));
    req.flush({ id: 100, title: 'Detalle' });
  });

  it('10. getMovieDetail debe manejar errores devolviendo null', () => {
    service.getMovieDetail('999').subscribe(m => expect(m).toBeNull());
    const req = httpMock.expectOne(r => r.url.includes('/movie/999'));
    req.flush('Error', { status: 404, statusText: 'Not Found' });
  });
});