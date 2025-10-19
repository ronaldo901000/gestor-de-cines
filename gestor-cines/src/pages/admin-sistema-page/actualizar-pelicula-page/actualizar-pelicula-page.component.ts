import { Component } from "@angular/core";

import { RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { ActualizarCineFormComponent } from "../../../components/cine/actualizar-cine-form-component/actualizar-cine-form.component";
import { ActualizarPeliculaFormComponent } from "../../../components/pelicula/pelicula-form-actualizacion/actualizar-pelicula-form.component";
@Component({
  selector: 'app-actualizar-pelicula-page',
  imports: [RouterLink, HeaderAdminSistemaComponent, ActualizarPeliculaFormComponent],
  templateUrl: './actualizar-pelicula-page.component.html',
})
export class ActualizarPeliculaComponentPage {


}