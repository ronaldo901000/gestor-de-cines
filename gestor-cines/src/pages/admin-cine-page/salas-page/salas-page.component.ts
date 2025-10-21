import { Component} from '@angular/core';
import { HeaderAdminCineComponent } from '../../../components/header/header-admin-cine/header-admin-cine.component';
import { RouterLink } from '@angular/router';
import { SalasTableComponent } from "../../../components/sala/sala-table/sala-table.component";
@Component({
    selector: 'app-salas-page',
    imports: [HeaderAdminCineComponent, RouterLink, SalasTableComponent],
    templateUrl: './salas-page.component.html',
})
export class SalasPageComponent {
    codigoCine:string='CN-1';
}