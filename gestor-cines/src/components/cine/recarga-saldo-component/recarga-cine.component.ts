import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastComponent } from '../../toast/toast.component';
import { } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LimiteRecargas } from '../../../shared/recarga/recarga-const';
import { CineServices } from '../../../services/cine/cine.services';
import { RecargaCine } from '../../../models/recarga/recarga-cine';

@Component({
  selector: 'app-recarga-cine-component',
  imports: [FormsModule, ReactiveFormsModule, ToastComponent, CommonModule],
  templateUrl: './recarga-cine.component.html',
  styleUrl: './recarga-cine.component.css'
})
export class RecargaCineComponent implements OnInit {

  @ViewChild('toast')
  toast!: ToastComponent;
  @Input()
  codigoCine!: string

    @Output()
    actualizarVistaSaldo=new EventEmitter<void>();

  maximo: number = LimiteRecargas.MAXIMO;
  minimo: number = LimiteRecargas.MINIMO;

  recargaForm!: FormGroup;
  nuevaRecarga!: RecargaCine;
  constructor(
    private formBuilder: FormBuilder, private cineServices: CineServices) {


  }

  ngOnInit(): void {
    this.recargaForm = this.formBuilder.group({
      codigoCine: [this.codigoCine, [Validators.required]],
      monto: [1, [Validators.required, Validators.min(this.minimo), Validators.max(this.maximo)]],
    });
  }


  recargar(): void {
    if (this.recargaForm.valid) {
      this.nuevaRecarga = this.recargaForm.value as RecargaCine;
      this.cineServices.recargarSaldo(this.nuevaRecarga).subscribe({
        next: (nuevoSaldo: number) => {
          this.toast.titulo = 'Recarga exitosa!';
          this.toast.tipo = 'success';
          this.toast.mensaje = 'Recarga realizada exitosamente';
          this.toast.dato1 = 'Nuevo saldo: Q.' + nuevoSaldo;
          this.toast.mostrar();
          this.reset();
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


  reset(): void {
    this.recargaForm.reset({
      codigoCine: this.codigoCine,
      monto: 1
    });
  }

  resetToast(): void {
    this.toast.dato1 = '';
  }
}
