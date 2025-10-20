import { Component, OnInit } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { Usuario } from '../../../models/usuario/usuario';
import { AdminSistemaServices } from '../../../services/admin-sistema/admin-sistema.service';
import { AdminsSistemaTableComponent } from "../../../components/admin-sistema/admin-sistema-table/admins-sistema-table.component";
@Component({
    selector: 'app-admins-sistema-page',
    imports: [HeaderAdminSistemaComponent, RouterLink, AdminsSistemaTableComponent],
    templateUrl: './admin-sistema-page.component.html',
})
export class AdminsSistemaPage implements OnInit {
    adminsSistema!: Usuario[];
    constructor(private adminsSistemaServices: AdminSistemaServices) {

    }

    ngOnInit(): void {
        this.adminsSistemaServices.traerAdminsSistema().subscribe({
            next: (adminsServer: Usuario[]) => {
                this.adminsSistema = adminsServer;
            },
            error: (error: any) => {
                console.log(error);
            }
        });
    }

}