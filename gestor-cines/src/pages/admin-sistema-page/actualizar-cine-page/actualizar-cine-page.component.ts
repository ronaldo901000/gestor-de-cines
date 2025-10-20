import { Component, OnInit } from "@angular/core";

import { ActivatedRoute, RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { ActualizarCineFormComponent } from "../../../components/cine/actualizar-cine-form-component/actualizar-cine-form.component";
@Component({
  selector: 'app-actualizar-cine-page',
  imports: [RouterLink, HeaderAdminSistemaComponent, ActualizarCineFormComponent],
  templateUrl: './actualizar-cine-page.component.html',
  styleUrl: './actualizar-cine-page.component.css'
})
export class ActualizarCineComponentPage implements OnInit {
  codigoCine!:string
  
  constructor(private router: ActivatedRoute){

  }

  ngOnInit(): void {
    this.codigoCine=this.router.snapshot.params['codigoCine'];
  }


}