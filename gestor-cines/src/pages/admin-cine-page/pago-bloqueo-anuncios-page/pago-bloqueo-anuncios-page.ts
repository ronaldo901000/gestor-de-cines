import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { UserProperties } from '../../../shared/user/user-properties';
import { CineServices } from '../../../services/cine/cine.services';
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
import { BloqueoAnuncioFormComponent } from "../../../components/anuncio/bloqueo-anuncio-form/bloqueo-anuncio-form.component";
import { CurrencyPipe } from '@angular/common';
import { CostoGlobalServices } from '../../../services/costo-funcionamiento/costo-global.services';
import { AdminCineProperties } from '../../../shared/user/admin-cine-properties';
@Component({
  selector: 'app-pago-bloqueo-anuncios-page',
  imports: [HeaderAdminCineComponent, AnuncioCardComponent, 
    BloqueoAnuncioFormComponent, CurrencyPipe],
  templateUrl: './pago-bloqueo-anuncios-page.html'
})
export class PagoBloqueoAnunciosPage implements OnInit {
  codigoCine!: string | null;
  saldo!: number;
  anuncios:AnuncioResponse []=[];
  costoBloqueo!:number;
  hayBloqueador!:string | null;

  constructor(private cineService: CineServices,
    private anuncioService: AnuncioServices, private costoService: CostoGlobalServices) {
  }

  ngOnInit(): void {
    this.codigoCine = localStorage.getItem(UserProperties.CODIGO_CINE);
    this.obtenerSaldo();
        this.hayBloqueador = localStorage.getItem(AdminCineProperties.TIENE_BLOQUEADOR_ANUNCIOS);
        if (this.hayBloqueador == 'false') {
          this.obtenerAnuncios();
        }
    this.obtenerCosto();
  }

  obtenerSaldo(): void {
    if (this.codigoCine) {
      this.cineService.obtenerSaldoActual(this.codigoCine).subscribe({
        next: (saldoServer: number) => {
          this.saldo = saldoServer;
        },
        error: (error) => {
          console.log(error.error);
        }
      });
    }
  }

  obtenerAnuncios() {
    const indice = Number(localStorage.getItem(UserProperties.INDICE_ANUNCIO)) || 0;
    this.anuncioService.obtenerAnunciosParaMostrar(indice).subscribe({
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

  obtenerCosto():void{
    this.costoService.obtenerCostoBloqueo().subscribe({
      next:(costoServer:number)=>{
        this.costoBloqueo=costoServer;
      }
    });
  }
}