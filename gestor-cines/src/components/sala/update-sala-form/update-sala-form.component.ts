import { Component, Input, OnInit, ViewChild } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';
import { ToastComponent } from '../../toast/toast.component';
import { SalasServices } from '../../../services/sala/sala.services';
import { Sala } from '../../../models/sala/sala';
import { SalaResponse } from '../../../models/sala/sala-response';

@Component({
    selector: 'app-update-sala-form',
    imports: [FormsModule, ReactiveFormsModule, ToastComponent],
    templateUrl: './update-sala-form.component.html',
})
export class UpdateSalaFormComponent implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;
    salaForm!: FormGroup;
    nuevaSala!: Sala;

    @Input()
    salaCargada!: SalaResponse;


    constructor(private formBuilder: FormBuilder, private salasServices: SalasServices) { }

    ngOnInit(): void {
        this.salaForm = this.formBuilder.group({
            codigo: [this.salaCargada.codigo, [Validators.required, Validators.maxLength(25)]],
            codigoCine: [this.salaCargada.codigoCine, [Validators.required, Validators.maxLength(25)]],
            nombre: [this.salaCargada.nombre, [Validators.required, Validators.maxLength(100)]],
            filas: [this.salaCargada.filas, [Validators.required, Validators.max(25)]],
            columnas: [this.salaCargada.columnas, [Validators.required, Validators.max(25)]],
        });

    }

    crear(): void {
        if (this.salaForm.valid) {
            this.nuevaSala = this.salaForm.value as Sala

            this.salasServices.actualizarSala(this.nuevaSala).subscribe({
                next: () => {
                    this.toast.titulo = 'Sala Actualizada';
                    this.toast.tipo = 'success';
                    this.toast.mensaje = 'Sala actualizada exitosamante';
                    this.toast.mostrar();
                },
                error: (error: any) => {
                    this.toast.titulo = 'Error en Actualizacion';
                    this.toast.tipo = 'danger';
                    this.toast.mensaje = error.error;
                    this.toast.mostrar();
                }
            });
        }

    }

    reset(): void {
        this.salaForm.reset({
            codigoCine: this.salaCargada.codigoCine,
            codigo:this.salaCargada.codigo
        });
    }

}