import { Component, OnInit, ViewChild } from '@angular/core';
import {
    FormBuilder,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';
import { CineServices } from '../../../services/cine/cine.services';
import { Cine } from '../../../models/cine/cine';
import { ToastComponent } from '../../toast/toast.component';
@Component({
    selector: 'app-cine-form',
    imports: [FormsModule, ReactiveFormsModule, ToastComponent],
    templateUrl: './cine-form.component.html',
    styleUrl: './cine-form.component.css',
})
export class CineFormComponent implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;
    newCineForm!: FormGroup;
    newCine!: Cine;
    mensaje: string = '';
    constructor(private formBuilder: FormBuilder, private cineServices: CineServices) { }

    ngOnInit(): void {
        this.newCineForm = this.formBuilder.group({
            codigo: [null, [Validators.required, Validators.maxLength(25)]],
            nombre: [null, [Validators.required, Validators.maxLength(100)]],
            ubicacion: [null, [Validators.required, Validators.maxLength(300)]],
            fechaCreacion: [new Date().toISOString().substring(0, 10), [Validators.required]],
        });
    }

    crear(): void {
        if (this.newCineForm.valid) {
            this.newCine = this.newCineForm.value as Cine;
            this.cineServices.crearNuevoCine(this.newCine).subscribe({
                next: () => {
                    this.toast.titulo = 'Exitoso';
                    this.toast.mensaje = 'Cine con codigo: "'+this.newCine.codigo+'" creado exitosamente!!! '
                    this.toast.tipo = 'success';
                    this.toast.mostrar();
                    this.reset();
                },
                error: (error: any) => {
                    this.toast.titulo = 'Error';
                    this.toast.tipo = 'danger';
                    console.log(error);
                    if (error.status == 409) {
                         this.toast.mensaje ='Ya existe un cine con el codigo: "'+this.newCine.codigo+'" usa otro';
                    } else if (error.status == 400) {
                         this.toast.mensaje ='Error en los datos enviados, por favor ingresa datos correctos';
                         this.reset();
                    } else if (error.status == 500) {
                         this.toast.mensaje ='Error interno del servidor';
                    }
                    this.toast.mostrar();
                },
            });
            console.log(this.newCine);
        }
    }

    reset(): void {
        this.newCineForm.reset({
            fechaCreacion: new Date().toISOString().substring(0, 10),
        });
    }
}
