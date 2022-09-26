import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserLikedListComponent } from './user-liked-list.component';

describe('UserLikedListComponent', () => {
  let component: UserLikedListComponent;
  let fixture: ComponentFixture<UserLikedListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserLikedListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserLikedListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
