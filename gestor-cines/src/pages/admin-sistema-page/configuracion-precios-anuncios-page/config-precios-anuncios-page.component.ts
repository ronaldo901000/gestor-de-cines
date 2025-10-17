import { Component } from "@angular/core";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { PreciosTableComponent } from "../../../components/precio-anuncio/precios-table/precios-table.component";
import { RouterLink } from "@angular/router";
@Component({
    selector: 'app-config-precios-anuncios-page',
    imports: [HeaderAdminSistemaComponent, PreciosTableComponent, RouterLink],
    templateUrl: './config-precios-anuncios-page.component.html',
})
export class ConfigPreciosAnunciosPage{

}