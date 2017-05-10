/**
 * Created by Adriana on 5/4/2017.
 */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaperNewComponent } from './paper-new.component';


describe('PaperNewComponent', () => {
  let component: PaperNewComponent;
  let fixture: ComponentFixture<PaperNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaperNewComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaperNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
