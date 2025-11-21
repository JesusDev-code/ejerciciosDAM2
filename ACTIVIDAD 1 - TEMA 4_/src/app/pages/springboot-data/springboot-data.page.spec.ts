import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SpringbootDataPage } from './springboot-data.page';

describe('SpringbootDataPage', () => {
  let component: SpringbootDataPage;
  let fixture: ComponentFixture<SpringbootDataPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(SpringbootDataPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
