import { Component, Input, OnInit } from "@angular/core";
import { RouterLink } from "@angular/router";
import { CrearProyeccionFormComponent } from "../../../components/proyeccion/crear-proyeccion-form/crear-proyeccion-form.component";
import { SalasTableComponent } from "../../../components/sala/sala-table/sala-table.component";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { PeliculasTableComponent } from "../../../components/pelicula/pelicula-table/pelicula-table.component";
import { CineServices } from "../../../services/cine/cine.services";
import { Cine } from "../../../models/cine/cine";
@Component({
    selector: 'app-crear-proyeccion-page',
    imports: [RouterLink, CrearProyeccionFormComponent, SalasTableComponent, HeaderAdminCineComponent, PeliculasTableComponent],
    templateUrl: './crear-proyeccion-page.component.html',
})


export class CrearProyeccionComponentPage implements OnInit {
    codigoCine: string = 'CN-1'
    cine!: Cine
    constructor(private cineServices: CineServices) {

    }
    ngOnInit(): void {
        this.cineServices.traerCinesPorPalabraClave(this.codigoCine).subscribe({
            next: (cineServer: Cine[]) => {
                this.cine = cineServer[0];
            },
            error: (error) => {
                console.log(error.error);
            }
        });
    }
}