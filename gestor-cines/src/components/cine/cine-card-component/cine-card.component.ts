import { Component, Input } from "@angular/core";
import { Cine } from "../../../models/cine/cine"; 
import { DatePipe } from "@angular/common";
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-cine-card',
  imports: [DatePipe,RouterLink],
  templateUrl: './cine-card.component.html',
  styleUrl: './cine-card.component.css'
})
export class CineCardComponent {
  @Input({required: true})
  cineSeleccionado!: Cine;


}