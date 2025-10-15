import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { CineCardComponent } from '../../../components/cine/cine-card-component/cine-card.component';
import { Cine } from '../../../models/cine/cine';
import { CineServices } from '../../../services/cine/cine.services';
import { BuscadorCineComponent } from "../../../components/cine/buscador-cine-component/buscador-cine.component";
import { ToastComponent } from '../../../components/toast/toast.component';
@Component({
  selector: 'app-cines-page',
  imports: [HeaderAdminSistemaComponent, RouterLink, CineCardComponent, CineCardComponent, BuscadorCineComponent, ToastComponent],
  templateUrl: './cines-page.component.html',
  styleUrl: './cines-page.component.css'
})
export class CinesPage implements OnInit {
  @ViewChild('toast') toast!: ToastComponent;
  protected cines!: Cine[];
  protected inicio: number = 0;
  protected rango: number = 5;
  constructor(private cinesServices: CineServices) {
  }

  ngOnInit(): void {
    this.cargarCines();
  }

  cargarCines() {
    this.cinesServices.traerCinesPorPagina(this.inicio).subscribe({
      next: (cinesServer: Cine[]) => {
        this.cines!;
        this.cines = cinesServer;
      },
      error: (err) =>
        console.error('Error al cargar cines:', err)
    });
  }

  buscarCines(palabra: string) {
    if (!palabra) {
      this.cargarCines();
      return;
    }
    this.cinesServices.traerCinesPorPalabraClave(palabra)
      .subscribe({
        next: cines => {
          this.cines = cines
          if (cines.length == 0) {
            this.toast.titulo = 'Sin Resultados';
            this.toast.tipo = 'info';
            this.toast.mensaje='Sin resultados de tu busqueda "'+palabra+'"';
            this.toast.mostrar();
          }
        }
        ,
        error: error => {
          this.toast.titulo = 'Error';
          this.toast.tipo = 'danger';
          console.error(error);
          if (error.status === 400) {
            this.toast.mensaje = 'Error en los datos enviados. Por favor, revisa';
          } else if (error.status === 500) {
            this.toast.mensaje = 'Error interno del servidor.';
          } else {
            this.toast.mensaje = 'Ocurrio un error desconocido';
          }

          this.toast.mostrar();

        }
      });
  }
  avanzarUnaPagina() {
    this.inicio += this.rango;
    this.cargarCines();
  }
  retrocederUnaPagina() {
    this.inicio -= this.rango;
    this.cargarCines();
  }

  hayMasCines(): boolean {
    if (!this.cines || this.cines.length === 0) {
      return false;
    }
    return this.cines.length === this.rango;
  }

}