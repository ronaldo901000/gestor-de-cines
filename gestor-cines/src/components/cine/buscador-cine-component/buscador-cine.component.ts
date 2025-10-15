import { Component, EventEmitter, Output} from "@angular/core";
import { CineServices } from "../../../services/cine/cine.services";
import { Cine } from "../../../models/cine/cine";
import { FormsModule } from '@angular/forms';
@Component({
    imports:[FormsModule],
  selector: 'app-buscador-cine-component',
  templateUrl: './buscador-cine.component.html',
})
export class BuscadorCineComponent {

 palabra: string = '';
  @Output() palabraBuscada = new EventEmitter<string>();

  enviarPalabra() {
    this.palabraBuscada.emit(this.palabra);
  }
}
