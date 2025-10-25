import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import { RegistroFormComponent } from "../../../components/cuenta/registro-form-component/registro-form.component";

@Component({
  selector: 'app-registro-page',
  imports: [RouterLink, RegistroFormComponent],
  templateUrl: './registro-page.html',
  styleUrl: './registro-page.css'
})
export class RegistroPage {

}
