import { Component, OnInit, ViewChild } from '@angular/core';
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
import { Pelicula } from '../../../models/pelicula/pelicula';
import { PeliculaServices } from '../../../services/pelicula/pelicula.services';
import { Status } from '../../../shared/status/status';
import { CategoriaService } from '../../../services/categoria/categoria.service';
import { CategoriaResponse } from '../../../models/categoria/categoria-response';
@Component({
    standalone: true,
    selector: 'app-pelicula-form-component',
    imports: [FormsModule, ReactiveFormsModule, ToastComponent, CommonModule],
    templateUrl: './pelicula-form.component.html',
    styleUrl: './pelicula-form.component.css'
})
export class PeliculaFormComponent implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;
    peliculaForm!: FormGroup;
    categoriasString!: String
    nuevaPelicula!: Pelicula;

    eventTypeOptions: string[] = Object.values(ClasificacionTypeEnum);

    constructor(
        private formBuilder: FormBuilder,
        private peliculaServices: PeliculaServices,
        private categoriaServices: CategoriaService) {

    }

    ngOnInit(): void {
        this.cargarListaCategorias();
        this.peliculaForm = this.formBuilder.group({
            codigo: [null, [Validators.required, Validators.maxLength(25), Validators.pattern(/^[a-zA-Z0-9-ñ]+$/)]],
            titulo: [null, [Validators.required, Validators.maxLength(100), Validators.pattern(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ,.'":;!¿?()\-]+$/)]],
            sinopsis: [null, [Validators.required, Validators.maxLength(300)]],
            duracion: [1, [Validators.required, Validators.min(1), Validators.max(1000)]],
            director: [null, [Validators.required, Validators.maxLength(100)]],
            cast: [null, [Validators.required, Validators.maxLength(200)]],
            clasificacion: [ClasificacionTypeEnum.A, [Validators.required]],
            fechaEstreno: [new Date().toISOString().substring(0, 10), [Validators.required]],
            idsCategorias: [null, [Validators.required, Validators.maxLength(100), Validators.pattern(/^[0-9,]+$/)]],

        });
    }

    crear(): void {
        if (this.peliculaForm.valid) {
            this.nuevaPelicula = this.peliculaForm.value as Pelicula;
            this.peliculaServices.crearNuevaPelicula(this.nuevaPelicula).subscribe({
                next: () => {
                    this.toast.titulo = 'Exitoso';
                    this.toast.mensaje = 'Pelicula con codigo: "' + this.nuevaPelicula.codigo + '" creada exitosamente!!! '
                    this.toast.tipo = 'success';
                    this.toast.mostrar();
                    this.reset();
                },
                error: (error) => {
                    this.toast.titulo = 'Error';
                    this.toast.tipo = 'danger';
                    console.log(error);
                    if (error.status == Status.CONFLICT) {
                        this.toast.mensaje = 'Ya existe una pelicula con el codigo: "' + this.nuevaPelicula.codigo + '" usa otro';
                    } else if (error.status == Status.BAD_REQUEST) {
                        this.toast.mensaje = 'Error en los datos enviados, por favor ingresa datos correctos';
                    } else if (error.status == Status.INTERNAL_SERVER_ERROR) {
                        this.toast.mensaje = 'Error interno del servidor';
                    } else if (error.status == Status.NOT_FOUND) {
                        this.toast.mensaje = 'Una de las categorias ingresadas no esta registrada en el sistema,revisa';
                    }
                    else {
                        this.toast.mensaje = 'Error desconocido';
                    }
                    this.toast.mostrar();
                }
            });
        }
    }


    reset(): void {
        this.peliculaForm.reset({
            fechaEstreno: new Date().toISOString().substring(0, 10),
            clasificacion: ClasificacionTypeEnum.A
        });
    }

    cargarListaCategorias() {
        this.categoriaServices.traerCategorias().subscribe({
            next: (categorias: CategoriaResponse[]) => {
                const listaFormateada = categorias.map(cat => `${cat.id}-${cat.nombre}`);
                this.categoriasString = listaFormateada.join(',  ');
            },
            error: (err) => {
                console.error('Error al traer las categorias', err);
            }
        });
    }


}
