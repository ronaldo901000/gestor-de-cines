import { Component, EventEmitter, Input, Output, ViewChild } from "@angular/core";
import { CineServices } from "../../../services/cine/cine.services";
import { ToastComponent } from "../../toast/toast.component";
import { Pelicula } from "../../../models/pelicula/pelicula";
import { DatePipe } from "@angular/common";
@Component({
  selector: 'app-pelicula-card-component',
  imports: [DatePipe],
  templateUrl: './pelicula-card.component.html',
  styleUrl: './pelicula-card.component.css'
})
export class PeliculaCardComponent {

  @Input({required:true})
  pelicula!: Pelicula;

  @ViewChild('toast') toast!: ToastComponent;

  constructor(private cineService: CineServices) {

  }

}