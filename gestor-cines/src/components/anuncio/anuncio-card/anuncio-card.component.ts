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

  URLImagen!: string;
  linkVideo!: string;
  constructor(private anunciosServices: AnuncioServices) { }


  ngOnInit(): void {
    if (this.anuncio.tipo == 'IMAGEN') {
      this.obtenerURLImagen();
    }
    if (this.anuncio.tipo == 'VIDEO') {
      this.obtenerLink();
    }
  }

  obtenerURLImagen(): void {
    this.URLImagen = this.anunciosServices.obtenerImagenUrl(this.anuncio.codigo);
  }

  obtenerLink(): void {
    this.anunciosServices.obtenerLink(this.anuncio.codigo).subscribe({
      next: (linkServer: string) => {
        this.linkVideo = linkServer;
      },
      error: (error) => {
        console.log(error.error);
      }
    });
  }
}
