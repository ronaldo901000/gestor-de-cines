import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";

@Component({
  selector: 'app-home-admin-cine-page',
  imports: [RouterLink, HeaderAdminCineComponent],
  templateUrl: './home-admin-cine-page.component.html',
})
export class HomeAdminCine {
}
