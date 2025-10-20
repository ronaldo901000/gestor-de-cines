import { Component, Input, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { ToastComponent } from "../../toast/toast.component";
import { AdminSistemaServices } from "../../../services/admin-sistema/admin-sistema.service";
import { AdminSistema } from "../../../models/admin-sistema/admin-sistema";

@Component({
    selector: 'app-crear-admin-sistema-form-component',
    standalone: true,
    imports: [FormsModule, ReactiveFormsModule, ToastComponent],
    templateUrl: './admin-sistema-form.component.html',
})
export class CrearAdminSistemaFormComponet implements OnInit {

    @ViewChild('toast') toast!: ToastComponent;

    adminSistemaForm!: FormGroup;
    adminSistema!: AdminSistema;

    constructor(private formBuilder: FormBuilder, private adminService: AdminSistemaServices
    ) { }

    ngOnInit(): void {
        this.adminSistemaForm = this.formBuilder.group({
            id: ['', [Validators.required, Validators.maxLength(25), Validators.minLength(1)]],
        });
    }

    crear(): void {
        if (this.adminSistemaForm.valid) {
            this.adminSistema = this.adminSistemaForm.value as AdminSistema;
            this.adminService.crearAdminSistema(this.adminSistema).subscribe({
                next: () => {
                    this.toast.titulo = 'Creacion exitosa';
                    this.toast.tipo = 'success';
                    this.toast.mensaje = 'Creacion del admin con ID:"' + this.adminSistema.id + '" exitoso';
                    this.toast.mostrar();
                    this.reset();

                },
                error: (error: any) => {
                    this.toast.titulo = 'Error de creacion';
                    this.toast.tipo = 'danger';
                    this.toast.mensaje = error.error;

                    this.toast.mostrar();
                }
            });
        }

    }

    reset(): void {
        this.adminSistemaForm.reset({
        });
    }
}
