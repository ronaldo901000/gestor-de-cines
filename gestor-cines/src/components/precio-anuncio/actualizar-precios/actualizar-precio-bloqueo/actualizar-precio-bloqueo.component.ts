import { Component, ViewChild } from '@angular/core';
import { ToastComponent } from '../../../toast/toast.component';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CostoGlobalServices } from '../../../../services/costo-funcionamiento/costo-global.services';
import { CurrencyPipe } from '@angular/common';
import { CostoBloqueo } from '../../../../services/precio-anuncio/costo-bloqueo';
@Component({
  selector: 'app-actualizar-precio-bloqueo-component',
  imports: [ToastComponent, CurrencyPipe, FormsModule, ReactiveFormsModule],
  templateUrl: './actualizar-precio-bloqueo.component.html',
  styleUrl: './actualizar-precio-bloqueo.component.css'
})
export class ActualizarPrecioBloqueoComponent {

  @ViewChild('toast') toast!: ToastComponent;
  costoForm!: FormGroup;
  nuevoCosto!: CostoBloqueo;
  costoActual!: number
  mensaje: string = '';

  constructor(private formBuilder: FormBuilder, private costoService: CostoGlobalServices) { }

  ngOnInit(): void {
    this.costoForm = this.formBuilder.group({
      costoBloqueo: [null, [Validators.required, Validators.min(0), Validators.max(10000000)]],

    });
    this.traerCostoActual();
  }

  crear(): void {
    if (this.costoForm.valid) {
      this.nuevoCosto = this.costoForm.value as CostoBloqueo;
      this.costoService.actualizarCostoBloqueo(this.nuevoCosto).subscribe({
        next: () => {
          this.traerCostoActual();
          this.toast.titulo = 'Exitoso';
          this.toast.mensaje = 'Costo de bloqueo de anuncios actualizado exitosamente!';
          this.toast.tipo = 'success';
          this.toast.mostrar();
          this.reset();
        },
        error: (error) => {
          this.toast.titulo = 'Error';
          this.toast.tipo = 'danger';
          this.toast.mensaje = error.error;
          console.log(error);
          this.toast.mostrar();
        },
      });
    }
  }

  traerCostoActual() {
    this.costoService.obtenerCostoBloqueo().subscribe({
      next: (costoServer: number) => {
        this.costoActual = costoServer;
      },
      error: (error) => {
        this.toast.titulo = 'Error al obtener costo';
        this.toast.tipo = 'danger';
        this.toast.mensaje = error.error;
        this.toast.mostrar();
      }
    });
  }
  reset(): void {
    this.costoForm.reset({
      fechaRegistro: new Date().toISOString().substring(0, 10),
    });
  }





}
