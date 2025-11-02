import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { ToastComponent } from "../../toast/toast.component";
import { AdminCinesServices } from '../../../services/admin-cine/admin-cine.services';
import { PagoBloqueo } from '../../../models/pago-bloqueo/pago-bloqueo';

@Component({
  selector: 'app-bloqueo-anuncio-form-component',
  imports: [ToastComponent, FormsModule, ReactiveFormsModule],
  templateUrl: './bloqueo-anuncio-form.component.html',
  styleUrl: './bloqueo-anuncio-form.component.css'
})
export class BloqueoAnuncioFormComponent implements OnInit {
  @ViewChild('toast')
  toast!: ToastComponent;
  @Input({ required: true })
  codigoCine!: string;
  compraForm!: FormGroup;

  @Input({ required: true })
  costoPorDia!: number;

  @Output()
  actualizarVistaSaldo = new EventEmitter();

  pago!: PagoBloqueo;
  constructor(
    private formBuilder: FormBuilder, private adminCineService: AdminCinesServices) {

  }
  ngOnInit(): void {
    this.compraForm = this.formBuilder.group({
      codigoCine: [this.codigoCine, [Validators.required, Validators.maxLength(25)]],
      fechaPago: [null, [Validators.required, Validators.maxLength(25)]],
      totalDias: [1, [Validators.required, Validators.max(30), Validators.min(1)]],
    });
  }


  comprar(): void {
    if (this.compraForm.valid) {
      const precio = this.costoPorDia;
      const cantidad = this.compraForm.get('totalDias')?.value;
      const total = precio * cantidad;
      const confirmado = confirm('¿El total es de "Q.' + total + '" Quieres confirmar la compra?');
      if (confirmado) {
        this.limpiar();
        this.pago = this.compraForm.value as PagoBloqueo;
        this.adminCineService.pagarBloqueoAnuncio(this.pago).subscribe({
          next: () => {
            this.toast.titulo = 'Pago exitoso';
            this.toast.tipo = 'success';
            this.toast.mensaje = 'Pago de bloqueo realizado exitosamente';
            this.toast.dato1 = 'Dias totales: ' + this.pago.totalDias;
            this.toast.dato2 = 'Fecha de pago: ' + this.pago.fechaPago;
            this.toast.dato3 = 'Costo total: Q.' + total;
            this.toast.mostrar();
            this.actualizarVistaSaldo.emit();
          },
          error: (error) => {
            this.toast.titulo = 'Error';
            this.toast.tipo = 'danger';
            this.toast.mensaje = error.error;
            this.toast.mostrar();
          }
        });
      }
    }
  }

  limpiar(): void {
    this.toast.dato1 = '';
    this.toast.dato2 = '';
    this.toast.dato3 = '';
  }
}
