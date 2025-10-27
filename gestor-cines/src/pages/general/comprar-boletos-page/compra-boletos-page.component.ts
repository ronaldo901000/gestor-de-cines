import { Component, Input, OnInit } from '@angular/core';
import { UserProperties } from '../../../shared/user/user-properties';
import { HeaderAdminSistemaComponent } from "../../../components/header/header-admin-sistema/header-admin-sistema.component";
import { HeaderAdminCineComponent } from "../../../components/header/header-admin-cine/header-admin-cine.component";
import { HeaderUsuarioNormalComponent } from "../../../components/header/header-usuario-normal/header-usuario-normal.component";
import { ProyeccionResponse } from '../../../models/proyeccion/proyeccion-response';
import { ProyeccionesServices } from '../../../services/proyeccion/proyeccion.services';
import { UsuarioServices } from '../../../services/usuario/usuario.services';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { BoletosFormComponent } from "../../../components/venta-boletos/boletos-form.component/boletos-form.component";
@Component({
  selector: 'app-compra-boletos-page.component',
  imports: [HeaderAdminSistemaComponent, DatePipe,HeaderAdminCineComponent, HeaderUsuarioNormalComponent, CurrencyPipe, BoletosFormComponent, RouterLink],
  templateUrl: './compra-boletos-page.component.html',
  styleUrl: './compra-boletos-page.component.css'
})
export class CompraBoletosPageComponent implements OnInit {

  rol!: string | null;
  id!: string | null;
  saldoActual!: number;
  codigoProyeccion!: String;
  proyeccion!: ProyeccionResponse

  constructor(private proyeccionService: ProyeccionesServices,
    private usuarioServices: UsuarioServices,
    private router: ActivatedRoute
  ) {

  }

  ngOnInit(): void {
    this.rol = localStorage.getItem(UserProperties.ROL);
    this.codigoProyeccion = this.router.snapshot.params['codigo-proyeccion'];
    this.obtenerProyeccion();
    this.id = localStorage.getItem(UserProperties.ID);
    this.obtenerSaldoActual();
    
  }

  obtenerProyeccion(): void {
    this.proyeccionService.obtenerProyecion(this.codigoProyeccion).subscribe({
      next: (proyeccionServer: ProyeccionResponse) => {
        this.proyeccion = proyeccionServer;
      },
      error: (error) => {
        console.log(error.error);
      }

    });
  }

  obtenerSaldoActual(): void {
    if (this.id) {
      this.usuarioServices.obtenerSaldoActual(this.id).subscribe({
        next: (saldoServer: number) => {
          this.saldoActual = saldoServer;
        },
        error: (error) => {
          console.log(error.error);
        }

      });
    }
  }
}
