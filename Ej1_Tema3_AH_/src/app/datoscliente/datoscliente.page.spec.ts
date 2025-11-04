import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DatosclientePage } from './datoscliente.page';

describe('DatosclientePage', () => {
  let component: DatosclientePage;
  let fixture: ComponentFixture<DatosclientePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(DatosclientePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
