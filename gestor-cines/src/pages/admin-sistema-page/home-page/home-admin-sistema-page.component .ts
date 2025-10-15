import { Component } from '@angular/core';
import { HeaderAdminSistemaComponent } from '../../../components/header/header-admin-sistema/header-admin-sistema.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home-admin-sistema-page',
  imports: [HeaderAdminSistemaComponent, RouterLink],
  templateUrl: './home-admin-sistema-page.component.html'
})
export class HomeAdminSistema {


}
