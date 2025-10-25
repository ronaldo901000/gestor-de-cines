import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators
} from '@angular/forms';
import { ToastComponent } from '../toast/toast.component';
import { CommonModule } from '@angular/common';
import { UsuarioServices } from '../../services/usuario/usuario.services';
import { Recarga } from '../../models/recarga/recarga';
@Component({
    standalone: true,
    selector: 'app-recarga-form-component',
    imports: [FormsModule, ReactiveFormsModule, ToastComponent, CommonModule],
    templateUrl: './recarga-form.component.html',
})
export class RecargaFormComponent implements OnInit {
    @ViewChild('toast')
    toast!: ToastComponent;

    @Input()
    idUsuario!: string;

    @Output()
    actualizarVistaSaldo=new EventEmitter<void>();

    recargaForm!: FormGroup;
    nuevaRecarga!: Recarga;

    constructor(
        private formBuilder: FormBuilder, private userServices: UsuarioServices) {

    }

    ngOnInit(): void {
        this.recargaForm = this.formBuilder.group({
            idUsuario: [this.idUsuario, [Validators.required]],
            monto: [1, [Validators.required, Validators.min(1), Validators.max(50000)]],
        });
    }


    recargar(): void {
        if (this.recargaForm.valid) {
            this.resetToast();
            this.nuevaRecarga = this.recargaForm.value as Recarga
            this.userServices.recargarCartera(this.nuevaRecarga).subscribe({

                next: (nuevoSaldo: number) => {
                    const saldo=nuevoSaldo;
                    this.toast.titulo = 'Recarga Exitosa';
                    this.toast.tipo = 'warning';
                    this.toast.mensaje = 'Recarga realizada exitosamente';
                    this.toast.dato1 = 'Saldo Actual: Q.' + saldo;
                    this.toast.mostrar();
                    this.actualizarVistaSaldo.emit();
                    this.reset();
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
            idUsuario: this.idUsuario,
            monto: 1
        });
    }

    resetToast(): void {
        this.toast.dato1 = '';
    }


}
