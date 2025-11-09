import { Component, OnInit } from '@angular/core';
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { RouterLink } from '@angular/router';
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { AnunciosTableComponent } from "../../../components/anuncio/anuncios-table/anuncios-table.component";

@Component({
  selector: 'app-desactivar-anuncios-page',
  imports: [HeaderAdminSistemaComponent, RouterLink, AnunciosTableComponent],
  templateUrl: './desactivar-anuncios-page.html',
})
export class DesactivarAnunciosPage implements OnInit {
  anuncios: AnuncioResponse[] = [];

  constructor(private anunciosServices: AnuncioServices) { }

  ngOnInit(): void {
    this.obtenerAnuncios();
  }

  obtenerAnuncios(): void {
    this.anunciosServices.obtenerTodosLosAnuncios().subscribe({
      next: (anunciosServer: AnuncioResponse[]) => {
        this.anuncios = anunciosServer;
      },
      error: (error) => {
        console.log(error.error);
      }
    });
  }



}
