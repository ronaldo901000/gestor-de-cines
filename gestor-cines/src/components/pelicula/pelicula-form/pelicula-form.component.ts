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
    selectedFile: File | null = null;
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
            poster: [null, [Validators.required]]
        });
    }

    crear(): void {
        if (this.peliculaForm.valid) {
            this.nuevaPelicula = this.peliculaForm.value;
            this.peliculaServices.crearNuevaPelicula(this.crearFormData()).subscribe({
                next: () => {
                    this.toast.titulo = 'Exitoso';
                    this.toast.mensaje = 'Pelicula creada exitosamente';
                    this.toast.dato1='Codigo: '+this.nuevaPelicula.codigo;
                    this.toast.dato2='Titulo: '+this.nuevaPelicula.titulo;
                    this.toast.dato3='Categorias: '+this.nuevaPelicula.idsCategorias;
                    this.toast.dato4='Duracion:'+this.nuevaPelicula.duracion;
                    this.toast.dato5='Clasificacion: '+this.nuevaPelicula.clasificacion;
                    this.toast.tipo = 'success';
                    this.toast.mostrar();
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

    crearFormData(): FormData {
        const formData = new FormData();
        formData.append('codigo', this.nuevaPelicula.codigo);
        formData.append('titulo', this.nuevaPelicula.titulo);
        formData.append('sinopsis', this.nuevaPelicula.sinopsis);
        formData.append('duracion', this.nuevaPelicula.duracion.toString());
        formData.append('director', this.nuevaPelicula.director);
        formData.append('cast', this.nuevaPelicula.cast);
        formData.append('clasificacion', this.nuevaPelicula.clasificacion);
        formData.append('duracion', this.nuevaPelicula.duracion.toString());
        formData.append('idsCategorias', this.nuevaPelicula.idsCategorias);
        formData.append('poster', this.selectedFile!);
        const fecha = new Date(this.nuevaPelicula.fechaEstreno);
        formData.append('fechaEstreno', fecha.toISOString().split('T')[0]);
        return formData;
    }

    onFileChange(event: any): void {
        const files = event.target.files;
        if (files && files.length > 0) {
            this.selectedFile = files[0];
            this.peliculaForm.controls['poster'].setValue(this.selectedFile);
            this.peliculaForm.controls['poster'].markAsTouched();
        } else {
            this.selectedFile = null;
            this.peliculaForm.controls['poster'].setValue(null);
        }
    }

}
