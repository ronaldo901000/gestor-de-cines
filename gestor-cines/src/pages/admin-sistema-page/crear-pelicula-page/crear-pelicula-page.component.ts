import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { CineFormComponent } from "../../../components/cine/cine-form-component/cine-form.component";
import { PeliculaFormComponent } from "../../../components/pelicula/pelicula-form/pelicula-form.component";
@Component({
  selector: 'app-crear-pelicula-page',
  imports: [RouterLink, HeaderAdminSistemaComponent, PeliculaFormComponent],
  templateUrl: './crear-pelicula-page.component.html',
})
export class CrearPeliculaPage {


}