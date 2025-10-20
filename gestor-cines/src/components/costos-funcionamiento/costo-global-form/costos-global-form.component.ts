import { Component, OnInit, ViewChild } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';
import { ToastComponent } from '../../toast/toast.component';
import { CostoGlobal } from '../../../models/costo-funcionamiento/costo-global';
import { CostoGlobalServices } from '../../../services/costo-funcionamiento/costo-global.services';
import { CurrencyPipe } from '@angular/common';
@Component({
    selector: 'app-costos-global-form-component',
    imports: [FormsModule, ReactiveFormsModule, ToastComponent, CurrencyPipe],
    templateUrl: './costo-global-form.component.html',
})
export class CostoGlobalFormComponent implements OnInit {

    @ViewChild('toast') toast!: ToastComponent;
    costoForm!: FormGroup;
    nuevoCosto!: CostoGlobal;
    costoActual!: CostoGlobal
    mensaje: string = '';

    constructor(private formBuilder: FormBuilder, private costoService: CostoGlobalServices) { }

    ngOnInit(): void {
        this.costoForm = this.formBuilder.group({
            costo: [null, [Validators.required, Validators.min(0), Validators.max(100000000)]],

        });
         this.traerCostoActual();
    }

    crear(): void {
        if (this.costoForm.valid) {
            this.nuevoCosto = this.costoForm.value as CostoGlobal;
            this.costoService.actualizarCosto(this.nuevoCosto).subscribe({
                next: () => {
                    this.traerCostoActual();
                    this.toast.titulo = 'Exitoso';
                    this.toast.mensaje = 'Costo global actualizado exitosamente!';
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
        this.costoService.obtenerCostoGlobal().subscribe({
            next: (costoServer: CostoGlobal) => {
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
