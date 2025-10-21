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

@Component({
    selector: 'app-sala-form',
    imports: [FormsModule, ReactiveFormsModule, ToastComponent],
    templateUrl: './sala-form.component.html',
})
export class SalaFormComponent implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;
    salaForm!: FormGroup;
    nuevaSala!: Sala;

    @Input()
    codigoCineRecibido!: string;
    constructor(private formBuilder: FormBuilder, private salasServices: SalasServices) { }

    ngOnInit(): void {
        this.salaForm = this.formBuilder.group({
            codigo: [null, [Validators.required, Validators.maxLength(25)]],
            codigoCine: [this.codigoCineRecibido, [Validators.required, Validators.maxLength(25)]],
            nombre: [null, [Validators.required, Validators.maxLength(100)]],
            filas: [1, [Validators.required, Validators.max(25)]],
            columnas: [1, [Validators.required, Validators.max(25)]],
        });

    }

    crear(): void {
        if (this.salaForm.valid) {
            this.nuevaSala = this.salaForm.value as Sala
        
            this.salasServices.crearSala(this.nuevaSala).subscribe({
                next: () => {
                    this.toast.titulo = 'Sala creada';
                    this.toast.tipo = 'success';
                    this.toast.mensaje = 'Sala creada exitosamante';
                    this.toast.mostrar();
                    this.reset();
                },
                error: (error: any) => {
                    this.toast.titulo = 'Error en creacion';
                    this.toast.tipo = 'danger';
                    this.toast.mensaje = error.error;
                    this.toast.mostrar();
                }
            });
        }

    }

    reset(): void {
        this.salaForm.reset({
            codigoCine: this.codigoCineRecibido
        });
    }
}