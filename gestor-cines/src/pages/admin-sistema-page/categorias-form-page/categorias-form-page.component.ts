import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { CategoriasFormComponent } from "../../../components/categorias/categorias-form/categorias-form.component";

@Component({
  selector: 'app-categoria-form-page',
  imports: [RouterLink, HeaderAdminSistemaComponent, CategoriasFormComponent],
  templateUrl: './categorias-form-page.component.html',
})

export class CategoriaFormPage {


}