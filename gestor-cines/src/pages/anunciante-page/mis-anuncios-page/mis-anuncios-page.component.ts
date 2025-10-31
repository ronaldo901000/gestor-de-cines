import { Component, OnInit } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { HeaderAdminCineComponent } from '../../../components/header/header-admin-cine/header-admin-cine.component';
import { UserProperties } from '../../../shared/user/user-properties';
import { AnunciosTableComponent } from "../../../components/anuncio/anuncios-table/anuncios-table.component";
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
@Component({
    selector: 'app-mis-anuncios-admin-cine-page',
    imports: [RouterLink, HeaderAdminCineComponent, HeaderAdminSistemaComponent, AnunciosTableComponent],
    templateUrl: './mis-anuncios-page.component.html',
    styleUrl: './mis-anuncios-page.component.css'
})
export class MisAnunciosPageComponent implements OnInit {
    rol!: string | null;
    idAnunciante!: string | null;
    anuncios: AnuncioResponse[] = [];

    constructor(private anuncioService: AnuncioServices) { }
    ngOnInit(): void {
        this.rol = localStorage.getItem(UserProperties.ROL);
        this.idAnunciante = localStorage.getItem(UserProperties.ID);
        this.obtenerAnuncios(); 
    }

    obtenerAnuncios() {
        if (this.idAnunciante) {
            this.anuncioService.obtenerAnuncios(this.idAnunciante).subscribe({
                next: (anunciosServer: AnuncioResponse[]) => {
                    this.anuncios = anunciosServer;
                },
                error: (error) => {
                    console.log(error.error);
                }
            });
        }
    }

}