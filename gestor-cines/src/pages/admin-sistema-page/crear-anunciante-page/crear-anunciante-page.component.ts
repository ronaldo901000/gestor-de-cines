import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { AnuncianteFormComponent } from "../../../components/anunciante/anunciante-fom/anunciante-form.componet";

@Component({
  selector: 'app-crear-anunciante-page',
  imports: [RouterLink, HeaderAdminSistemaComponent, AnuncianteFormComponent],
  templateUrl: './crear-anunciante-page.component .html',
})

export class CrearAnunciantePageComponent {


}