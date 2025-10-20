import { Component, EventEmitter, Output } from "@angular/core";
import { FormsModule } from '@angular/forms';
@Component({
  imports: [FormsModule],
  selector: 'app-buscador-component',
  templateUrl: './buscador-cine.component.html',
})
export class BuscadorComponent {

  palabra: string = '';
  @Output()
  palabraBuscada = new EventEmitter<string>();

  enviarPalabra() {
    this.palabra = this.palabra.trim();
    if (this.palabra !== '') {
      this.palabraBuscada.emit(this.palabra);
    }
  }

}
