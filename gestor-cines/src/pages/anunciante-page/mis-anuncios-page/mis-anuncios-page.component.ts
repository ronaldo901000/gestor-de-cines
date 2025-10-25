import { Component, OnInit } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';
import { HeaderAdminCineComponent } from '../../../components/header/header-admin-cine/header-admin-cine.component';
@Component({
    selector: 'app-mis-anuncios-admin-cine-page',
    imports: [RouterLink, HeaderAdminCineComponent, HeaderAdminSistemaComponent],
    templateUrl: './mis-anuncios-page.component.html',
    styleUrl: './mis-anuncios-page.component.css'
})
export class MisAnunciosPageComponent implements OnInit{
    rol!:string | null;

    ngOnInit(): void {
        this.rol=localStorage.getItem('role');
    }


}