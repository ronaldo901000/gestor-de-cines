import { Component, Input, OnInit, ViewChild } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators
} from '@angular/forms';
import { ToastComponent } from '../../toast/toast.component';
import { CommonModule } from '@angular/common';
import { ProyeccionesServices } from '../../../services/proyeccion/proyeccion.services';
import { Proyeccion } from '../../../models/proyeccion/proyeccion';
import { ProyeccionResponse } from '../../../models/proyeccion/proyeccion-response';
@Component({
    standalone: true,
    selector: 'app-update-proyeccion-form-component',
    imports: [FormsModule, ReactiveFormsModule, ToastComponent, CommonModule],
    templateUrl: './update-proyeccion-form.component.html',
})
export class UpdateProyeccionFormComponent implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;
    proyeccionForm!: FormGroup;
    proyeccionUpdate!: Proyeccion;
    @Input()
    fechaMinima!: Date

    @Input()
    proyeccionActual!: ProyeccionResponse

    constructor(
        private formBuilder: FormBuilder, private proyeccionService: ProyeccionesServices) {

    }

    ngOnInit(): void {
        this.proyeccionForm = this.formBuilder.group({
            codigo: [this.proyeccionActual.codigo, [Validators.required, Validators.maxLength(25), Validators.pattern(/^[a-zA-Z0-9-ñ]+$/)]],
            codigoPelicula: [this.proyeccionActual.pelicula.codigo, [Validators.required, Validators.maxLength(25)]],
            codigoSala: [this.proyeccionActual.sala.codigo, [Validators.required, Validators.maxLength(25)]],
            fecha: [this.proyeccionActual.fecha, [Validators.required]],
            horaInicio: [this.proyeccionActual.horaInicio, [Validators.required]],
            horaFin: [this.proyeccionActual.horaFin, [Validators.required]],
            precio: [this.proyeccionActual.precio, [Validators.required, Validators.max(10000000), Validators.min(0)]],
        });
    }


    actualizar(): void {
        if (this.proyeccionForm.valid) {
            this.proyeccionUpdate = this.proyeccionForm.value as Proyeccion;
            this.proyeccionService.actualizarProyeccion(this.proyeccionUpdate).subscribe({
                next: () => {
                    this.toast.titulo = 'Exitoso';
                    this.toast.mensaje = 'Proyeccion de pelicula con codigo: "' + this.proyeccionUpdate.codigo + '" actualizada exitosamente!!! '
                    this.toast.tipo = 'success';
                    this.toast.mostrar();
                },
                error: (error) => {
                    this.toast.titulo = 'Error';
                    this.toast.tipo = 'danger';
                    console.log(error);
                    this.toast.mensaje = error.error;
                    this.toast.mostrar();
                }
            });
        }
    }


    reset(): void {
        this.proyeccionForm.reset({
            fecha: new Date().toISOString().substring(0, 10),
            precio: 1
        });
    }



}
