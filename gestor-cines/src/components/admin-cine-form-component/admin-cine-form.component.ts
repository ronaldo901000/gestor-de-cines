import { Component, Input, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { ToastComponent } from "../toast/toast.component";
import { AdminCine } from "../../models/admin-cine/admin-cine";
import { Cine } from "../../models/cine/cine";
import { AdminCinesServices } from "../../services/admin-cine/admin-cine.services";
import { Status } from "../../shared/status/status";

@Component({
  selector: 'app-crear-admin-cine-form-component',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, ToastComponent],
  templateUrl: './admin-cine-form.component.html',
})
export class CrearAdminCineFormComponet implements OnInit {
  @Input() cine!: Cine;

  @ViewChild('toast') toast!: ToastComponent;

  adminCineForm!: FormGroup;
  adminCine!: AdminCine;

  constructor(private formBuilder: FormBuilder, private adminService: AdminCinesServices
  ) { }

  ngOnInit(): void {
    this.adminCineForm = this.formBuilder.group({
      codigoCine: [this.cine.codigo, [Validators.required, Validators.maxLength(25)]],
      id: ['', [Validators.required, Validators.maxLength(25), Validators.minLength(1)]],
    });
  }

  crear(): void {
    if (this.adminCineForm.valid) {
      this.adminCine = this.adminCineForm.value as AdminCine;
      this.adminService.crearAdminCine(this.adminCine).subscribe({
        next: () => {
          this.toast.titulo = 'Creacion exitosa';
          this.toast.tipo = 'success';
          this.toast.mensaje = 'Creacion del admin con ID:"' + this.adminCine.id + '" exitoso';
          this.toast.mostrar();
          this.reset();

        },
        error: (error: any) => {
          this.toast.titulo = 'Error de creacion';
          this.toast.tipo = 'danger';
          this.toast.mensaje=error.error;
          this.toast.mostrar();
        }
      });
    }

  }

  reset(): void {
    this.adminCineForm.reset({
      codigoCine: this.cine.codigo
    });
  }
}
