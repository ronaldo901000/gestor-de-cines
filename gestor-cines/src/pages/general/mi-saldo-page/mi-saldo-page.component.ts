import { Component, OnInit } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { HeaderAdminCineComponent } from '../../../components/header/header-admin-cine/header-admin-cine.component';
import { UserProperties } from '../../../shared/user/user-properties';
import { UsuarioServices } from '../../../services/usuario/usuario.services';
import { CurrencyPipe } from '@angular/common';
import { RecargaFormComponent } from "../../../components/recarga-form-component/recarga-form.component";
import { HeaderUsuarioNormalComponent } from "../../../components/header/header-usuario-normal/header-usuario-normal.component";
@Component({
    selector: 'app-mi-saldo-page',
    imports: [HeaderAdminCineComponent, HeaderAdminSistemaComponent, CurrencyPipe, RecargaFormComponent, HeaderUsuarioNormalComponent],
    templateUrl: './mi-saldo-page.component.html',
})
export class MiSaldoPageComponent implements OnInit {
    rol!: string | null;
    idUser!: string | null;
    saldoActual!: number;
    constructor(private usuarioService: UsuarioServices) { }

    ngOnInit(): void {
        this.idUser = localStorage.getItem(UserProperties.ID);
        this.rol = localStorage.getItem(UserProperties.ROL);
        this.traerSaldoActual();
    }


    traerSaldoActual(): void {
        if (this.idUser) {
            this.usuarioService.obtenerSaldoActual(this.idUser).subscribe({
                next: (saldoServer: number) => {
                    this.saldoActual = saldoServer;
                }
            });
        }
    }
}