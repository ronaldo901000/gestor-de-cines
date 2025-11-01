import { Component, OnInit } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HeaderAdminCineComponent } from '../../../components/header/header-admin-cine/header-admin-cine.component';
import { UserProperties } from '../../../shared/user/user-properties';
import { HeaderUsuarioNormalComponent } from "../../../components/header/header-usuario-normal/header-usuario-normal.component";
import { ProyeccionesTableComponent } from "../../../components/proyeccion/proyecciones-table/proyecciones-table.component";
import { ProyeccionesPeliculaTableComponent } from "../../../components/venta-boletos/proyecciones-table-component/proyecciones-table.component";
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
@Component({
    selector: 'app-proyecciones-page',
    imports: [HeaderAdminCineComponent, HeaderAdminSistemaComponent, HeaderUsuarioNormalComponent, ProyeccionesPeliculaTableComponent, RouterLink, AnuncioCardComponent],
    templateUrl: './proyecciones-page.html',
})
export class ProyeccionesPeliculaPageComponent implements OnInit {
    rol!: string | null;
    idUser!: string | null;

    codigoPelicula!: string;
    nombrePelicula!: string
    indiceAnuncio!: number;
    anuncios:AnuncioResponse[]=[];
    constructor(private router: ActivatedRoute, private anunciosServices:AnuncioServices) { }

    ngOnInit(): void {
        this.idUser = localStorage.getItem(UserProperties.ID);
        this.rol = localStorage.getItem(UserProperties.ROL);
        //se obtiene el codigo de pelicula pasada por la url
        this.codigoPelicula = this.router.snapshot.params['codigo-pelicula'];
        this.nombrePelicula = this.router.snapshot.params['nombre-pelicula'];
        this.indiceAnuncio = this.router.snapshot.params['indice-anuncios'];
        this.obtenerAnuncios();
    }

    obtenerAnuncios() {
        const indice = Number(localStorage.getItem(UserProperties.INDICE_ANUNCIO)) || 0;
        this.anunciosServices.obtenerAnunciosParaMostrar(indice).subscribe({
            next: (anunciosServer: AnuncioResponse[]) => {
                this.anuncios = anunciosServer;
                if (this.anuncios.length > 0) {
                    const nuevoIndice = indice + 2;
                    localStorage.setItem(UserProperties.INDICE_ANUNCIO, nuevoIndice.toString());
                }
                else {
                    localStorage.setItem(UserProperties.INDICE_ANUNCIO, '0');
                }
            },
            error: (error) => {
                console.log(error.error);
            }
        });
    }
}