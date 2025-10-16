import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { CrearAdminCineFormComponet } from "../../../components/admin-cine-form-component/admin-cine-form.component";
import { Cine } from "../../../models/cine/cine";
@Component({
  selector: 'app-crear-admin-cine-page',
  imports: [RouterLink, HeaderAdminSistemaComponent, CrearAdminCineFormComponet],
  templateUrl: './crear-admin-cine-page.component.html',
})
export class CrearAdminCineComponentPage {

  cine!:Cine

      ngOnInit(): void {
         this.cine = history.state['cine'];
    }

}