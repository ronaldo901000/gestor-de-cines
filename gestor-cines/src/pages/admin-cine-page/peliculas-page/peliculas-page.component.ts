import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PeliculasTableComponent } from "../../../components/pelicula/pelicula-table/pelicula-table.component";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
@Component({
    selector: 'app-peliculas-page',
    imports: [RouterLink, PeliculasTableComponent, HeaderAdminCineComponent],
    templateUrl: './peliculas-page.component.html',
})
export class PeliculasPageComponent  {
}