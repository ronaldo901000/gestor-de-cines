import { Component, OnInit } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { CineCardComponent } from '../../../components/cine/cine-card-component/cine-card.component';
import { Cine } from '../../../models/cine/cine';
import { CineServices } from '../../../services/cine/cine.services';
import { BuscadorCineComponent } from "../../../components/cine/buscador-cine-component/buscador-cine.component";
@Component({
  selector: 'app-cines-page',
  imports: [HeaderAdminSistemaComponent, RouterLink, CineCardComponent, CineCardComponent, BuscadorCineComponent],
  templateUrl: './cines-page.component.html',
  styleUrl: './cines-page.component.css'
})
export class CinesPage implements OnInit {

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
        console.log('Cines cargados:', this.cines);
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
    console.log('Palabra: '+ palabra);
    this.cinesServices.traerCinesPorPalabraClave(palabra)
      .subscribe({
        next: cines => this.cines = cines,
        error: err => console.error('Error al buscar cines:', err)
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