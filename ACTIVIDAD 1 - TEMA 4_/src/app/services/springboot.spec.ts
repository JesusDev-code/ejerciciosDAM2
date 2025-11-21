import { TestBed } from '@angular/core/testing';

import { Springboot } from './springboot';

describe('Springboot', () => {
  let service: Springboot;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Springboot);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
