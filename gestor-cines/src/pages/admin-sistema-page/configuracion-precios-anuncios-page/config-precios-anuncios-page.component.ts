import { Component } from "@angular/core";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { PreciosTableComponent } from "../../../components/precio-anuncio/precios-table/precios-table.component";
import { RouterLink } from "@angular/router";
import { ActualizarPrecioBloqueoComponent } from "../../../components/precio-anuncio/actualizar-precios/actualizar-precio-bloqueo/actualizar-precio-bloqueo.component";
@Component({
    selector: 'app-config-precios-anuncios-page',
    imports: [HeaderAdminSistemaComponent, PreciosTableComponent, RouterLink, ActualizarPrecioBloqueoComponent],
    templateUrl: './config-precios-anuncios-page.component.html',
})
export class ConfigPreciosAnunciosPage{

}