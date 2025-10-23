import { Component, Input } from "@angular/core";
import { RouterLink } from "@angular/router";
import { SalaFormComponent } from "../../../components/sala/sala-form/sala-form.component";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
@Component({
    selector: 'app-crear-sala-page',
    imports: [RouterLink, SalaFormComponent, HeaderAdminCineComponent],
    templateUrl: './crear-sala-page.component.html',
})


export class CrearSalaComponentPage {
    codigoCine:string='CN-1'

}