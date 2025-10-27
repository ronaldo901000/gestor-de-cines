import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { ToastComponent } from "../../toast/toast.component";
import { CompraBoletos } from '../../../models/compraBoletos/compra-boletos';
import { CompraBoletosServices } from '../../../services/compra-boletos/compra-boletos.services';

@Component({
  selector: 'app-boletos-form-component',
  imports: [ToastComponent, FormsModule, ReactiveFormsModule],
  templateUrl: './boletos-form.component.html',
  styleUrl: './boletos-form.component.css'
})
export class BoletosFormComponent implements OnInit {
  @ViewChild('toast')
  toast!: ToastComponent;

  compraForm!: FormGroup;

  @Input({ required: true })
  fechaMaxima!: Date;

  @Input({ required: true })
  idUsuario!: string;

  @Input({ required: true })
  codigoProyeccion!: string;

  @Input({ required: true })
  precioBoleto!: number;

  nuevaCompra!: CompraBoletos;

  @Output()
  actualizarVistaSaldo = new EventEmitter();

  constructor(
    private formBuilder: FormBuilder, private compraService: CompraBoletosServices) {

  }
  ngOnInit(): void {
    this.compraForm = this.formBuilder.group({
      idUsuario: [this.idUsuario, [Validators.required, Validators.maxLength(25)]],
      codigoProyeccion: [this.codigoProyeccion, [Validators.required, Validators.maxLength(25)]],
      fechaCompra: [this.fechaMaxima, [Validators.required, Validators.maxLength(25)]],
      cantidad: [1, [Validators.required, Validators.max(5), Validators.min(1)]],
    });
  }

  comprar(): void {
    if (this.compraForm.valid) {
      this.limpiarToast();
      const precio = this.precioBoleto;
      const cantidad = this.compraForm.get('cantidad')?.value;
      const total = precio * cantidad;
      const confirmado = confirm('¿El total es de "Q.' + total + '" Quieres confirmar la compra?');
      if (confirmado) {
        this.nuevaCompra = this.compraForm.value as CompraBoletos;
        this.compraService.crearNuevaCompra(this.nuevaCompra).subscribe({
          next: () => {
            console.log('compra exitosa');
            this.toast.titulo = 'Compra exitosa!'
            this.toast.tipo = 'success';
            this.toast.mensaje = 'Compra realizada con exito detalles:';
            this.toast.dato1 = 'Id del usuario: ' + this.idUsuario;
            this.toast.dato2 = 'Codigo de proyeccion: ' + this.codigoProyeccion;
            this.toast.dato3 = 'Fecha de compra: ' + this.nuevaCompra.fechaCompra;
            this.toast.dato4 = 'Cantidad de boletos: ' + this.nuevaCompra.cantidad;
            this.toast.dato5 = 'Costo total: Q.' + total;
            this.toast.mostrar();
            this.actualizarVistaSaldo.emit();
          },
          error: (error) => {
            this.toast.titulo = 'Error en la compra!'
            this.toast.tipo = 'danger';
            this.toast.mensaje =error.error;
            this.toast.mostrar();
          }
        });
      }
    }
  }

  limpiarToast() {
    this.toast.dato1 = '';
    this.toast.dato2 = '';
    this.toast.dato3 = '';
    this.toast.dato4 = '';
    this.toast.dato5 = '';
  }

}
