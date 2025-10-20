import { Component, EventEmitter, Input, Output, ViewChild } from "@angular/core";
import { Cine } from "../../../models/cine/cine";
import { DatePipe } from "@angular/common";
import { RouterLink } from "@angular/router";
import { CineServices } from "../../../services/cine/cine.services";
import { ToastComponent } from "../../toast/toast.component";
import { Status } from "../../../shared/status/status";
@Component({
  selector: 'app-cine-card',
  imports: [DatePipe, RouterLink, ToastComponent],
  templateUrl: './cine-card.component.html',
  styleUrl: './cine-card.component.css'
})
export class CineCardComponent {


  @Input({ required: true })
  cineSeleccionado!: Cine;

  @ViewChild('toast') toast!: ToastComponent;

  @Output()
  cineEliminado = new EventEmitter<void>();

  constructor(private cineService: CineServices) {

  }
  confirmarEliminacion(): void {
    const confirmado = confirm('¿Estas seguro de que deseas eliminar este cine?');
    if (confirmado) {
      this.eliminarCine();
    }
  }

  eliminarCine(): void {
    this.cineService.eliminarCine(this.cineSeleccionado.codigo).subscribe({
      next: () => {
        this.toast.titulo = 'Eliminacion exitosa';
        this.toast.tipo = 'info';
        this.toast.mensaje = 'Eliminacion realizada con exito'
        this.toast.mostrar();
        this.cineEliminado.emit();
      },
      error: (error) => {
        this.toast.titulo = 'Error al Eliminar';
        this.toast.tipo = 'danger';
        this.toast.mensaje = error.error;
        this.toast.mostrar();
      }
    });
  }


}