import { Component, OnInit } from '@angular/core';
import { HeaderAdminCineComponent } from '../../../components/header/header-admin-cine/header-admin-cine.component';
import { RouterLink } from '@angular/router';
import { SalasTableComponent } from "../../../components/sala/sala-table/sala-table.component";
import { UserProperties } from '../../../shared/user/user-properties';
@Component({
    selector: 'app-salas-page',
    imports: [HeaderAdminCineComponent, RouterLink, SalasTableComponent],
    templateUrl: './salas-page.component.html',
})
export class SalasPageComponent implements OnInit {

    codigoCine!: string | null;

    ngOnInit(): void {
        this.codigoCine=localStorage.getItem(UserProperties.CODIGO_CINE);
    }
}