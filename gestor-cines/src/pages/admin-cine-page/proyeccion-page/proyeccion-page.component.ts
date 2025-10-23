import { Component } from '@angular/core';
import { HeaderAdminCineComponent } from '../../../components/header/header-admin-cine/header-admin-cine.component';
import { RouterLink } from '@angular/router';
import { SalasTableComponent } from "../../../components/sala/sala-table/sala-table.component";
import { ProyeccionesTableComponent } from "../../../components/proyeccion/proyecciones-table/proyecciones-table.component";
@Component({
    selector: 'app-salas-page',
    imports: [HeaderAdminCineComponent, RouterLink, ProyeccionesTableComponent],
    templateUrl: './proyeccion-page.component.html',
})
export class ProyeccionessPageComponent {
    codigoCine: string = 'CN-1';
}