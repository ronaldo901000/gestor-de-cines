import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { HeaderConfigPeliculasComponent } from "../../../components/header-config-peliculas/header-config-peliculas.component";
import { PeliculasTableComponent } from "../../../components/pelicula/pelicula-table/pelicula-table.component";
@Component({
    selector: 'app-config-peliculas-page',
    imports: [HeaderAdminSistemaComponent, RouterLink, HeaderConfigPeliculasComponent, PeliculasTableComponent],
    templateUrl: './config-peliculas-page.component.html',
})
export class ConfigPeliculasPage  {
}