import { Component, OnInit } from '@angular/core';
import { UserProperties } from '../../../shared/user/user-properties';
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { ActualizacionCuentaFormComponent } from "../../../components/cuenta/actualizacion-cuenta-form-component/actualizacion-cuenta-form.component";
import { Usuario } from '../../../models/usuario/usuario';
import { UsuarioServices } from '../../../services/usuario/usuario.services';

@Component({
  selector: 'app-actualizacion-cuenta-page.component',
  imports: [HeaderAdminCineComponent, HeaderAdminSistemaComponent, ActualizacionCuentaFormComponent],
  templateUrl: './actualizacion-cuenta-page.component.html',
  styleUrl: './actualizacion-cuenta-page.component.css'
})
export class ActualizacionCuentaPageComponent implements OnInit {

  idUsuario!: string | null;
  rol!: string | null;
  usuarioActual!: Usuario;
  constructor(private usuarioServices: UsuarioServices) {

  }
  ngOnInit(): void {
    this.idUsuario = localStorage.getItem(UserProperties.ID);
    this.rol = localStorage.getItem(UserProperties.ROL);
    this.obtenerUsuario();
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
}
