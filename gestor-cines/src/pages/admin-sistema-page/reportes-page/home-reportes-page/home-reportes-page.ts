import { Component } from '@angular/core';
import { HeaderAdminSistemaComponent } from "../../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home-reportes-page',
  imports: [HeaderAdminSistemaComponent, RouterLink],
  templateUrl: './home-reportes-page.html',
})
export class HomeReportesPage {

}
