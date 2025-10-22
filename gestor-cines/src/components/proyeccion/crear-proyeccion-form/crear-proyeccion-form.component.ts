import { Component, Input, OnInit, ViewChild } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators
} from '@angular/forms';
import { ToastComponent } from '../../toast/toast.component';
import { ClasificacionTypeEnum } from '../../../models/pelicula/pelicula-type-enums';
import { CommonModule } from '@angular/common';
import { ProyeccionesServices } from '../../../services/proyeccion/proyeccion.services';
import { Proyeccion } from '../../../models/proyeccion/proyeccion';
@Component({
    standalone: true,
    selector: 'app-crear-proyeccion-form-component',
    imports: [FormsModule, ReactiveFormsModule, ToastComponent, CommonModule],
    templateUrl: './crear-proyeccion-form.component.html',
})
export class CrearProyeccionFormComponent implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;
    proyeccionForm!: FormGroup;
    nuevaProyeccion!: Proyeccion;
    @Input()
    fechaMinima!: Date
    constructor(
        private formBuilder: FormBuilder, private proyeccionService: ProyeccionesServices) {

    }

    ngOnInit(): void {
        this.proyeccionForm = this.formBuilder.group({
            codigo: [null, [Validators.required, Validators.maxLength(25), Validators.pattern(/^[a-zA-Z0-9-ñ]+$/)]],
            codigoPelicula: [null, [Validators.required, Validators.maxLength(25)]],
            codigoSala: [null, [Validators.required, Validators.maxLength(25)]],
            fecha: [new Date().toISOString().substring(0, 10), [Validators.required]],
            horaInicio: [null, [Validators.required]],
            horaFin: [null, [Validators.required]],
            precio: [1, [Validators.required, Validators.max(10000000), Validators.min(0)]],
        });
    }


    crear(): void {
        if (this.proyeccionForm.valid) {
            this.nuevaProyeccion = this.proyeccionForm.value as Proyeccion;
            this.proyeccionService.crearProyeccion(this.nuevaProyeccion).subscribe({
                next: () => {
                    this.toast.titulo = 'Exitoso';
                    this.toast.mensaje = 'Proyeccion de pelicula con codigo: "' + this.nuevaProyeccion.codigo + '" creada exitosamente!!! '
                    this.toast.tipo = 'success';
                    this.toast.mostrar();
                    this.reset();
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
