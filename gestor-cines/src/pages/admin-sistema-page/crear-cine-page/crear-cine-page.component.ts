import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { CineFormComponent } from "../../../components/cine/cine-form-component/cine-form.component";
@Component({
  selector: 'app-crear-cine-page',
  imports: [RouterLink, HeaderAdminSistemaComponent,CineFormComponent],
  templateUrl: './crear-cine-page.component.html',
  styleUrl: './crear-cine-page.component.css'
})
export class CrearCineComponentPage {


}