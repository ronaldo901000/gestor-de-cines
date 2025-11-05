import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from "@angular/forms";
import { ToastComponent } from '../../toast/toast.component';
import { FiltroComentariosSalas } from '../../../models/filtros-reportes-admin-cine/filtro-comentarios.-salas';
import { ReporteAdminCineServices } from '../../../services/reportes/reportes-admin-cine.services';
import { HttpClient } from '@angular/common/http';
import { Status } from '../../../shared/status/status';
@Component({
  selector: 'app-comentarios-de-salas-form-component',
  imports: [ToastComponent, FormsModule, ReactiveFormsModule],
  templateUrl: './comentarios-de-salas-form.component.html',
})
export class ComentariosDeSalasFormComponent implements OnInit {


  @ViewChild('toast') toast!: ToastComponent;
  @Input({ required: true })
  codigoCine!: String;
  nuevoFiltro!: FiltroComentariosSalas;
  filtroForm!: FormGroup;
  constructor(private httpClient: HttpClient, private formBuilder: FormBuilder, private reporteServices: ReporteAdminCineServices) { }
  ngOnInit(): void {
    this.inicializarForm();
  }

  generar(): void {
    if (this.filtroForm.valid) {
      this.nuevoFiltro = this.filtroForm.value as FiltroComentariosSalas;

      const url = this.reporteServices.generarURLReporteComentariosSalas(this.nuevoFiltro);

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
        this.toast.mensaje = 'Codigo de sala no registrado en el sistema';
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
      codigoCine: this.codigoCine,
    });
  }

  inicializarForm(): void {

    this.filtroForm = this.formBuilder.group({
      codigoCine: [this.codigoCine, [Validators.required]],
      codigoSala: [null],
      fechaInicio: [null],
      fechaFin: [null],
    });
  }
}
