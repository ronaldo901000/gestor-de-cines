import { Component, OnInit } from '@angular/core';
import { UserProperties } from '../../../shared/user/user-properties';
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { ActualizacionCuentaFormComponent } from "../../../components/cuenta/actualizacion-cuenta-form-component/actualizacion-cuenta-form.component";
import { Usuario } from '../../../models/usuario/usuario';
import { UsuarioServices } from '../../../services/usuario/usuario.services';
import { HeaderUsuarioNormalComponent } from "../../../components/header/header-usuario-normal/header-usuario-normal.component";
import { AnuncioResponse } from '../../../models/anuncio/anuncio-response';
import { AnuncioServices } from '../../../services/anuncio/anuncio.services';
import { AnuncioCardComponent } from "../../../components/anuncio/anuncio-card/anuncio-card.component";

@Component({
  selector: 'app-actualizacion-cuenta-page.component',
  imports: [HeaderAdminCineComponent, HeaderAdminSistemaComponent, ActualizacionCuentaFormComponent, HeaderUsuarioNormalComponent, AnuncioCardComponent],
  templateUrl: './actualizacion-cuenta-page.component.html',
  styleUrl: './actualizacion-cuenta-page.component.css'
})
export class ActualizacionCuentaPageComponent implements OnInit {

  idUsuario!: string | null;
  rol!: string | null;
  usuarioActual!: Usuario;
  anuncios: AnuncioResponse[] = [];

  constructor(private usuarioServices: UsuarioServices, private anunciosServices: AnuncioServices) {

  }
  ngOnInit(): void {
    this.idUsuario = localStorage.getItem(UserProperties.ID);
    this.rol = localStorage.getItem(UserProperties.ROL);
    this.obtenerUsuario();
    this.obtenerAnuncios();
  }

  obtenerUsuario(): void {
    if (this.idUsuario) {
      this.usuarioServices.obtenerUsuario(this.idUsuario).subscribe({
        next: (usuarioServer: Usuario) => {
          this.usuarioActual = usuarioServer;
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
