import { Component, OnInit, ViewChild } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';
import { ToastComponent } from '../../toast/toast.component';
import { CostoFuncionamiento } from '../../../models/costo-funcionamiento/costo-funcionamiento';
import { CostoFuncionamientoServices } from '../../../services/costo-funcionamiento/costo-funcionamiento.services';
@Component({
    selector: 'app-costos-cine-form-component',
    imports: [FormsModule, ReactiveFormsModule, ToastComponent],
    templateUrl: './costos-form.component.html',
})
export class CostosCineFormComponent implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;
    costoForm!: FormGroup;
    nuevoCosto!: CostoFuncionamiento;
    mensaje: string = '';
    constructor(private formBuilder: FormBuilder, private costoService: CostoFuncionamientoServices) { }

    ngOnInit(): void {
        this.costoForm = this.formBuilder.group({
            codigoCine: [null, [Validators.required, Validators.maxLength(25)]],
            fechaRegistro: [new Date().toISOString().substring(0, 10), [Validators.required]],
            costo: [null, [Validators.required, Validators.min(0), Validators.max(100000000)]],

        });
    }

    crear(): void {
        if (this.costoForm.valid) {
            this.nuevoCosto = this.costoForm.value as CostoFuncionamiento;
            this.costoService.crearNuevoCosto(this.nuevoCosto).subscribe({
                next: () => {
                    this.toast.titulo = 'Exitoso';
                    this.toast.mensaje = 'Costo creado exitosamente!!! ';
                    this.toast.tipo = 'success';
                    this.toast.mostrar();
                    this.reset();
                },
                error: (error) => {
                    this.toast.titulo = 'Error';
                    this.toast.tipo = 'danger';
                    this.toast.mensaje=error.error;
                    console.log(error);
                    this.toast.mostrar();
                },
            });
            console.log(this.nuevoCosto);
        }
    }

    reset(): void {
        this.costoForm.reset({
            fechaRegistro: new Date().toISOString().substring(0, 10),
        });
    }
}
