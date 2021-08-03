import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateDocumentFormComponent } from './create-document-form.component';

describe('CreateDocumentFormComponent', () => {
  let component: CreateDocumentFormComponent;
  let fixture: ComponentFixture<CreateDocumentFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateDocumentFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateDocumentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
