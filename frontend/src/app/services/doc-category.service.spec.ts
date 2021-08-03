import { TestBed } from '@angular/core/testing';

import { DocCategoryService } from './doc-category.service';

describe('DocCategoryService', () => {
  let service: DocCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocCategoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
