import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastComponent } from "../../toast/toast.component";
import { Categoria } from '../../../models/categoria/categoria';
import { CategoriaService } from '../../../services/categoria/categoria.service';
import { Status } from '../../../shared/status/status';

@Component({
    selector: 'app-categorias-form-component',
    standalone: true,
    imports: [ToastComponent, ReactiveFormsModule],
    templateUrl: './categorias-form.component.html',
})
export class CategoriasFormComponent implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;
    categoriaForm!: FormGroup;
    categoria!: Categoria;


    constructor(private formBuilder: FormBuilder, private categoriaService: CategoriaService) {
    }

    ngOnInit(): void {
        this.categoriaForm = this.formBuilder.group({
            nombre: ['',[ Validators.required,Validators.minLength(1), Validators.maxLength(50),
                    Validators.pattern('^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+$') 
            ]],
        });
    }

    crear() {
        if (this.categoriaForm.valid) {
            this.categoria = this.categoriaForm.value as Categoria;

            this.categoriaService.crearCategoria(this.categoria).subscribe({
                next: () => {
                    this.toast.titulo = 'Exitoso';
                    this.toast.tipo = 'success';
                    this.toast.mensaje = 'Creacion de categoria "' + this.categoria.nombre + '" exitosa';
                    this.toast.mostrar();
                    this.reset();
                },
                error: (error) => {
                    this.toast.titulo = 'Error';
                    this.toast.tipo = 'danger';
                    console.log(error);
                    if (error.status == Status.CONFLICT) {
                        this.toast.mensaje = 'Ya existe una categoria con el nombre: "' + this.categoria.nombre + '" usa otro';
                    } else if (error.status == Status.BAD_REQUEST) {
                        this.toast.mensaje = 'Error en los datos enviados, por favor ingresa datos correctos';
                        this.reset();
                    } else if (error.status == Status.INTERNAL_SERVER_ERROR) {
                        this.toast.mensaje = 'Error interno del servidor';
                    }
                    this.toast.mostrar();
                }
            });

        }
    }
    reset(): void {
        this.categoriaForm.reset({

        });
    }
}
