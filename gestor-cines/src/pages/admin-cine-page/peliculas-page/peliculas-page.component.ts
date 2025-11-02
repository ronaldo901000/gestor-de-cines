import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PeliculasTableComponent } from "../../../components/pelicula/pelicula-table/pelicula-table.component";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { AdminCineProperties } from '../../../shared/user/admin-cine-properties';
import { UserProperties } from '../../../shared/user/user-properties';
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
@Component({
    selector: 'app-peliculas-page',
    imports: [RouterLink, PeliculasTableComponent, HeaderAdminCineComponent, AnuncioCardComponent],
    templateUrl: './peliculas-page.component.html',
})
export class PeliculasPageComponent implements OnInit {

    hayBloqueador!: string | null;
    anuncios: AnuncioResponse[] = [];

    constructor(private anunciosServices: AnuncioServices) { }

    ngOnInit(): void {
        this.hayBloqueador = localStorage.getItem(AdminCineProperties.TIENE_BLOQUEADOR_ANUNCIOS);
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