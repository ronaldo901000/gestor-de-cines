import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { CrearAdminSistemaFormComponet } from "../../../components/admin-sistema/admin-sistema-form/admin-sistema-form.component";
@Component({
  selector: 'app-crear-admin-sistema-page',
  imports: [RouterLink, HeaderAdminSistemaComponent, CrearAdminSistemaFormComponet],
  templateUrl: './crear-admin-sistema-page.component.html',
})
export class CrearAdminSistemaComponentPage {

}