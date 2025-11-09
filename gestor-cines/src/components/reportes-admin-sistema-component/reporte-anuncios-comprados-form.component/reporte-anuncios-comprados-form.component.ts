import { Component, OnInit, ViewChild } from '@angular/core';
import { ToastComponent } from '../../toast/toast.component';
import { FormGroup, FormsModule, ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { FiltroAnunciosComprados } from '../../../models/filtros-reportes-admin-cine/filtro-anunciosComprados';
import { ReporteAdminSistemaServices } from '../../../services/reportes/reportes-admin-sistema.services';
import { HttpClient } from '@angular/common/http';
import { Status } from '../../../shared/status/status';

@Component({
  selector: 'app-reporte-anuncios-comprados-form-component',
  imports: [ToastComponent, FormsModule, ReactiveFormsModule],
  templateUrl: './reporte-anuncios-comprados-form.component.html',
})
export class ReporteAnunciosCompradosFormComponent implements OnInit {
  @ViewChild('toast') toast!: ToastComponent;
  nuevoFiltro!: FiltroAnunciosComprados;
  URL!: string;
  constructor(private formBuilder: FormBuilder, private reporteService: ReporteAdminSistemaServices, private httpClient: HttpClient) { }
  ngOnInit(): void {
    this.filtroForm = this.formBuilder.group({
      tipoAnuncio: [null],
      periodoTiempo: [null],
      fechaInicio: [null],
      fechaFin: [null],
    });
  }

  filtroForm!: FormGroup;

  generar(): void {
    this.nuevoFiltro = this.filtroForm.value as FiltroAnunciosComprados;
    this.URL = this.reporteService.generarURLReporteAnunciosComprados(this.nuevoFiltro);
    this.httpClient.get(this.URL, { responseType: 'blob' }).subscribe({
      next: () => {
        window.location.href = this.URL;
      },
      error: (error) => {
        this.mostrarError(error.status);
      }
    });
  }

  limpiar(): void {
    this.filtroForm.reset({

    });
  }

  mostrarError(status: number): void {
    this.toast.tipo = 'danger';
    switch (status) {
      case Status.BAD_REQUEST:
        this.toast.mensaje = 'Error en los datos enviados';
        break;
      case Status.NOT_FOUND:
        this.toast.mensaje = 'El ID ingresado no pertenece a ningun anunciante';
        break;
      case Status.INTERNAL_SERVER_ERROR:
        this.toast.mensaje = 'Error del servidor';
        break;
      default:
        this.toast.mensaje = 'Error al generar el reporte';
    }
    this.toast.mostrar();
  }

}
