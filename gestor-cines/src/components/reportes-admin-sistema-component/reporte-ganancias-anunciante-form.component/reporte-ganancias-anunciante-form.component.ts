import { Component, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Status } from '../../../shared/status/status';
import { FiltroComentariosSalas } from '../../../models/filtros-reportes-admin-cine/filtro-comentarios.-salas';
import { HttpClient } from '@angular/common/http';
import { ToastComponent } from '../../toast/toast.component';
import { ReporteAdminCineServices } from '../../../services/reportes/reportes-admin-cine.services';
import { ReporteAdminSistemaServices } from '../../../services/reportes/reportes-admin-sistema.services';

@Component({
  selector: 'app-reporte-ganancias-anunciante-form-component',
  imports: [ToastComponent, FormsModule, ReactiveFormsModule],
  templateUrl: './reporte-ganancias-anunciante-form.component.html'
})
export class ReporteGananciasAnuncianteFormComponent {
  @ViewChild('toast') toast!: ToastComponent;
  nuevoFiltro!: FiltroComentariosSalas;
  filtroForm!: FormGroup;
  constructor(private httpClient: HttpClient, private formBuilder: FormBuilder, private reporteServices: ReporteAdminSistemaServices) { }
  ngOnInit(): void {
    this.inicializarForm();
  }

  generar(): void {
    if (this.filtroForm.valid) {
      this.nuevoFiltro = this.filtroForm.value as FiltroComentariosSalas;

      const url = this.reporteServices.generarURLReporteGananciasPorAnunciante(this.nuevoFiltro);

      this.httpClient.get(url, { responseType: 'blob' }).subscribe({
        next: () => {
          window.location.href = url;
        },
        error: (error) => {
          this.mostrarError(error.status);
        }
      });
    }
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

  limpiar(): void {
    this.filtroForm.reset({

    });
  }

  inicializarForm(): void {

    this.filtroForm = this.formBuilder.group({
      codigoSala: [null],
      fechaInicio: [null],
      fechaFin: [null],
    });
  }
}
