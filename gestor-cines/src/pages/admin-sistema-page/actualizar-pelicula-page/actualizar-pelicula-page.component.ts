import { Component, OnInit } from "@angular/core";

import { ActivatedRoute, RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { ActualizarPeliculaFormComponent } from "../../../components/pelicula/pelicula-form-actualizacion/actualizar-pelicula-form.component";
@Component({
  selector: 'app-actualizar-pelicula-page',
  imports: [RouterLink, HeaderAdminSistemaComponent, ActualizarPeliculaFormComponent],
  templateUrl: './actualizar-pelicula-page.component.html',
})
export class ActualizarPeliculaComponentPage implements OnInit{

  codigoPelicula!:string;

  constructor(private router: ActivatedRoute){

  }
  ngOnInit(): void {
    this.codigoPelicula=this.router.snapshot.params['codigoPelicula'];
  }

}