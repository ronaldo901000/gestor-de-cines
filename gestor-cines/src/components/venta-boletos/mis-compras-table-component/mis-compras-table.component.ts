import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { CompraBoletosResponse } from '../../../models/compraBoletos/compra-boletos-response';
import { ToastComponent } from '../../toast/toast.component';
import { RouterLink } from "@angular/router";
import { DatePipe, CurrencyPipe } from '@angular/common';
import { CompraBoletosServices } from '../../../services/compra-boletos/compra-boletos.services';

@Component({
  selector: 'app-mis-compras-table-component',
  imports: [ToastComponent, RouterLink, DatePipe, CurrencyPipe],
  templateUrl: './mis-compras-table.component.html',
  styleUrl: './mis-compras-table.component.css'
})
export class MisComprasTableComponent implements OnInit {
  @ViewChild
    ('toast') toast!: ToastComponent;

  compras: CompraBoletosResponse[] = [];
  @Input({ required: true })
  idUser!: string;
  inicio: number = 0;
  rango: number = 5;

  constructor(
    private comprasServices: CompraBoletosServices
  ) { }
  ngOnInit(): void {
    this.obtenerMisCompras();
  }

  obtenerMisCompras(): void {
    if (this.idUser) {
      this.comprasServices.obtenerMisCompras(this.idUser, this.inicio).subscribe({
        next: (comprasServer: CompraBoletosResponse[]) => {
          this.compras = comprasServer;
        },
        error: (error) => {
          console.log(error.error);
        }
      });
    }
  }
  retrocederUnaPagina(): void {
    this.inicio -= this.rango;
    this.obtenerMisCompras();
  }

  avanzarUnaPagina(): void {
    this.inicio += this.rango;
    this.obtenerMisCompras();
  }

  hayMasCompras(): boolean {
    if(this.compras.length<5){
      return false;
    }
    return true;
  }

}
