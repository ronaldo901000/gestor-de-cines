import { Component, Input, ViewChild } from "@angular/core";
import { ToastComponent } from "../../toast/toast.component";
import { Pelicula } from "../../../models/pelicula/pelicula";
import { DatePipe } from "@angular/common";
import { RouterLink } from "@angular/router";
@Component({
  selector: 'app-pelicula-card-component',
  imports: [DatePipe, RouterLink],
  templateUrl: './pelicula-card.component.html',
  styleUrl: './pelicula-card.component.css'
})
export class PeliculaCardComponent {

  @Input({ required: true })
  pelicula!: Pelicula;
  @Input({ required: true })
  posterUrl!: string;

  @ViewChild('toast') toast!: ToastComponent;


}