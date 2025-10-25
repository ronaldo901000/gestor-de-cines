import { Component, EventEmitter, Input, Output, ViewChild } from "@angular/core";
import { ToastComponent } from "../../toast/toast.component";
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";
import { FormsModule } from "@angular/forms";
import { AnuncioTexto } from "../../../models/anuncio/anuncio-texto";
import { Anuncio } from "../../../models/anuncio/anuncio";
import { TiposAnuncio } from "../../../shared/tipo-anuncio/tipo-anuncio";
import { UserProperties } from "../../../shared/user/user-properties";
import { AnuncioServices } from "../../../services/anuncio/anuncio.services";
import { AnuncioVideo } from "../../../models/anuncio/anuncio-video";

@Component({
    selector: 'app-anuncio-form-component',
    imports: [ToastComponent, FormsModule, ReactiveFormsModule],
    templateUrl: './anuncio-form.component.html',

})
export class AnuncioFormComponent {
    @ViewChild('toast') toast!: ToastComponent;
    nuevoAnuncioForm!: FormGroup;
    nuevoAnuncioTexto!: Anuncio;
    nuevoAnuncioVideo!: AnuncioVideo
    @Input()
    tipoAnuncio!: string;

    @Input()
    precio!: number;

    @Output()
    actualizarSaldo = new EventEmitter<void>();

    idAnuciante!: string | null;

    constructor(private formBuilder: FormBuilder, private anuncioServices: AnuncioServices) {

    }

    ngOnInit(): void {
        this.idAnuciante = localStorage.getItem(UserProperties.ID);
        this.inicializarForm();
    }

    crear(): void {
        this.reiniciarToast();
        const precio = this.nuevoAnuncioForm.get('precio')?.value;
        const duracion = this.nuevoAnuncioForm.get('duracion')?.value;
        const total = precio * duracion;
        if (this.nuevoAnuncioForm.valid) {
            const confirmado = confirm('¿El total es de "Q.' + total + '" Quieres confirmar la compra?');
            if (confirmado) {
                if (this.tipoAnuncio == TiposAnuncio.TEXTO) {
                    this.nuevoAnuncioTexto = this.nuevoAnuncioForm.value as AnuncioTexto;
                    this.anuncioServices.crearAnuncioDeTexto(this.nuevoAnuncioTexto).subscribe({
                        next: () => {
                            this.mostrarMensajeYAvisar(this.nuevoAnuncioTexto);
                        },
                        error: (error) => {
                            this.mostrarError(error);
                        }
                    });
                } else if (this.tipoAnuncio == TiposAnuncio.VIDEO) {
                    this.nuevoAnuncioVideo = this.nuevoAnuncioForm.value as AnuncioVideo;
                    this.anuncioServices.crearAnuncioDeVideo(this.nuevoAnuncioVideo).subscribe({
                        next: () => {
                            this.mostrarMensajeYAvisar(this.nuevoAnuncioVideo);
                        },
                        error: (error) => {
                            this.mostrarError(error);
                        }
                    });
                }
            }
        }
    }

    mostrarMensajeYAvisar(anuncioNuevo: Anuncio): void {
        this.toast.titulo = 'Compra exitosa';
        this.toast.tipo = 'success';
        this.toast.mensaje = 'Compra de anuncio exitoso';
        this.toast.dato1 = 'Codigo: ' + anuncioNuevo.codigo;
        this.toast.dato2 = 'Titulo: ' + anuncioNuevo.titulo;
        this.toast.dato3 = 'Tipo: ' + anuncioNuevo.tipo;
        this.toast.dato4 = 'Duracion: ' + anuncioNuevo.duracion + ' Dias';
        this.toast.mostrar();
        this.actualizarSaldo.emit();
        this.reset();
    }


    inicializarForm(): void {
        if (this.tipoAnuncio == TiposAnuncio.VIDEO) {
            this.nuevoAnuncioForm = this.formBuilder.group({
                codigo: [null, [Validators.required, Validators.maxLength(25), Validators.pattern(/^[a-zA-Z0-9-ñ]+$/)]],
                idAnunciante: [this.idAnuciante, Validators.required],
                titulo: [null, [Validators.required, Validators.pattern(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ,.'":;!¿?()\-]+$/)]],
                descripcion: [null, [Validators.required, Validators.pattern(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ,.'":;!¿?()\-]+$/)]],
                linkVideo: [null, Validators.required],
                fechaRegistro: [new Date().toISOString().substring(0, 10), [Validators.required]],
                duracion: [1, Validators.required],
                tipo: [this.tipoAnuncio, Validators.required],
                precio: [this.precio, Validators.required]
            });
        }
        else if (this.tipoAnuncio == TiposAnuncio.IMAGEN) {
            this.nuevoAnuncioForm = this.formBuilder.group({
                codigo: [null, [Validators.required, Validators.maxLength(25), Validators.pattern(/^[a-zA-Z0-9-ñ]+$/)]],
                idAnunciante: [this.idAnuciante, Validators.required],
                titulo: [null, [Validators.required, Validators.pattern(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ,.'":;!¿?()\-]+$/)]],
                descripcion: [null, [Validators.required, Validators.pattern(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ,.'":;!¿?()\-]+$/)]],
                imagen: [null, Validators.required],
                fechaRegistro: [new Date().toISOString().substring(0, 10), [Validators.required]],
                duracion: [1, Validators.required],
                tipo: [this.tipoAnuncio, Validators.required],
                precio: [this.precio, Validators.required]
            });
        }
        else {
            this.nuevoAnuncioForm = this.formBuilder.group({
                codigo: [null, [Validators.required, Validators.maxLength(25), Validators.pattern(/^[a-zA-Z0-9-ñ]+$/)]],
                idAnunciante: [this.idAnuciante, Validators.required],
                titulo: [null, [Validators.required, Validators.pattern(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ,.'":;!¿?()\-]+$/)]],
                descripcion: [null, [Validators.required, Validators.pattern(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ,.'":;!¿?()\-]+$/)]],
                fechaRegistro: [new Date().toISOString().substring(0, 10), [Validators.required]],
                duracion: [1, Validators.required],
                tipo: [this.tipoAnuncio, Validators.required],
                precio: [this.precio, Validators.required]
            });
        }

    }

    mostrarError(error: any): void {
        this.toast.titulo = 'Error';
        this.toast.tipo = 'danger';
        this.toast.mensaje = error.error;
        this.toast.mostrar();
    }


    reset(): void {
        this.nuevoAnuncioForm.reset({
            fechaRegistro: new Date().toISOString().substring(0, 10),
            duracion: 1,
            idAnunciante: this.idAnuciante,
            tipo: this.tipoAnuncio,
            precio: this.precio
        });
    }

    reiniciarToast(): void {
        this.toast.dato1 = '';
        this.toast.dato2 = '';
        this.toast.dato3 = '';
        this.toast.dato4 = '';
        this.toast.dato5 = '';
    }

}