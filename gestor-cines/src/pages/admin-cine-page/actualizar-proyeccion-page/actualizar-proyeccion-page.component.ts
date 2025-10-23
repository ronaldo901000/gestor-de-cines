import { Component, Input } from "@angular/core";
import { ActivatedRoute, RouterLink } from "@angular/router";
import { UpdateSalaFormComponent } from "../../../components/sala/update-sala-form/update-sala-form.component";
import { SalaResponse } from "../../../models/sala/sala-response";
import { SalasServices } from "../../../services/sala/sala.services";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { ProyeccionResponse } from "../../../models/proyeccion/proyeccion-response";
import { ProyeccionesServices } from "../../../services/proyeccion/proyeccion.services";
import { UpdateProyeccionFormComponent } from "../../../components/proyeccion/update-proyeccion-form/update-proyeccion-form.component";
import { PeliculasTableComponent } from "../../../components/pelicula/pelicula-table/pelicula-table.component";
import { SalasTableComponent } from "../../../components/sala/sala-table/sala-table.component";
@Component({
    selector: 'app-actualizar-proyeccion-page',
    imports: [RouterLink, HeaderAdminCineComponent, UpdateProyeccionFormComponent, PeliculasTableComponent],
    templateUrl: './actualizar-proyeccion-page.component.html',
})

export class ActualizarProyeccionComponentPage {

    codigoProyeccion!: string
    proyeccion!: ProyeccionResponse
    constructor(private router: ActivatedRoute, private proyeccionServices: ProyeccionesServices) {

    }

    ngOnInit(): void {
        this.codigoProyeccion = this.router.snapshot.params['codigoProyeccion'];
        this.proyeccionServices.obtenerProyecion(this.codigoProyeccion).subscribe({
            next: (proyeccionServer: ProyeccionResponse) => {
                this.proyeccion = proyeccionServer;
            },
            error: (error) => {
                console.log(error.error);
            }
        });
    }


}