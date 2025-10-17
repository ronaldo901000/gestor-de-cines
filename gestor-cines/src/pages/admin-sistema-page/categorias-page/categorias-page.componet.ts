import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { AnunciantesTableComponent } from "../../../components/categorias/categoria-table/categoria-table.component";
@Component({
    selector: 'app-categorias-page',
    imports: [HeaderAdminSistemaComponent, RouterLink, AnunciantesTableComponent],
    templateUrl: './categorias-page.componet.html',
})
export class CategoriasPage  {
}