/**
 * Created by user on 5/4/2017.
 */

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConferenceNewComponent } from './conference-new.component';

describe('ConferenceNewComponent', () => {
  let component: ConferenceNewComponent;
  let fixture: ComponentFixture<ConferenceNewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConferenceNewComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConferenceNewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
