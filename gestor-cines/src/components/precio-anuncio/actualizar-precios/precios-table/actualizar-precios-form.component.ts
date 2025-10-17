import { Component, OnInit, ViewChild } from '@angular/core';
import { PrecioAnunciosServices } from '../../../../services/precio-anuncio/precio-anuncio.services';
import { PrecioAnuncio } from '../../../../models/precio-anuncio/precio-anuncio';
import { ToastComponent } from '../../../toast/toast.component';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Status } from '../../../../shared/status/status';
@Component({
  selector: 'app-actualizar-precios-form-component',
  imports: [ToastComponent, FormsModule, ReactiveFormsModule],
  templateUrl: './actualizar-precios-form.component.html',
  styleUrl: './actualizar-precios-form.component.css'
})
export class ActualizarPreciosFormComponent implements OnInit {
  @ViewChild('toast') toast!: ToastComponent;

  idPrecioAnuncio!: number;
  precioAnuncio!: PrecioAnuncio;
  actualizarPreciosForm!: FormGroup;

  precioAnuncioRecoletado!: PrecioAnuncio

  constructor(
    private precioAnuncioService: PrecioAnunciosServices,
    private formBuilder: FormBuilder) {

  }


actualizar() {
  if (this.actualizarPreciosForm.valid) {
    const formValue = this.actualizarPreciosForm.value;

    const precioActualizado: PrecioAnuncio = {
      id: formValue.id,
      tipo: formValue.tipo,
      precioVentaPorDia: formValue.precioVenta,
      precioBloqueoPorDia: formValue.precioBloqueo
    };

    this.precioAnuncioService.actualizarPrecios(precioActualizado).subscribe({
      next: (respuesta: PrecioAnuncio) => {
        this.toast.titulo = 'Actualizacion exitosa';
        this.toast.tipo = 'success';
        this.toast.mensaje = 'Actualizacion realizada con exito, ' +
          'nuevo precio de venta: "Q.' + respuesta.precioVentaPorDia +
           '" nuevo precio de bloqueo: "Q.' + respuesta.precioBloqueoPorDia + '"';
        this.toast.mostrar();
      },
      error: (error) => {
        this.toast.titulo = 'Error al actualizar';
        this.toast.tipo = 'danger';
        if (error.status == Status.BAD_REQUEST) {
          this.toast.mensaje = 'Error en los datos enviados';
        }
        else if (error.status == Status.NOT_FOUND) {
          this.toast.mensaje = 'El id enviado no esta registrado'
        }
        else if (error.status == Status.INTERNAL_SERVER_ERROR) {
          this.toast.mensaje = 'Ocurrio un error al actualizar en la base de datos';
        }
        this.toast.mostrar();
      }
    });
  }
}


  ngOnInit(): void {
    this.idPrecioAnuncio = history.state['idPrecioAnuncio'];

    this.actualizarPreciosForm = this.formBuilder.group({
      id: ['', [Validators.required, Validators.maxLength(10)]],
      tipo: ['', [Validators.required, Validators.maxLength(100)]],
      precioVenta: ['', [Validators.required, Validators.min(0), Validators.max(999999999)]],
      precioBloqueo: ['', [Validators.required, Validators.min(0), Validators.max(999999999)]],
    });
    this.obtenerPreciosAnuncio();
  }

  obtenerPreciosAnuncio() {
    this.precioAnuncioService.traerPrecios().subscribe({
      next: (preciosServer: PrecioAnuncio[]) => {
        for (let i = 0; i < preciosServer.length; i++) {
          if (preciosServer[i].id === this.idPrecioAnuncio) {
            this.precioAnuncio = preciosServer[i];
            this.llenarCampos();
            break;
          }

        }
      },
      error: (err) => {
        console.error('Error al traer precios', err);
      }
    });
  }

  llenarCampos() {
    this.actualizarPreciosForm.patchValue({
      id: this.precioAnuncio.id,
      tipo: this.precioAnuncio.tipo,
      precioVenta: this.precioAnuncio.precioVentaPorDia,
      precioBloqueo: this.precioAnuncio.precioBloqueoPorDia,
    });
  }
}



