import { Component, Input, OnInit } from '@angular/core';
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { AnuncianteServices } from '../../../services/anunciante/anunciante.services';
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';

@Component({
  selector: 'app-anuncio-card-component',
  imports: [],
  templateUrl: './anuncio-card.component.html',
  styleUrl: './anuncio-card.component.css'
})
export class AnuncioCardComponent implements OnInit {
  @Input({ required: true })
  anuncio!: AnuncioResponse;

  @Input()
  linkVideo!: string;

  URLImagen!: string;

  constructor(private anunciosServices: AnuncioServices) { }


  ngOnInit(): void {
    this.obtenerURLImagen();
  }

  obtenerURLImagen(): void {
    this.URLImagen = this.anunciosServices.obtenerImagenUrl(this.anuncio.codigo);
  }

}
