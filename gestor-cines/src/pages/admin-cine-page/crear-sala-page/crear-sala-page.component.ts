import { Component, Input } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { CineFormComponent } from "../../../components/cine/cine-form-component/cine-form.component";
import { SalaFormComponent } from "../../../components/sala/sala-form/sala-form.component";
@Component({
    selector: 'app-crear-sala-page',
    imports: [RouterLink, HeaderAdminSistemaComponent, SalaFormComponent],
    templateUrl: './crear-sala-page.component.html',
})


export class CrearSalaComponentPage {
    codigoCine:string='CN-1'

}