import { Component } from '@angular/core';
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { HeaderUsuarioNormalComponent } from "../../../components/header/header-usuario-normal/header-usuario-normal.component";
import { UserProperties } from '../../../shared/user/user-properties';
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
import { ActivatedRoute, RouterLink } from '@angular/router';
import { UsuarioServices } from '../../../services/usuario/usuario.services';
import { OpinionResponse } from '../../../models/opinion/opinion-response';
import { OpinionesTableComponent } from "../../../components/venta-boletos/opiniones-table-component/opiniones-table.component";

@Component({
  selector: 'app-opiniones-page',
  imports: [HeaderAdminCineComponent, HeaderAdminSistemaComponent, HeaderUsuarioNormalComponent, AnuncioCardComponent, OpinionesTableComponent, RouterLink],
  templateUrl: './opiniones-page.html',
})
export class OpinionesPage {
  rol!: string | null;
  idUser!: string | null;
  saldoActual!: number;
  anuncios: AnuncioResponse[] = [];

  codigo!: string;
  nombre!: string;
  pelicula!: string;
  esPelicula: boolean = false;
  opiniones!: OpinionResponse[];
  codigoPelicula!:string;
  nombrePelicula!:string;

  constructor(private anunciosServices: AnuncioServices, private router: ActivatedRoute,
    private opinionesService: UsuarioServices
  ) { }

  ngOnInit(): void {
    this.idUser = localStorage.getItem(UserProperties.ID);
    this.rol = localStorage.getItem(UserProperties.ROL);
    this.obtenerAnuncios();
    this.codigo = this.router.snapshot.params['codigo'];
    this.nombre = this.router.snapshot.params['nombre'];
    this.pelicula = this.router.snapshot.params['es-pelicula'];
    this.codigoPelicula = this.router.snapshot.params['codigo-pelicula'];
    this.nombrePelicula = this.router.snapshot.params['nombre-pelicula'];
    if (this.pelicula == 'true') {
      this.esPelicula = true;
    }
    this.obtenerOpiniones();
  }

  obtenerAnuncios(): void {
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

  obtenerOpiniones(): void {
    this.opinionesService.obtenerOpiniones(this.codigo, this.esPelicula).subscribe({
      next: (opinionesServer: OpinionResponse[]) => {
        this.opiniones = opinionesServer;
      },
      error: (error) => {
        console.log(error.errror);
      }
    });
  }
}
