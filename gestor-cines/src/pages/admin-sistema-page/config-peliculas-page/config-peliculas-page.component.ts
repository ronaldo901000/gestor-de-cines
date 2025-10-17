import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { AnunciantesTableComponent } from "../../../components/anunciante/anunciante-table/anunciante-table.component";
import { HeaderConfigPeliculasComponent } from "../../../components/header-config-peliculas/header-config-peliculas.component";
@Component({
    selector: 'app-config-peliculas-page',
    imports: [HeaderAdminSistemaComponent, RouterLink, AnunciantesTableComponent, HeaderConfigPeliculasComponent],
    templateUrl: './config-peliculas-page.component.html',
})
export class ConfigPeliculasPage  {
}