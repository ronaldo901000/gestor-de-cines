import { Component, OnInit } from '@angular/core';
import { UserProperties } from '../../../../shared/user/user-properties';
import { AnuncioResponse } from '../../../../models/anuncio/anuncio-response';
import { AnuncioServices } from '../../../../services/anuncio/anuncio.services';
import { AdminCineProperties } from '../../../../shared/user/admin-cine-properties';
import { AnuncioCardComponent } from "../../../../components/anuncio/anuncio-card/anuncio-card.component";
import { HeaderAdminCineComponent } from "../../../../components/header/header-admin-cine/header-admin-cine.component";
import { RouterLink } from '@angular/router';
import { ComentariosDeSalasFormComponent } from "../../../../components/reportes-admin-cine-component/comentarios-de-salas-form-component/comentarios-de-salas-form.component";
@Component({
  selector: 'app-reporte-comentarios-salas-page',
  imports: [AnuncioCardComponent, HeaderAdminCineComponent, RouterLink, ComentariosDeSalasFormComponent],
  templateUrl: './reporte-comentarios-salas-page.html'
})
export class ReporteComentariosSalasPage implements OnInit{
  hayBloqueador!: string | null;
  anuncios: AnuncioResponse[] = [];
  codigoCine!:string | null;
  constructor(private anunciosServices: AnuncioServices) { }

  ngOnInit(): void {
    this.codigoCine =localStorage.getItem(AdminCineProperties.CODIGO_CINE);
    this.hayBloqueador = localStorage.getItem(AdminCineProperties.TIENE_BLOQUEADOR_ANUNCIOS);
    //si no hay bloqueador de anuncios se traen de la api
    if (this.hayBloqueador == 'false') {
      this.obtenerAnuncios();
    }
  }


  obtenerAnuncios() {
    const indice = Number(localStorage.getItem(UserProperties.INDICE_ANUNCIO));
    this.anunciosServices.obtenerAnunciosParaMostrar(indice).subscribe({
      next: (anunciosServer: AnuncioResponse[]) => {
        this.anuncios = anunciosServer;
        if (this.anuncios.length < 2) {
          localStorage.setItem(UserProperties.INDICE_ANUNCIO, '0');
        }
        else {
          const nuevoIndice = indice + 2;
          localStorage.setItem(UserProperties.INDICE_ANUNCIO, nuevoIndice.toString());
        }
      },
      error: (error) => {
        console.log(error.error);
      }
    });
  }

}
