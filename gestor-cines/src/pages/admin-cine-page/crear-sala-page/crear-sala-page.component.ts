import { Component, Input, OnInit } from "@angular/core";
import { RouterLink } from "@angular/router";
import { SalaFormComponent } from "../../../components/sala/sala-form/sala-form.component";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { UserProperties } from "../../../shared/user/user-properties";
@Component({
    selector: 'app-crear-sala-page',
    imports: [RouterLink, SalaFormComponent, HeaderAdminCineComponent],
    templateUrl: './crear-sala-page.component.html',
})


export class CrearSalaComponentPage implements OnInit {
    codigoCine!: string | null;

    ngOnInit(): void {
        this.codigoCine=localStorage.getItem(UserProperties.CODIGO_CINE);
    }

}