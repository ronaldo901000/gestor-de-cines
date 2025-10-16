import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { ToastComponent } from '../../../components/toast/toast.component';
import { AnunciantesTableComponent } from "../../../components/anunciante/anunciante-table/anunciante-table.component";
@Component({
    selector: 'app-anunciantes-page',
    imports: [HeaderAdminSistemaComponent, RouterLink, AnunciantesTableComponent],
    templateUrl: './anunciantes-page.component.html',
})
export class AnunciantesPageComponent implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;

    constructor() {
       
    }

    ngOnInit(): void {
     
    }

    traerAdmins(){
        
    }
}