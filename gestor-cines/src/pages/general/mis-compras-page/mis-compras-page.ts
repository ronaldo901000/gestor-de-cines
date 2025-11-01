import { Component } from '@angular/core';
import { UserProperties } from '../../../shared/user/user-properties';
import { HeaderUsuarioNormalComponent } from "../../../components/header/header-usuario-normal/header-usuario-normal.component";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
import { CompraBoletosResponse } from '../../../models/compraBoletos/compra-boletos-response';
import { CompraBoletosServices } from '../../../services/compra-boletos/compra-boletos.services';
import { MisComprasTableComponent } from "../../../components/venta-boletos/mis-compras-table-component/mis-compras-table.component";

@Component({
  selector: 'app-mis-compras-page',
  imports: [HeaderUsuarioNormalComponent, HeaderAdminSistemaComponent, HeaderAdminCineComponent, AnuncioCardComponent, MisComprasTableComponent],
  templateUrl: './mis-compras-page.html',
  styleUrl: './mis-compras-page.css'
})
export class MisComprasPage {
  rol!: string | null;
  idUser!: string | null;
  saldoActual!: number;
  anuncios: AnuncioResponse[] = [];
  compras: CompraBoletosResponse[] = [];


  constructor(private anunciosServices: AnuncioServices,
    private comprasServices: CompraBoletosServices
  ) { }

  ngOnInit(): void {
    this.idUser = localStorage.getItem(UserProperties.ID);
    this.rol = localStorage.getItem(UserProperties.ROL);
    this.obtenerAnuncios();
  }

  obtenerAnuncios() {
    const indice = Number(localStorage.getItem(UserProperties.INDICE_ANUNCIO)) || 0;
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
