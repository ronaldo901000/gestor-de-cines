import { Component, OnInit } from '@angular/core';
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { Cine } from '../../../models/cine/cine';
import { CineServices } from '../../../services/cine/cine.services';
import { AdminCineProperties } from '../../../shared/user/admin-cine-properties';
import { RecargaCineComponent } from "../../../components/cine/recarga-saldo-component/recarga-cine.component";
import { CurrencyPipe } from '@angular/common';
@Component({
  selector: 'app-saldo-cine-page',
  imports: [HeaderAdminCineComponent, RecargaCineComponent, CurrencyPipe],
  templateUrl: './saldo-cine-page.html',
  styleUrl: './saldo-cine-page.css'
})
export class SaldoCinePage implements OnInit {

  cine!: Cine;
  codigoCine!: string | null;
  saldoActual!: number;

  constructor(private cineServices: CineServices) {

  }
  ngOnInit(): void {
    this.codigoCine = localStorage.getItem(AdminCineProperties.CODIGO_CINE);
    console.log('codigo cine: ' + this.codigoCine);
    this.obtenerCine();
    this.obtenerSaldo();
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
}

