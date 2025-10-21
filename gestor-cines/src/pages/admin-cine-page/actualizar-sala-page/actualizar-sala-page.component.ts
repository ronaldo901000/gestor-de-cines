import { Component, Input } from "@angular/core";
import { ActivatedRoute, RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { UpdateSalaFormComponent } from "../../../components/sala/update-sala-form/update-sala-form.component";
import { SalaResponse } from "../../../models/sala/sala-response";
import { SalasServices } from "../../../services/sala/sala.services";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
@Component({
    selector: 'app-actualizar-sala-page',
    imports: [RouterLink, UpdateSalaFormComponent, HeaderAdminCineComponent],
    templateUrl: './actualizar-sala-page.component.html',
})

export class ActualizarSalaComponentPage {

    codigoSala!: string
    sala!: SalaResponse
    constructor(private router: ActivatedRoute, private salaServices: SalasServices) {

    }

    ngOnInit(): void {
        this.codigoSala = this.router.snapshot.params['codigoSala'];
        this.salaServices.obtenerSala(this.codigoSala).subscribe({
            next: (salaServer: SalaResponse) => {
                this.sala = salaServer;
            },
            error: (error) => {
                console.log(error.error);
            }
        });
    }


}