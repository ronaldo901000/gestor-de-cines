import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserProperties } from '../../shared/user/user-properties';
import { AdminCineProperties } from '../../shared/user/admin-cine-properties';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Credencial } from '../../models/credencial/credencial';
import { UsuarioServices } from '../../services/usuario/usuario.services';
import { PropiedadesUsuario } from '../../models/propiedades-usuario/propiedades-usuario';
import { Roles } from '../../shared/user/user-roles';
@Component({
  selector: 'app-login-page',
  imports: [FormsModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class Login implements OnInit {

  alreadyLoggedIn!: boolean;
  currentRole!: string | null;
  idUsuario: string = 'USR001';

  role!: string | null;

  credencialesForm!: FormGroup;
  nuevaCredencial!: Credencial;
  propiedadesUsuario!: PropiedadesUsuario;
  errorInicioSesion!:boolean;
  mensaje!:string;

  constructor(private router: Router, private formBuilder: FormBuilder,
    private usuarioServices: UsuarioServices
  ) {

  }
  ngOnInit(): void {
    this.alreadyLoggedIn = localStorage.getItem('role') != null;
    this.currentRole = localStorage.getItem('role');
    this.inicializarForm();
  }

  login(role: string): void {
    localStorage.setItem('role', role);
    localStorage.setItem(UserProperties.ID, this.idUsuario)
    switch (role) {
      case 'ADMIN-SISTEMA':
        this.router.navigate(['/home/admin-sistema']);
        break;
      case 'ADMIN-CINE':
        localStorage.setItem(AdminCineProperties.CODIGO_CINE, 'CN-1');
        this.router.navigate(['/home/admin-cine']);
    }
  }

  iniciarSesion(): void {
    if (this.credencialesForm.valid) {
      this.nuevaCredencial = this.credencialesForm.value as Credencial;
      this.usuarioServices.iniciarSesion(this.nuevaCredencial).subscribe({
        next: (propiedadesUsuarioServer: PropiedadesUsuario) => {
          this.propiedadesUsuario = propiedadesUsuarioServer;
          this.agregarPropiedadesALocalStorage();
          //navegar hacia el home dependiendo el rol
          if (this.role) {
            this.llevarAlHome(this.role);
          }
        },
        error:(error)=>{
          console.log(error.error);
          this.errorInicioSesion=true;
          this.mensaje=error.error;
        }
      });
    }
  }

  agregarPropiedadesALocalStorage(): void {
    //se agrega el id
    localStorage.setItem(UserProperties.ID, this.propiedadesUsuario.id);
    //se agrega el rol
    localStorage.setItem(UserProperties.ROL, this.propiedadesUsuario.role);
    this.role = localStorage.getItem(UserProperties.ROL);
    //se agrega si es anunciante o no
    localStorage.setItem(UserProperties.ES_ANUNCIANTE, this.propiedadesUsuario.esAnunciante.toString());
    if (this.propiedadesUsuario.role == Roles.ADMIN_CINE) {
      //se agrega el cine al que trabaja
      localStorage.setItem(UserProperties.CODIGO_CINE, this.propiedadesUsuario.codigoCine);
    }
  }

  llevarAlHome(role: string) {
    switch (role) {
      
      case Roles.ADMIN_SISTEMA:
        this.router.navigate(['/home/admin-sistema']);
        break;
      case Roles.ADMIN_CINE:
        this.router.navigate(['/home/admin-cine']);
        break;
        
    }
  }
  inicializarForm(): void {
    this.credencialesForm = this.formBuilder.group({
      correo: [null, [Validators.required, Validators.email]],
      contraseña: [null, [Validators.required]],
    });
  }
}