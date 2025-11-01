import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { CineCardComponent } from '../../../components/cine/cine-card-component/cine-card.component';
import { Cine } from '../../../models/cine/cine';
import { CineServices } from '../../../services/cine/cine.services';
import { BuscadorComponent } from "../../../components/cine/buscador-cine-component/buscador-cine.component";
import { ToastComponent } from '../../../components/toast/toast.component';
import { UserProperties } from '../../../shared/user/user-properties';
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { HeaderUsuarioNormalComponent } from "../../../components/header/header-usuario-normal/header-usuario-normal.component";
@Component({
  selector: 'app-cines-disponibles-page',
  imports: [HeaderAdminSistemaComponent, CineCardComponent, CineCardComponent, BuscadorComponent, ToastComponent, HeaderAdminCineComponent, HeaderUsuarioNormalComponent],
  templateUrl: './cines-page.component.html',
  styleUrl: './cines-page.component.css'
})
export class CinesDisponiblesPage implements OnInit {
  @ViewChild('toast') toast!: ToastComponent;
  protected cines!: Cine[];
  protected inicio: number = 0;
  protected rango: number = 5;
  protected rol!: string | null;

  constructor(private cinesServices: CineServices) {
  }

  ngOnInit(): void {
    this.rol=localStorage.getItem(UserProperties.ROL);
    this.cargarCines();
  }

  cargarCines() {
    this.cinesServices.traerCinesPorPagina(this.inicio).subscribe({
      next: (cinesServer: Cine[]) => {
        this.cines = cinesServer;
      },
      error: (error) => {
        this.toast.titulo = 'Error';
        this.toast.tipo = 'danger';
        console.error(error);
        this.toast.mensaje = error.error;
        this.toast.mostrar();
      }

    });
  }

  buscarCines(palabra: string) {
    if (!palabra || palabra.trim().length == 0) {
      this.toast.titulo = 'Error';
      this.toast.tipo = 'warning';
      this.toast.mensaje = 'Debe ingresar al menos un caracter';
      this.toast.mostrar();
      return;
    }
    this.cinesServices.traerCinesPorPalabraClave(palabra)
      .subscribe({
        next: cines => {
          this.cines = cines
          if (cines.length == 0) {
            this.toast.titulo = 'Sin Resultados';
            this.toast.tipo = 'info';
            this.toast.mensaje = 'Sin resultados de tu busqueda "' + palabra + '"';
            this.toast.mostrar();
          }
        }
        ,
        error: error => {
          this.toast.titulo = 'Error';
          this.toast.tipo = 'danger';
          console.error(error);
          this.toast.mensaje = error.error;
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