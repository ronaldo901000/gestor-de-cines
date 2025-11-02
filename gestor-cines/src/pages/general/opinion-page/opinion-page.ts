import { Component, Input } from '@angular/core';
import { UserProperties } from '../../../shared/user/user-properties';
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { HeaderUsuarioNormalComponent } from "../../../components/header/header-usuario-normal/header-usuario-normal.component";
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
import { OpinionFormComponent } from "../../../components/venta-boletos/opinion-form-component/opinion-form.component";

@Component({
  selector: 'app-opinion-page',
  imports: [HeaderAdminCineComponent, HeaderAdminSistemaComponent, HeaderUsuarioNormalComponent, AnuncioCardComponent, RouterLink, OpinionFormComponent],
  templateUrl: './opinion-page.html'
})
export class OpinionPage {
  rol!: string | null;
  idUser!: string | null;

  titulo!: string;

  tipo!: string;

  codigo!:string;

  indiceAnuncio!: number;
  anuncios: AnuncioResponse[] = [];
  constructor(private router: ActivatedRoute, private anunciosServices: AnuncioServices) { }

  ngOnInit(): void {
    this.idUser = localStorage.getItem(UserProperties.ID);
    this.rol = localStorage.getItem(UserProperties.ROL);
    this.titulo = this.router.snapshot.params['titulo'];
    this.tipo = this.router.snapshot.params['tipo'];
    this.indiceAnuncio = this.router.snapshot.params['indice-anuncios'];
    this.codigo=this.router.snapshot.params['codigo'];
    this.obtenerAnuncios();
  }

  obtenerAnuncios() {
    const indice = Number(localStorage.getItem(UserProperties.INDICE_ANUNCIO)) || 0;
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
