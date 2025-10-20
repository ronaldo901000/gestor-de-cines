import { Component, OnInit } from "@angular/core";

import { ActivatedRoute, RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { ActualizarPreciosFormComponent } from "../../../components/precio-anuncio/actualizar-precios/precios-table/actualizar-precios-form.component";
@Component({
  selector: 'app-precios-anuncios-page',
  imports: [RouterLink, HeaderAdminSistemaComponent, ActualizarPreciosFormComponent],
  templateUrl: './actualizar-precios-page.component.html',
})
export class ActualizarPreciosAnunciosPageComponent implements OnInit{
  id!:number

  constructor(private router: ActivatedRoute){}
  ngOnInit(): void {
    this.id=this.router.snapshot.params['id'];
  }


}