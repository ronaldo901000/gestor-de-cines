import { Component, Input, OnInit } from "@angular/core";
import { RouterLink } from "@angular/router";
import { CrearProyeccionFormComponent } from "../../../components/proyeccion/crear-proyeccion-form/crear-proyeccion-form.component";
import { SalasTableComponent } from "../../../components/sala/sala-table/sala-table.component";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { PeliculasTableComponent } from "../../../components/pelicula/pelicula-table/pelicula-table.component";
import { CineServices } from "../../../services/cine/cine.services";
import { Cine } from "../../../models/cine/cine";
import { UserProperties } from "../../../shared/user/user-properties";
import { AnuncioResponse } from "../../../models/anuncio/anuncio-response";
import { AnuncioServices } from "../../../services/anuncio/anuncio.services";
import { AdminCineProperties } from "../../../shared/user/admin-cine-properties";
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
@Component({
    selector: 'app-crear-proyeccion-page',
    imports: [RouterLink, CrearProyeccionFormComponent, SalasTableComponent, HeaderAdminCineComponent, PeliculasTableComponent, AnuncioCardComponent],
    templateUrl: './crear-proyeccion-page.component.html',
})

export class CrearProyeccionComponentPage implements OnInit {

    codigoCine!: string | null;
    cine!: Cine;
    hayBloqueador!: string | null;
    anuncios: AnuncioResponse[] = [];

    constructor(private cineServices: CineServices, private anunciosServices: AnuncioServices) {

    }
    ngOnInit(): void {
        this.codigoCine = localStorage.getItem(UserProperties.CODIGO_CINE);
        if (this.codigoCine) {
            this.cineServices.traerCinesPorPalabraClave(this.codigoCine).subscribe({
                next: (cineServer: Cine[]) => {
                    this.cine = cineServer[0];
                },
                error: (error) => {
                    console.log(error.error);
                }
            });
        }
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