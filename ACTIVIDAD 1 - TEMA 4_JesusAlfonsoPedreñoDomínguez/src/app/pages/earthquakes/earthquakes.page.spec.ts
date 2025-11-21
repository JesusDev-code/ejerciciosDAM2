import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EarthquakesPage } from './earthquakes.page';

describe('EarthquakesPage', () => {
  let component: EarthquakesPage;
  let fixture: ComponentFixture<EarthquakesPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(EarthquakesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
