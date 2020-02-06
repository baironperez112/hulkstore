import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VenderProductoComponent } from './vender-producto.component';

describe('VenderProductoComponent', () => {
  let component: VenderProductoComponent;
  let fixture: ComponentFixture<VenderProductoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VenderProductoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VenderProductoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
