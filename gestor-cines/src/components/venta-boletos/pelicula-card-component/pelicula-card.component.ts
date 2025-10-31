import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from "@angular/core";
import { CineServices } from "../../../services/cine/cine.services";
import { ToastComponent } from "../../toast/toast.component";
import { Pelicula } from "../../../models/pelicula/pelicula";
import { DatePipe } from "@angular/common";
import { RouterLink } from "@angular/router";
import { PeliculaServices } from "../../../services/pelicula/pelicula.services";
@Component({
  selector: 'app-pelicula-card-component',
  imports: [DatePipe, RouterLink],
  templateUrl: './pelicula-card.component.html',
  styleUrl: './pelicula-card.component.css'
})
export class PeliculaCardComponent{

  @Input({ required: true })
  pelicula!: Pelicula;
  @Input({ required: true })
  posterUrl!: string;
  @ViewChild('toast') toast!: ToastComponent;


}