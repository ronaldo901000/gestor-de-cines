import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { CineFormComponent } from "../../../components/cine/cine-form-component/cine-form.component";
import { CostosCineFormComponent } from "../../../components/costos-funcionamiento/costos-form/costos-form.component";
@Component({
  selector: 'app-crear-costo-cine-page',
  imports: [RouterLink, HeaderAdminSistemaComponent, CostosCineFormComponent],
  templateUrl: './crear-costo-cine-page.component.html',
})
export class CrearCostoCinePage {


}