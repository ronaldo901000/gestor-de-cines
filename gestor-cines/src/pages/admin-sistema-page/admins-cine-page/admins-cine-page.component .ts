import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ToastComponent } from '../../../components/toast/toast.component';
import { TablaAdminsCine } from "../../../components/tabla-admins-cine/tabla-admins-cine.component";
import { Cine } from '../../../models/cine/cine';
import { Usuario } from '../../../models/usuario/usuario';
import { CineServices } from '../../../services/cine/cine.services';
@Component({
    selector: 'app-admins-cine-page',
    imports: [HeaderAdminSistemaComponent, RouterLink, TablaAdminsCine],
    templateUrl: './admins-cine-page.component.html',
})
export class AdminsCinePage implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;
    adminsCine!: Usuario[];
    cine!: Cine;
    codigoCine!: string;
    constructor(private router: ActivatedRoute, private cineService: CineServices) {

    }

    ngOnInit(): void {
        this.codigoCine = this.router.snapshot.params['codigoCine'];
        this.cineService.traerCinesPorPalabraClave(this.codigoCine).subscribe({
            next: (cineServer: Cine[]) => {
                if (cineServer.length > 0) {
                    this.cine = cineServer[0];
                }
            },
            error:(error:any)=>{
                console.log(error);
            }
        });
    }

}