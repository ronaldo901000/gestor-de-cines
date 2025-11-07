import { Component, ViewChild } from '@angular/core';
import { Status } from '../../../shared/status/status';
import { FiltroComentariosSalas } from '../../../models/filtros-reportes-admin-cine/filtro-comentarios.-salas';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastComponent } from '../../toast/toast.component';
import { HttpClient } from '@angular/common/http';
import { ReporteAdminSistemaServices } from '../../../services/reportes/reportes-admin-sistema.services';

@Component({
  selector: 'app-reporte-salas-populares-form-component',
  imports: [ToastComponent, FormsModule, ReactiveFormsModule],
  templateUrl: './reporte-salas-populares-form.component.html'
})
export class ReporteSalasPopularesFormComponent {
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

      const url = this.reporteServices.generarURLReporteSalasPopulares(this.nuevoFiltro);

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
