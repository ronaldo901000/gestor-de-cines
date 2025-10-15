import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-paginacion-component',
  templateUrl: './paginacion.component.html',
})
export class PaginacionComponent {
  @Input() inicio: number = 0;
  @Input() rango: number = 5;
  @Input() hayMas: boolean = true;

  @Output() cambiarPagina: EventEmitter<boolean> = new EventEmitter<boolean>();

  anterior() {
    if(this.inicio > 0) this.cambiarPagina.emit(false);
  }

  siguiente() {
    if(this.hayMas) this.cambiarPagina.emit(true);
  }
}
