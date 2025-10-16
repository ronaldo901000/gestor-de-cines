import { Component, ViewChild } from "@angular/core";
import { ToastComponent } from "../../toast/toast.component";
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";
import { CineServices } from "../../../services/cine/cine.services";
import { Cine } from "../../../models/cine/cine";
import { FormsModule } from "@angular/forms";
import { AnuncianteServices } from "../../../services/anunciante/anunciante.services";
import { Anunciante } from "../../../models/anunciante/anunciante";
import { Status } from "../../../shared/status/status";

@Component({
    selector: 'app-anunciante-form-component',
    imports: [ToastComponent, FormsModule, ReactiveFormsModule],
    templateUrl: './anunciante-form.componet.html',
    styleUrl: './anunciante-form.componet.css'

})
export class AnuncianteFormComponent {
    @ViewChild('toast') toast!: ToastComponent;
    nuevoAnuncianteForm!: FormGroup;
    nuevoAnunciante!: Anunciante;
    constructor(private formBuilder: FormBuilder, private anunciantesServices: AnuncianteServices) { 

    }

    ngOnInit(): void {
        this.nuevoAnuncianteForm = this.formBuilder.group({
            idUsuario: [null, [Validators.required, Validators.maxLength(25)]],
        });
    }

    crear(): void {
        if (this.nuevoAnuncianteForm.valid) {

            this.nuevoAnunciante = this.nuevoAnuncianteForm.value as Anunciante;
            this.anunciantesServices.crearNuevoAnunciante(this.nuevoAnunciante).subscribe({
                next: () => {
                    
                    this.toast.titulo = 'Anunciante Creado con exito';
                    this.toast.tipo = 'success';
                    this.toast.mensaje = 'Anunciante creado exitosamente'
                    this.toast.mostrar();
                    this.reset();
                },
                error: (error) => {
                    this.toast.titulo = 'Error al crear anunciante';
                    this.toast.tipo = 'danger';
                    if(error.status==Status.BAD_REQUEST){
                        this.toast.mensaje='Error en los datos enviados';
                        this.reset();
                    }
                    else if(error.status==Status.CONFLICT){
                        this.toast.mensaje='El Usuario ya es anunciante'
                    }
                    else if(error.status==Status.NOT_FOUND){
                        this.toast.mensaje='El usuario no esta registrado en el sistema'
                    }
                    else if(error.status==Status.INTERNAL_SERVER_ERROR){
                        this.toast.mensaje='Error con la base de datos'
                    }
                    else{
                        this.toast.mensaje='Ocurrio un error desconocido'
                    }
                    this.toast.mostrar();
                }

            })
        }
    }

    reset(): void {
        this.nuevoAnuncianteForm.reset({
        });
    }
}