import { Component, OnInit } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HeaderAdminCineComponent } from '../../../components/header/header-admin-cine/header-admin-cine.component';
import { UserProperties } from '../../../shared/user/user-properties';
import { HeaderUsuarioNormalComponent } from "../../../components/header/header-usuario-normal/header-usuario-normal.component";
import { ProyeccionesTableComponent } from "../../../components/proyeccion/proyecciones-table/proyecciones-table.component";
import { ProyeccionesPeliculaTableComponent } from "../../../components/venta-boletos/proyecciones-table-component/proyecciones-table.component";
@Component({
    selector: 'app-proyecciones-page',
    imports: [HeaderAdminCineComponent, HeaderAdminSistemaComponent, HeaderUsuarioNormalComponent, ProyeccionesPeliculaTableComponent, RouterLink],
    templateUrl: './proyecciones-page.html',
})
export class ProyeccionesPeliculaPageComponent implements OnInit {
    rol!: string | null;
    idUser!: string | null;

    codigoPelicula!:string;
    nombrePelicula!:string
    constructor(private router: ActivatedRoute ) { }

    ngOnInit(): void {
        this.idUser = localStorage.getItem(UserProperties.ID);
        this.rol = localStorage.getItem(UserProperties.ROL);
        //se obtiene el codigo de pelicula pasada por la url
        this.codigoPelicula = this.router.snapshot.params['codigo-pelicula'];
        this.nombrePelicula = this.router.snapshot.params['nombre-pelicula'];
    }


}