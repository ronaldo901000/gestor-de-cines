import { Component } from "@angular/core";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { RouterLink } from "@angular/router";
import { CostosTableComponent } from "../../../components/costos-funcionamiento/costos-table/costos-table.component";
import { FormsModule } from "@angular/forms";
@Component({
    selector: 'app-costo-funcionamiento-page',
    imports: [HeaderAdminSistemaComponent, RouterLink, CostosTableComponent,FormsModule],
    templateUrl: './costo-funcionamiento-page.component.html',
})
export class CostoFuncionamientoPage{
  
}