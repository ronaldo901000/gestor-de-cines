import { Component, OnInit } from '@angular/core';
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { Cine } from '../../../models/cine/cine';
import { CineServices } from '../../../services/cine/cine.services';
import { AdminCineProperties } from '../../../shared/user/admin-cine-properties';
import { RecargaCineComponent } from "../../../components/cine/recarga-saldo-component/recarga-cine.component";
import { CurrencyPipe } from '@angular/common';
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { UserProperties } from '../../../shared/user/user-properties';
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
@Component({
  selector: 'app-saldo-cine-page',
  imports: [HeaderAdminCineComponent, RecargaCineComponent, CurrencyPipe, AnuncioCardComponent],
  templateUrl: './saldo-cine-page.html',
  styleUrl: './saldo-cine-page.css'
})
export class SaldoCinePage implements OnInit {

  cine!: Cine;
  codigoCine!: string | null;
  saldoActual!: number;
  hayBloqueador!: string | null;
  anuncios: AnuncioResponse[] = [];

  constructor(private cineServices: CineServices, private anunciosServices: AnuncioServices) {

  }
  ngOnInit(): void {
    this.codigoCine = localStorage.getItem(AdminCineProperties.CODIGO_CINE);
    console.log('codigo cine: ' + this.codigoCine);
    this.obtenerCine();
    this.obtenerSaldo();
    this.hayBloqueador = localStorage.getItem(AdminCineProperties.TIENE_BLOQUEADOR_ANUNCIOS);
    if (this.hayBloqueador == 'false') {
      this.obtenerAnuncios();
    }
  }

  obtenerCine() {
    if (this.codigoCine) {
      this.cineServices.traerCinesPorPalabraClave(this.codigoCine).subscribe({
        next: (cineServer: Cine[]) => {
          if (cineServer.length > 0) {
            this.cine = cineServer[0];
          }
          else {
            console.log('No se encontro el cine');
          }
        },
        error: (error) => {
          console.log(error);
        }
      });
    }
  }

  obtenerSaldo() {
    if (this.codigoCine) {
      this.cineServices.obtenerSaldoActual(this.codigoCine).subscribe({
        next: (saldo: number) => {
          this.saldoActual = saldo;
        },
        error: (error) => {
          console.log(error.error);
        }
      });
    }
  }

  obtenerAnuncios() {
    const indice = Number(localStorage.getItem(UserProperties.INDICE_ANUNCIO));
    this.anunciosServices.obtenerAnunciosParaMostrar(indice).subscribe({
      next: (anunciosServer: AnuncioResponse[]) => {
        this.anuncios = anunciosServer;
        if (this.anuncios.length < 2) {
          localStorage.setItem(UserProperties.INDICE_ANUNCIO, '0');
        }
        else {
          const nuevoIndice = indice + 2;
          localStorage.setItem(UserProperties.INDICE_ANUNCIO, nuevoIndice.toString());
        }
      },
      error: (error) => {
        console.log(error.error);
      }
    });
  }


}

