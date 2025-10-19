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
    selector: 'app-actualizar-pelicula-form-component',
    imports: [FormsModule, ReactiveFormsModule, ToastComponent, CommonModule],
    templateUrl: './actualizar-pelicula-form.component.html',
    styleUrl: './actualizar-pelicula-form.component.css'
})
export class ActualizarPeliculaFormComponent implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;
    peliculaForm!: FormGroup;
    categoriasString!: String
    peliculaActualizada!: Pelicula;
    codigoPelicula!: string;
    eventTypeOptions: string[] = Object.values(ClasificacionTypeEnum);

    constructor(
        private formBuilder: FormBuilder,
        private peliculaServices: PeliculaServices,
        private categoriaServices: CategoriaService) {

    }

    ngOnInit(): void {
        this.codigoPelicula = history.state['codigoPelicula'];
        this.inicializarRestriccionesForm();
        this.traerPelicula();
        this.cargarListaCategorias();

    }

    actualizar(): void {
        if (this.peliculaForm.valid) {
            this.peliculaActualizada = this.peliculaForm.value as Pelicula;
            this.peliculaServices.actualizarPelicula(this.peliculaActualizada).subscribe({
                next: () => {
                    this.toast.titulo = 'Exitoso';
                    this.toast.mensaje = 'Pelicula con codigo: "' + this.peliculaActualizada.codigo + '" Actualizada exitosamente!!! '
                    this.toast.tipo = 'success';
                    this.toast.mostrar();
                },
                error: (error) => {
                    this.toast.titulo = 'Error';
                    this.toast.tipo = 'danger';
                    console.log(error);
                    if (error.status == Status.CONFLICT) {
                        this.toast.mensaje = 'Ya existe una pelicula con el codigo: "' + this.peliculaActualizada.codigo + '" usa otro';
                    } else if (error.status == Status.BAD_REQUEST) {
                        this.toast.mensaje = 'Error en los datos enviados, por favor ingresa datos correctos';
                    } else if (error.status == Status.INTERNAL_SERVER_ERROR) {
                        this.toast.mensaje = error.error;
                    } else if (error.status == Status.NOT_FOUND) {
                        this.toast.mensaje = error.error;
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

    inicializarRestriccionesForm() {
        this.peliculaForm = this.formBuilder.group({
            codigo: [null, [Validators.required, Validators.maxLength(25), Validators.pattern(/^[a-zA-Z0-9-ñ]+$/)]],
            titulo: [null, [Validators.required, Validators.maxLength(100), Validators.pattern(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ,.'":;!¿?()\-]+$/)]],
            sinopsis: [null, [Validators.required, Validators.maxLength(300)]],
            duracion: [1, [Validators.required, Validators.min(1)]],
            director: [null, [Validators.required, Validators.maxLength(100)]],
            cast: [null, [Validators.required, Validators.maxLength(200)]],
            clasificacion: [ClasificacionTypeEnum.A, [Validators.required]],
            fechaEstreno: [new Date().toISOString().substring(0, 10), [Validators.required]],
            idsCategorias: [null, [Validators.required, Validators.maxLength(100), Validators.pattern(/^[0-9,]+$/)]],
        });
    }

    traerPelicula() {
        this.peliculaServices.traerPeliculaPorCodigo(this.codigoPelicula).subscribe({
            next: (peliculaServer: Pelicula) => {
                this.peliculaForm.patchValue({
                    codigo: peliculaServer.codigo,
                    titulo: peliculaServer.titulo,
                    sinopsis: peliculaServer.sinopsis,
                    duracion: peliculaServer.duracion,
                    director: peliculaServer.director,
                    cast: peliculaServer.cast,
                    clasificacion: peliculaServer.clasificacion,
                    fechaEstreno: peliculaServer.fechaEstreno,
                    idsCategorias: peliculaServer.idsCategorias
                });
            },
            error: (error) => {
                console.log(error);
                this.toast.titulo = 'Error';
                this.toast.tipo = 'danger';
                if(error.status==Status.INTERNAL_SERVER_ERROR){
                    this.toast.mensaje = 'Error al traer pelicula';
                }
                
                this.toast.mostrar();
            }
        });
    }
}
