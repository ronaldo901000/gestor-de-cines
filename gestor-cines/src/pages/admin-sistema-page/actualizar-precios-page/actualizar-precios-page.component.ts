import { Component } from "@angular/core";

import { RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { ActualizarCineFormComponent } from "../../../components/cine/actualizar-cine-form-component/actualizar-cine-form.component";
import { ActualizarPreciosFormComponent } from "../../../components/precio-anuncio/actualizar-precios/precios-table/actualizar-precios-form.component";
@Component({
  selector: 'app-precios-anuncios-page',
  imports: [RouterLink, HeaderAdminSistemaComponent, ActualizarPreciosFormComponent],
  templateUrl: './actualizar-precios-page.component.html',
})
export class ActualizarPreciosAnunciosPageComponent {


}