import { Component, Input } from "@angular/core";
import { ActivatedRoute, RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { UpdateSalaFormComponent } from "../../../components/sala/update-sala-form/update-sala-form.component";
import { SalaResponse } from "../../../models/sala/sala-response";
import { SalasServices } from "../../../services/sala/sala.services";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { AnuncioResponse } from "../../../models/anuncio/anuncio-response";
import { AnuncioServices } from "../../../services/anuncio/anuncio.services";
import { AdminCineProperties } from "../../../shared/user/admin-cine-properties";
import { UserProperties } from "../../../shared/user/user-properties";
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
@Component({
    selector: 'app-actualizar-sala-page',
    imports: [RouterLink, UpdateSalaFormComponent, HeaderAdminCineComponent, AnuncioCardComponent],
    templateUrl: './actualizar-sala-page.component.html',
})

export class ActualizarSalaComponentPage {

    codigoSala!: string
    sala!: SalaResponse
    hayBloqueador!: string | null;
    anuncios: AnuncioResponse[] = [];

    constructor(private router: ActivatedRoute, private salaServices: SalasServices,
        private anunciosServices: AnuncioServices
    ) {

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
        this.hayBloqueador = localStorage.getItem(AdminCineProperties.TIENE_BLOQUEADOR_ANUNCIOS);
        //si no hay bloqueador de anuncios se traen de la api
        if (this.hayBloqueador == 'false') {
            this.obtenerAnuncios();
        }
    }

    obtenerAnuncios() {
        const indice = Number(localStorage.getItem(UserProperties.INDICE_ANUNCIO));
        this.anunciosServices.obtenerAnunciosParaMostrar(indice).subscribe({
            next: (anunciosServer: AnuncioResponse[]) => {
                this.anuncios = anunciosServer;
                if (this.anuncios.length < 2) {
                    localStorage.setItem(UserProperties.INDICE_ANUNCIO, '0');
                }
                else {
                    const nuevoIndice = indice + 2;
                    localStorage.setItem(UserProperties.INDICE_ANUNCIO, nuevoIndice.toString());
                }
            },
            error: (error) => {
                console.log(error.error);
            }
        });
    }


}