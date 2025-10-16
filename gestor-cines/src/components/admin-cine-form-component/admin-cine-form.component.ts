import { Component, Input, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { ToastComponent } from "../toast/toast.component";
import { AdminCine } from "../../models/admin-cine/admin-cine";
import { Cine } from "../../models/cine/cine";
import { AdminCinesServices } from "../../services/admin-cine/admin-cine.services";

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
      id: ['', [Validators.required, Validators.maxLength(25),Validators.minLength(1)]],
    });
  }

  crear(): void {
    if (this.adminCineForm.valid) {
      this.adminCine = this.adminCineForm.value as AdminCine;
      this.adminService.crearAdminCine(this.adminCine).subscribe({
        next: () => {
          this.toast.titulo = 'Creacion exitosa';
          this.toast.tipo = 'success';
          this.toast.mensaje = 'Creacion del admin exitoso';
          this.toast.mostrar();

        },
        error: (error: any) => {
          this.toast.titulo = 'Error de creacion';
          this.toast.tipo = 'danger';
          if(error.status==400){
            this.toast.mensaje='Error con los datos enviados';
          }
          else if(error.status==409){
            this.toast.mensaje='El usuario ya es admin';
          }
          else if(error.status==404){
            this.toast.mensaje='El ID del usuario no esta registrado en el sistema';
          }
          this.toast.mostrar();
        }
      });
    }

  }

  reset(): void {
    this.adminCineForm.reset();
  }
}
