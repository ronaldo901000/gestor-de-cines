import { Component, OnInit } from '@angular/core';
import { PrecioAnuncio } from '../../../models/precio-anuncio/precio-anuncio';
import { PrecioAnunciosServices } from '../../../services/precio-anuncio/precio-anuncio.services';
import { ToastComponent } from '../../toast/toast.component';
import { CurrencyPipe } from '@angular/common';
import { RouterLink } from "@angular/router";
@Component({
  selector: 'app-precios-table-component',
  imports: [ToastComponent, CurrencyPipe, RouterLink],
  templateUrl: './precios-table.component.html',
  styleUrl: './precios-table.component.css'
})
export class PreciosTableComponent implements OnInit {

  preciosAnuncios: PrecioAnuncio[] = [];
  toast!:ToastComponent
  constructor(private preciosService:PrecioAnunciosServices){

  }
  ngOnInit(): void {
    this.cargarAnuncios();
  }

  cargarAnuncios(){
    this.preciosService.traerPrecios().subscribe({
      next:(preciosServer:PrecioAnuncio[])=>{
        this.preciosAnuncios=preciosServer;
      },
      error:(error)=>{
        this.toast.titulo='Error al obtener precios';
        this.toast.tipo='danger';
        this.toast.mensaje='Ocurrio un erro al obtener los precios';
        this.toast.mostrar();
      }
    });
  }
}
