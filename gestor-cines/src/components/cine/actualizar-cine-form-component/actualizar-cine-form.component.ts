import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { CineServices } from "../../../services/cine/cine.services";
import { Cine } from "../../../models/cine/cine";

@Component({
    selector: 'app-actualizar-cine-form',
    standalone: true,
    imports: [FormsModule, ReactiveFormsModule],
    templateUrl: './actualizar-cine-form.component.html',
    styleUrl: './actualizar-cine-form.component.css'
})
export class ActualizarCineFormComponent implements OnInit {

    cineActualizarForm!: FormGroup;
    cineActualizado!: Cine;

    constructor(
        private formBuilder: FormBuilder,
        private cineServices: CineServices,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.cineActualizado = history.state['cine'];
        if(this.cineActualizado==null){
            console.log('Es nulo el cine');
        }
        this.cineActualizarForm = this.formBuilder.group({
            codigo: [this.cineActualizado.codigo, [Validators.required, Validators.maxLength(25)]],
            nombre: [this.cineActualizado.nombre, [Validators.required, Validators.maxLength(100)]],
            ubicacion: [this.cineActualizado.ubicacion, [Validators.required, Validators.maxLength(300)]],
            fechaCreacion: [this.cineActualizado.fechaCreacion, [Validators.required]],
        });
    }


    submit(): void {
        console.log('se hizo submit');
        if (this.cineActualizarForm.valid) {
            console.log(this.cineActualizarForm.value);
            /*
            
            this.newCine = this.newCineForm.value as Cine;
            this.cineServices.createNewEvent(this.newCine).subscribe({
                next: () => {
                    this.reset();
                },
                error: (error: any) => {
                    console.log(error);
                }
            });
            
            
            */
            console.log(this.cineActualizado);
        }
    }

}