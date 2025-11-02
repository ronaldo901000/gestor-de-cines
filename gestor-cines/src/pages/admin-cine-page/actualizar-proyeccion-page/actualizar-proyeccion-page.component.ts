import { Component } from "@angular/core";
import { ActivatedRoute, RouterLink } from "@angular/router";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { ProyeccionResponse } from "../../../models/proyeccion/proyeccion-response";
import { ProyeccionesServices } from "../../../services/proyeccion/proyeccion.services";
import { UpdateProyeccionFormComponent } from "../../../components/proyeccion/update-proyeccion-form/update-proyeccion-form.component";
import { PeliculasTableComponent } from "../../../components/pelicula/pelicula-table/pelicula-table.component";
import { SalasTableComponent } from "../../../components/sala/sala-table/sala-table.component";
import { UserProperties } from "../../../shared/user/user-properties";
import { AnuncioResponse } from "../../../models/anuncio/anuncio-response";
import { AnuncioServices } from "../../../services/anuncio/anuncio.services";
import { AdminCineProperties } from "../../../shared/user/admin-cine-properties";
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
@Component({
    selector: 'app-actualizar-proyeccion-page',
    imports: [RouterLink, HeaderAdminCineComponent, UpdateProyeccionFormComponent, PeliculasTableComponent, SalasTableComponent, AnuncioCardComponent],
    templateUrl: './actualizar-proyeccion-page.component.html',
})

export class ActualizarProyeccionComponentPage {

    codigoCine!: string | null;
    codigoProyeccion!: string
    proyeccion!: ProyeccionResponse
    hayBloqueador!: string | null;
    anuncios: AnuncioResponse[] = [];

    constructor(private router: ActivatedRoute, private proyeccionServices: ProyeccionesServices,
        private anunciosServices: AnuncioServices
    ) {

    }

    ngOnInit(): void {
        this.codigoProyeccion = this.router.snapshot.params['codigoProyeccion'];
        this.codigoCine = localStorage.getItem(UserProperties.CODIGO_CINE);
        this.proyeccionServices.obtenerProyecion(this.codigoProyeccion).subscribe({
            next: (proyeccionServer: ProyeccionResponse) => {
                this.proyeccion = proyeccionServer;
            },
            error: (error) => {
                console.log(error.error);
            }
        });
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