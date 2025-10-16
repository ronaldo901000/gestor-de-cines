import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { Router, RouterLink } from '@angular/router';
import { ToastComponent } from '../../../components/toast/toast.component';
import { TablaAdminsCine } from "../../../components/tabla-admins-cine/tabla-admins-cine.component";
import { Cine } from '../../../models/cine/cine';
import { Usuario } from '../../../models/usuario/usuario';
import { AdminCinesServices } from '../../../services/admin-cine/admin-cine.services';
@Component({
    selector: 'app-admins-cine-page',
    imports: [HeaderAdminSistemaComponent, RouterLink, TablaAdminsCine],
    templateUrl: './admins-cine-page.component.html',
})
export class AdminsCinePage implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;
    adminsCine!:Usuario[];
    cine!: Cine;

    constructor(private adminsService:AdminCinesServices) {
       
    }

    ngOnInit(): void {
         this.cine = history.state['cine'];
    }

    traerAdmins(){
        
    }
}