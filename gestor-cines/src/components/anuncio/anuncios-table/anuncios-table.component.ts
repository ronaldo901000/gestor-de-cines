import { Component, EventEmitter, Input, Output, ViewChild } from "@angular/core";
import { AnuncioResponse } from "../../../models/anuncio/anuncio-response";
import { DatePipe, CurrencyPipe } from "@angular/common";
import { AnuncioServices } from "../../../services/anuncio/anuncio.services";
import { ToastComponent } from "../../toast/toast.component";

@Component({
    selector: 'app-anuncios-table-component',
    imports: [DatePipe, CurrencyPipe, ToastComponent],
    templateUrl: './anuncios-table.component.html',

})
export class AnunciosTableComponent {
    @ViewChild('toast') toast!: ToastComponent;
    @Input({ required: true })
    anuncios: AnuncioResponse[] = [];

    @Output()
    actualizarTabla = new EventEmitter();

    constructor(private anuncianteService: AnuncioServices) { }
    eliminar(codigoAnuncio: string): void {
        const confirmar = confirm('Estas seguro de eliminar este anuncio??');
        if (!confirmar) {
            return;
        }
        this.anuncianteService.eliminarAnuncio(codigoAnuncio).subscribe({
            next: () => {
                this.toast.tipo = 'warning';
                this.toast.mensaje = 'Eliminacion exitosa';
                this.toast.mostrar();
                this.actualizarTabla.emit();
            },
            error: (error) => {
                this.toast.tipo = 'danger';
                this.toast.mensaje = error.error;
                this.toast.mostrar();
            }
        });
    }

    desactivar(codigoAnuncio: string): void {
        this.anuncianteService.desactivarAnuncio(codigoAnuncio).subscribe({
            next: () => {
                this.toast.tipo = 'info';
                this.toast.mensaje = 'Desactivacion exitosa';
                this.toast.mostrar();
                this.actualizarTabla.emit();
            },
            error: (error) => {
                this.toast.tipo = 'danger';
                this.toast.mensaje = error.error;
                this.toast.mostrar();
            }
        });
    }

}