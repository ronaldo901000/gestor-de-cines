import { Component, OnInit } from '@angular/core';
import { HeaderAdminCineComponent } from '../../../components/header/header-admin-cine/header-admin-cine.component';
import { RouterLink } from '@angular/router';
import { ProyeccionesTableComponent } from "../../../components/proyeccion/proyecciones-table/proyecciones-table.component";
import { UserProperties } from '../../../shared/user/user-properties';
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { AdminCineProperties } from '../../../shared/user/admin-cine-properties';
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
@Component({
    selector: 'app-salas-page',
    imports: [HeaderAdminCineComponent, RouterLink, ProyeccionesTableComponent, AnuncioCardComponent],
    templateUrl: './proyeccion-page.component.html',
})
export class ProyeccionessPageComponent implements OnInit {

    codigoCine!: string | null;
    hayBloqueador!: string | null;
    anuncios: AnuncioResponse[] = [];

    constructor(private anunciosServices: AnuncioServices) { }

    ngOnInit(): void {
        this.codigoCine = localStorage.getItem(UserProperties.CODIGO_CINE);
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