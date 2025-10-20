import { Component } from "@angular/core";
import { ActivatedRoute, RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { CrearAdminCineFormComponet } from "../../../components/admin-cine-form-component/admin-cine-form.component";
import { Cine } from "../../../models/cine/cine";
import { CineServices } from "../../../services/cine/cine.services";
@Component({
  selector: 'app-crear-admin-cine-page',
  imports: [RouterLink, HeaderAdminSistemaComponent, CrearAdminCineFormComponet],
  templateUrl: './crear-admin-cine-page.component.html',
})
export class CrearAdminCineComponentPage {

  constructor(private router: ActivatedRoute, private cineService: CineServices) { }
  cine!: Cine
  codigoCine!: string;

  ngOnInit(): void {
    this.codigoCine = this.router.snapshot.params['codigoCine'];
    console.log('codigo: '+this.codigoCine);
    this.cineService.traerCinesPorPalabraClave(this.codigoCine).subscribe({
      next: (cineServer: Cine[]) => {
        if (cineServer.length > 0) {
          this.cine = cineServer[0];
        }
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

}