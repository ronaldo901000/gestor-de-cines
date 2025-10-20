import { Component, ViewChild } from '@angular/core';
import { ToastComponent } from '../../toast/toast.component';
import { CostoFuncionamientoServices } from '../../../services/costo-funcionamiento/costo-funcionamiento.services';
import { CostoFuncionamientoResponse } from '../../../models/costo-funcionamiento/costo-funcionamiento-response';
import { FormsModule } from '@angular/forms';
import { DatePipe, CurrencyPipe } from '@angular/common';
@Component({
    selector: 'app-costos-table-component',
    imports: [ToastComponent, FormsModule, DatePipe, CurrencyPipe],
    templateUrl: './costos-table.component.html',
})
export class CostosTableComponent {

    @ViewChild
        ('toast') toast!: ToastComponent;

    palabra!: string;

    costos: CostoFuncionamientoResponse[] = []

    constructor(private costosService: CostoFuncionamientoServices) {
    }


    traerHistorialCostos() {
        if (this.palabra == '' || this.palabra == undefined) {
            return
        }
        this.costosService.obtenerCostosPorPalabra(this.palabra.trim()).subscribe({
            next: (anunciantesServer: CostoFuncionamientoResponse[]) => {
                this.costos = anunciantesServer;
                if (this.costos.length == 0) {
                    this.toast.titulo = 'Sin elementos';
                    this.toast.tipo = 'warning';
                    this.toast.mensaje = 'Cine sin historial de costos';
                    this.toast.mostrar();
                }
            },
            error: (error) => {
                this.toast.titulo = 'Error al obtener costos';
                this.toast.tipo = 'danger';
                this.toast.mensaje = error.error;
                this.toast.mostrar();
            }
        });
    }

    confirmarEliminacion(id: number): void {
        const confirmado = confirm('¿Estas seguro de que deseas eliminar este cine?');
        if (confirmado) {
            this.eliminar(id);
        }
    }

    eliminar(id: number) {
        this.costosService.eliminarCosto(String(id)).subscribe({
            next: () => {
                this.toast.titulo = 'Eliminacion';
                this.toast.tipo = 'warning';
                this.toast.mensaje = 'Eliminacion exitosa';
                this.toast.mostrar();
                this.traerHistorialCostos();
            },
            error: (error) => {
                this.toast.titulo = 'Error De Eliminacion';
                this.toast.tipo = 'danger';
                this.toast.mensaje = error.error;
                this.toast.mostrar();
            }
        });
    }

}