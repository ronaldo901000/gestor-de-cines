import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ToastComponent } from '../../toast/toast.component';
import { UsuarioServices } from '../../../services/usuario/usuario.services';
import { UsuarioRequest } from '../../../models/usuario/usuario-request';
import { Usuario } from '../../../models/usuario/usuario';
@Component({
    selector: 'app-actualizacion-cuenta-form-component',
    imports: [FormsModule, ReactiveFormsModule, ToastComponent],
    templateUrl: './actualizacion-cuenta-form.component.html',
})
export class ActualizacionCuentaFormComponent implements OnInit {

    @ViewChild('toast')
    toast!: ToastComponent;

    usuarioForm!: FormGroup

    usuarioUpdate!: UsuarioRequest;

    @Input()
    usuarioActual!: Usuario;



    constructor(private formBuilder: FormBuilder, private usuarioServices: UsuarioServices) { }

    ngOnInit(): void {
        this.inicializarForm();
    }

    actualizar(): void {
        if (this.usuarioForm.valid) {
            this.usuarioUpdate = this.usuarioForm.value as UsuarioRequest;
            this.reiniciarToast();
            this.usuarioServices.actualizarCuenta(this.usuarioUpdate).subscribe({
                next: () => {
                    this.toast.titulo = 'Actualizacion exitosa';
                    this.toast.tipo = 'warning';
                    this.toast.mensaje = 'Actualizacion de cuenta exitosa';
                    this.toast.dato1 = 'ID: ' + this.usuarioUpdate.id;
                    this.toast.dato2 = 'Nombre: ' + this.usuarioUpdate.nombre;
                    this.toast.dato3 = 'Correo: ' + this.usuarioUpdate.correo;
                    this.toast.dato4 = 'Telefono: ' + this.usuarioUpdate.telefono;
                    this.toast.mostrar();
                },
                error: (error) => {
                    this.toast.titulo = 'Error en la Actualizacion';
                    this.toast.tipo = 'danger';
                    this.toast.mensaje = error.error;
                    this.toast.mostrar();
                }
            });
        }

    }


    inicializarForm(): void {
        this.usuarioForm = this.formBuilder.group({
            id: [null, [Validators.required, Validators.maxLength(25), Validators.pattern('^[0-9A-Za-zñÑ]+$')]],
            nombre: [null, [Validators.required, Validators.maxLength(100)]],
            correo: [null, [Validators.required, Validators.email, Validators.maxLength(100)]],
            telefono: [null, [Validators.required, Validators.maxLength(25), Validators.pattern('^[0-9]+$')]]
        });
        this.usuarioForm.patchValue({
            id: this.usuarioActual.id,
            nombre: this.usuarioActual.nombre,
            correo: this.usuarioActual.correo,
            telefono: this.usuarioActual.telefono
        });
    }


    reiniciarToast() {
        this.toast.dato1 = '';
        this.toast.dato2 = '';
        this.toast.dato3 = '';
        this.toast.dato4 = '';
    }
}
