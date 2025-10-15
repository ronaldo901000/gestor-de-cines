import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { CineServices } from "../../../services/cine/cine.services";
import { Cine } from "../../../models/cine/cine";
import { ToastComponent } from "../../toast/toast.component";
@Component({
    selector: 'app-actualizar-cine-form',
    standalone: true,
    imports: [FormsModule, ReactiveFormsModule, ToastComponent],
    templateUrl: './actualizar-cine-form.component.html',
    styleUrl: './actualizar-cine-form.component.css'
})
export class ActualizarCineFormComponent implements OnInit {
    @ViewChild('toast') toast!: ToastComponent;
    cineActualizarForm!: FormGroup;
    codigoCine!: String
    cineActualizado!: Cine
    cineUpdateServer!: Cine
    constructor(
        private formBuilder: FormBuilder,
        private cineServices: CineServices,
        private router: Router) {
        this.traerCine();
    }

    ngOnInit(): void {
        this.codigoCine = history.state['codigoCine'];
        if (!this.codigoCine) {
            console.error('No se recibio ningun codigo del cine');
            return;
        }
        this.cineActualizarForm = this.formBuilder.group({
            codigo: ['', [Validators.required, Validators.maxLength(25)]],
            nombre: ['', [Validators.required, Validators.maxLength(100)]],
            ubicacion: ['', [Validators.required, Validators.maxLength(300)]],
            fechaCreacion: ['', [Validators.required]],
        });

        this.traerCine();
    }

    traerCine() {
        this.cineServices.traerCinesPorPalabraClave(this.codigoCine)
            .subscribe({
                next: (resultado: Cine[]) => {
                    if (resultado.length > 0) {
                        this.cineActualizado = resultado[0];
                        console.log('Cine encontrado:', this.cineActualizado);

                        this.cineActualizarForm.patchValue({
                            codigo: this.cineActualizado.codigo,
                            nombre: this.cineActualizado.nombre,
                            ubicacion: this.cineActualizado.ubicacion,
                            fechaCreacion: this.cineActualizado.fechaCreacion,
                        });
                    }
                },
                error: (err) => console.error('Error al traer cine:', err)
            });
    }


    actualizar(): void {
        if (this.cineActualizarForm.valid) {    
            this.cineActualizado = this.cineActualizarForm.getRawValue() as Cine;

            this.cineServices.actualizarCine(this.cineActualizado).subscribe({
                next: (cineUpdateServer: Cine) => {
                    this.cineUpdateServer = cineUpdateServer;
                    this.toast.titulo = 'Actualización exitosa';
                    this.toast.tipo = 'success';
                    this.toast.mensaje = `El cine "${cineUpdateServer.nombre}" fue actualizado correctamente`;
                    this.toast.mostrar();
                },
                error: (error: any) => {
                    this.toast.titulo = 'Error';
                    this.toast.tipo = 'danger';
                    console.error(error);

                    if (error.status === 404) {
                        this.toast.mensaje = `No se encontro el cine con código: "${this.cineActualizado.codigo}".`;
                    } else if (error.status === 400) {
                        this.toast.mensaje = 'Error en los datos enviados. Por favor, revisa los campos';
                    } else if (error.status === 500) {
                        this.toast.mensaje = 'Error interno del servidor.';
                    } else {
                        this.toast.mensaje = 'Ocurrio un error desconocido';
                    }

                    this.toast.mostrar();
                }
            });
        }
    }


}