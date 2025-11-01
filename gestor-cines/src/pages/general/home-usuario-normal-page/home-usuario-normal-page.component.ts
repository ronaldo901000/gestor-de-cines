import { Component, Input, OnInit } from '@angular/core';
import { HeaderUsuarioNormalComponent } from "../../../components/header/header-usuario-normal/header-usuario-normal.component";
import { PeliculaCardComponent } from "../../../components/venta-boletos/pelicula-card-component/pelicula-card.component";
import { Pelicula } from '../../../models/pelicula/pelicula';
import { PeliculaServices } from '../../../services/pelicula/pelicula.services';
import { FormsModule } from '@angular/forms';
import { UserProperties } from '../../../shared/user/user-properties';
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { ActivatedRoute } from '@angular/router';
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { RangoAnuncios } from '../../../shared/rango-anuncios';
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
@Component({
    selector: 'app-usuario-normal-page',
    imports: [HeaderUsuarioNormalComponent, PeliculaCardComponent, FormsModule, HeaderAdminSistemaComponent, HeaderAdminCineComponent, AnuncioCardComponent],
    templateUrl: './home-usuario-normal-page.component.html',
    styleUrl: './home-usuario-normal-page.component.css'
})
export class HomeUsuarioNormalPage implements OnInit {
    peliculas: Pelicula[] = [];
    inicio: number = 0;
    rango: number = 6;
    titulo!: string;
    rol!: string | null;

    //anuncios para mostrar
    anuncios: AnuncioResponse[] = [];

    constructor(
        private peliculasServices: PeliculaServices,
        private router: ActivatedRoute,
        private anunciosServices: AnuncioServices) {

    }

    ngOnInit(): void {
        this.rol = localStorage.getItem(UserProperties.ROL);
        this.obtenerPeliculas();
        this.obtenerAnuncios();
    }

    obtenerPeliculas() {
        this.peliculasServices.traerPeliculasPorPagina(this.inicio).subscribe({
            next: (peliculasServer: Pelicula[]) => {
                this.peliculas = peliculasServer;
            }
        });
    }

    buscarPeliculas(cadena: string): void {
        cadena = cadena.trim();
        if (cadena.length > 0) {
            this.peliculasServices.traerPeliculasPorTituloOCategoria(cadena).subscribe({
                next: (peliculasServer: Pelicula[]) => {
                    this.peliculas = peliculasServer;
                },
                error: (error) => {
                    console.log(error.error);
                }
            });
        }
    }
    cargarPoster(codigo: string): string {
        return this.peliculasServices.obtenerPosterUrl(codigo);
    }
    retroceder(): void {
        this.inicio -= this.rango;
        this.obtenerPeliculas();
    }

    avanzar(): void {
        this.inicio += this.rango;
        this.obtenerPeliculas();
    }

    hayMasPeliculas(): boolean {
        if (this.peliculas.length == undefined || this.peliculas.length < this.rango) {
            return false;
        }
        return true;
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

    obtenerURLImagen(codigoAnuncio: string): string {
        return this.anunciosServices.obtenerImagenUrl(codigoAnuncio);
    }

}
