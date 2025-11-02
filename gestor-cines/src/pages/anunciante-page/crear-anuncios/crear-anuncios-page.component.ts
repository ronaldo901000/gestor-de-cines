import { Component, Input, OnInit } from "@angular/core";
import { ActivatedRoute, RouterLink } from "@angular/router";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { AnuncioFormComponent } from "../../../components/anuncio/anuncio-form/anuncio-form.component";
import { PrecioAnunciosServices } from "../../../services/precio-anuncio/precio-anuncio.services";
import { PrecioAnuncio } from "../../../models/precio-anuncio/precio-anuncio";
import { CurrencyPipe } from "@angular/common";
import { UserProperties } from "../../../shared/user/user-properties";
import { TiposAnuncio } from "../../../shared/tipo-anuncio/tipo-anuncio";
import { UsuarioServices } from "../../../services/usuario/usuario.services";
import { HeaderUsuarioNormalComponent } from "../../../components/header/header-usuario-normal/header-usuario-normal.component";
import { AnuncioServices } from "../../../services/anuncio/anuncio.services";
import { AnuncioResponse } from "../../../models/anuncio/anuncio-response";
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";
@Component({
  selector: 'app-crear-anuncios-page',
  imports: [RouterLink, HeaderAdminCineComponent, HeaderAdminSistemaComponent, AnuncioFormComponent, CurrencyPipe, HeaderUsuarioNormalComponent, AnuncioCardComponent],
  templateUrl: './crear-anuncios-page.component.html',
})


export class CrearAnunciosComponentPage implements OnInit {


  rol!: string | null;
  idUser!: string | null;
  precio!: number;
  miSaldo!: number;
  tipoAnuncio!: string;
  anuncios: AnuncioResponse[] = [];

  constructor(
    private router: ActivatedRoute,
    private preciosService: PrecioAnunciosServices,
    private userServices: UsuarioServices,
    private anunciosServices: AnuncioServices) { }

  ngOnInit(): void {
    this.idUser = localStorage.getItem(UserProperties.ID);
    this.rol = localStorage.getItem(UserProperties.ROL);
    this.tipoAnuncio = this.router.snapshot.params['tipo'];
    this.obtenerPrecio();
    this.obtenerMiSaldoActual();
    this.obtenerAnuncios();
  }

  obtenerPrecio() {
    this.preciosService.traerPrecios().subscribe({
      next: (precios: PrecioAnuncio[]) => {
        if (this.tipoAnuncio == TiposAnuncio.TEXTO) {
          this.precio = precios[0].precioVentaPorDia;
        }
        else if (this.tipoAnuncio == TiposAnuncio.IMAGEN) {
          this.precio = precios[1].precioVentaPorDia;
        }
        else if (this.tipoAnuncio == TiposAnuncio.VIDEO) {
          this.precio = precios[2].precioVentaPorDia;
        }
      }
    });
  }

  obtenerMiSaldoActual() {
    if (this.idUser) {
      this.userServices.obtenerSaldoActual(this.idUser).subscribe({
        next: (saldoServer: number) => {
          this.miSaldo = saldoServer;
        },
        error: (error) => {
          console.log(error.error);
        }
      });
    }
  }

  obtenerAnuncios(): void {
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