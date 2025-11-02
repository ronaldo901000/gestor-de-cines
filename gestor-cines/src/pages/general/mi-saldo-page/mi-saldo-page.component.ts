import { Component, OnInit } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { HeaderAdminCineComponent } from '../../../components/header/header-admin-cine/header-admin-cine.component';
import { UserProperties } from '../../../shared/user/user-properties';
import { UsuarioServices } from '../../../services/usuario/usuario.services';
import { CurrencyPipe } from '@angular/common';
import { RecargaFormComponent } from "../../../components/recarga-form-component/recarga-form.component";
import { HeaderUsuarioNormalComponent } from "../../../components/header/header-usuario-normal/header-usuario-normal.component";
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
@Component({
    selector: 'app-mi-saldo-page',
    imports: [HeaderAdminCineComponent, HeaderAdminSistemaComponent, CurrencyPipe, RecargaFormComponent, HeaderUsuarioNormalComponent, AnuncioCardComponent],
    templateUrl: './mi-saldo-page.component.html',
})
export class MiSaldoPageComponent implements OnInit {
    rol!: string | null;
    idUser!: string | null;
    saldoActual!: number;
    anuncios: AnuncioResponse[] = [];

    constructor(private usuarioService: UsuarioServices, private anunciosServices: AnuncioServices) { }

    ngOnInit(): void {
        this.idUser = localStorage.getItem(UserProperties.ID);
        this.rol = localStorage.getItem(UserProperties.ROL);
        this.traerSaldoActual();
        this.obtenerAnuncios();
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

    obtenerAnuncios(): void {
        const indice = Number(localStorage.getItem(UserProperties.INDICE_ANUNCIO)) || 0;
        this.anunciosServices.obtenerAnunciosParaMostrar(indice).subscribe({
            next: (anunciosServer: AnuncioResponse[]) => {
                this.anuncios = anunciosServer;
                if (this.anuncios.length < 2) {
                    localStorage.setItem(UserProperties.INDICE_ANUNCIO, '0');
                }
                else {
                    const nuevoIndice = indice + 2;
                    localStorage.setItem(UserProperties.INDICE_ANUNCIO, nuevoIndice.toString());
                }
            },
            error: (error) => {
                console.log(error.error);
            }
        });
    }

}