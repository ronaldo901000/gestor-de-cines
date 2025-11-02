import { Component, Input, OnInit, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { ToastComponent } from '../../toast/toast.component';
import { Opinion } from '../../../models/opinion/opinion';
import { UsuarioServices } from '../../../services/usuario/usuario.services';
@Component({
  selector: 'app-opinion-form-component',
  imports: [ToastComponent, FormsModule, ReactiveFormsModule],
  templateUrl: './opinion-form.component.html'
})
export class OpinionFormComponent implements OnInit {
  @ViewChild('toast') toast!: ToastComponent;
  opinionForm!: FormGroup;

  @Input({ required: true })
  codigoEntidad!: string

  @Input({ required: true })
  idUsuario!: string

  @Input({ required: true })
  tipo!: string

  nuevaOpinion!: Opinion;

  constructor(
    private formBuilder: FormBuilder, private opinionService: UsuarioServices) {
  }


  ngOnInit(): void {
    this.inicializarForm();
  }

  inicializarForm(): void {
    this.opinionForm = this.formBuilder.group({
      codigoEntidad: [this.codigoEntidad, [Validators.required]],
      idUsuario: [this.idUsuario, [Validators.required]],
      tipo: [this.tipo, [Validators.required]],
      comentario: [null, [Validators.required, Validators.maxLength(300)]],
      calificacion: [4, [Validators.required]],
      fecha: [new Date().toISOString().substring(0, 10), [Validators.required]],

    });
  }
  publicar(): void {
    if (this.opinionForm.valid) {
      this.nuevaOpinion = this.opinionForm.value as Opinion;

      this.opinionService.publicarOpinion(this.nuevaOpinion).subscribe({
        next: () => {
          this.toast.titulo = 'Opinion Exitosa';
          this.toast.tipo = 'success';
          this.toast.mensaje = 'Opinion publicada exitosamente!!';
          this.toast.mostrar();
        },
        error: (error) => {
          this.toast.titulo = 'Error';
          this.toast.tipo = 'danger';
          this.toast.mensaje = error.error;
          this.toast.mostrar();
        }
      });
    }
  }
}
