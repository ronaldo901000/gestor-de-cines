import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ToastComponent } from '../../toast/toast.component';
import { SalasServices } from '../../../services/sala/sala.services';
import { RouterLink } from '@angular/router';
import { SalaResponse } from '../../../models/sala/sala-response';
@Component({
    selector: 'app-salas-table-component',
    imports: [ToastComponent, RouterLink],
    templateUrl: './sala-table.component.html',
})
export class SalasTableComponent implements OnInit {
    @Input()
    codigoCine!: string
    @ViewChild
        ('toast') toast!: ToastComponent;
    protected inicio: number = 0;
    protected rango: number = 5;

    salas: SalaResponse[] = []

    constructor(private salasService: SalasServices) {

    }

    ngOnInit(): void {
        this.traerSalas();
    }

    traerSalas() {
        this.salasService.traerSalasPorPagina(this.codigoCine, this.inicio).subscribe({
            next: (salasServer: SalaResponse[]) => {
                this.salas = salasServer;

            },
            error: (error) => {
                console.log(error.error);
            }
        });

    }

    confirmarEliminacion(codigoPelicula: string): void {
        const confirmado = confirm('¿Estas seguro de que deseas eliminar esta sala?');
        if (confirmado) {
            this.eliminarSala(codigoPelicula);
        }
    }

    eliminarSala(codigoSala: string) {
        this.salasService.eliminarSala(codigoSala).subscribe({
            next: () => {
                this.toast.titulo = 'Sala Eliminada';
                this.toast.tipo = 'warning';
                this.toast.mensaje = 'Sala Eliminada exitosamante';
                this.toast.mostrar();
                this.traerSalas();
            },
            error: (error: any) => {
                this.toast.titulo = 'Error de Eliminacion';
                this.toast.tipo = 'danger';
                this.toast.mensaje = error.error;
                this.toast.mostrar();
            }
        });
    }

    avanzarUnaPagina() {
        this.inicio += this.rango;
        this.traerSalas();
    }
    retrocederUnaPagina() {
        this.inicio -= this.rango;
        this.traerSalas();
    }

    hayMasCines(): boolean {
        if (!this.salas || this.salas.length === 0) {
            return false;
        }
        return this.salas.length === this.rango;
    }

}