import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastComponent } from '../../toast/toast.component';
import { UsuarioServices } from '../../../services/usuario/usuario.services';
import { UsuarioRequest } from '../../../models/usuario/usuario-request';
@Component({
  selector: 'app-registro-form-component',
  imports: [FormsModule, ReactiveFormsModule, ToastComponent],
  templateUrl: './registro-form.component.html',
  styleUrl: './registro-form.component.css'
})
export class RegistroFormComponent implements OnInit {

  @ViewChild('toast')
  toast!: ToastComponent;

  usuarioForm!: FormGroup

  nuevoUsuario!: UsuarioRequest

  constructor(private formBuilder: FormBuilder, private usuarioServices: UsuarioServices) { }

  ngOnInit(): void {

    this.inicializarForm();
  }

  crear(): void {
    if (this.usuarioForm.valid) {
      this.nuevoUsuario = this.usuarioForm.value as UsuarioRequest;
      this.reiniciarToast();
      this.usuarioServices.crearCuenta(this.nuevoUsuario).subscribe({
        next: () => {
          this.toast.titulo = 'Creacion exitosa';
          this.toast.tipo = 'warning';
          this.toast.mensaje = 'Creacion de cuenta exitosa';
          this.toast.dato1 = 'ID: ' + this.nuevoUsuario.id;
          this.toast.dato2 = 'Nombre: ' + this.nuevoUsuario.nombre;
          this.toast.dato3 = 'Correo: ' + this.nuevoUsuario.correo;
          this.toast.dato4 = 'Telefono: ' + this.nuevoUsuario.telefono;
          this.toast.mostrar();
          this.reset();
        },
        error: (error) => {
          this.toast.titulo = 'Error en la creacion';
          this.toast.tipo = 'danger';
          this.toast.mensaje = error.error;
          this.toast.mostrar();
        }
      });
    }

  }

  reset(): void {
    this.usuarioForm.reset();
  }

  inicializarForm(): void {
    this.usuarioForm = this.formBuilder.group({
      id: [null, [Validators.required, Validators.maxLength(25), Validators.pattern('^[0-9A-Za-zñÑ]+$')]],
      nombre: [null, [Validators.required, Validators.maxLength(100)]],
      correo: [null, [Validators.required, Validators.email, Validators.maxLength(100)]],
      contraseña: [null, [Validators.required, Validators.maxLength(25)]],
      telefono: [null, [Validators.required, Validators.maxLength(25), Validators.pattern('^[0-9]+$')]]
    });
  }

  reiniciarToast() {
    this.toast.dato1 = '';
    this.toast.dato2 = '';
    this.toast.dato3 = '';
    this.toast.dato4 = '';
  }
}
