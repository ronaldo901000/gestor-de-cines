import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { HeaderConfigPeliculasComponent } from "../../../components/header-config-peliculas/header-config-peliculas.component";
import { PeliculasTableComponent } from "../../../components/pelicula/pelicula-table/pelicula-table.component";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
@Component({
    selector: 'app-peliculas-page',
    imports: [RouterLink, HeaderConfigPeliculasComponent, PeliculasTableComponent, HeaderAdminCineComponent],
    templateUrl: './peliculas-page.component.html',
})
export class PeliculasPageComponent  {
}