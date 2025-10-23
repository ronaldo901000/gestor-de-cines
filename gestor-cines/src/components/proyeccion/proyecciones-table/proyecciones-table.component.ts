import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ToastComponent } from '../../toast/toast.component';
import { RouterLink } from '@angular/router';
import { ProyeccionResponse } from '../../../models/proyeccion/proyeccion-response';
import { ProyeccionesServices } from '../../../services/proyeccion/proyeccion.services';
import { DatePipe, CurrencyPipe } from '@angular/common';
@Component({
    selector: 'app-proyecciones-table-component',
    imports: [ToastComponent, RouterLink, DatePipe, CurrencyPipe,],
    templateUrl: './proyecciones-table.component.html',
})
export class ProyeccionesTableComponent implements OnInit {
    @Input()
    codigoCine!: string
    @ViewChild
        ('toast') toast!: ToastComponent;
    protected inicio: number = 0;
    protected rango: number = 5;

    proyecciones: ProyeccionResponse[] = []

    constructor(private proyeccionesServices: ProyeccionesServices) {

    }

    ngOnInit(): void {
        this.traerProyecciones();
    }

    traerProyecciones() {
        this.proyeccionesServices.obtenerProyeccionesPorPagina(this.codigoCine, this.inicio).subscribe({
            next: (proyeccionesServer: ProyeccionResponse[]) => {
                this.proyecciones = proyeccionesServer;

            },
            error: (error) => {
                console.log(error.error);
            }
        });

    }

    confirmarEliminacion(codigoProyeccion: string): void {
        const confirmado = confirm('¿Estas seguro de que deseas eliminar esta proyeccion?');
        if (confirmado) {
            this.eliminarProyeccion(codigoProyeccion);
        }
    }

    eliminarProyeccion(codigoProyeccion: string) {
        this.proyeccionesServices.eliminarProyeccion(codigoProyeccion).subscribe({
            next: () => {
                this.toast.titulo='Eliminacion exitosa';
                this.toast.tipo='warning';
                this.toast.mensaje='Elimacion realizada con exito';
                this.toast.mostrar();
                this.traerProyecciones();
            }
        });
    }

    avanzarUnaPagina() {
        this.inicio += this.rango;
        this.traerProyecciones();
    }
    retrocederUnaPagina() {
        this.inicio -= this.rango;
        this.traerProyecciones();
    }

    hayMasProyecciones(): boolean {
        if (!this.proyecciones || this.proyecciones.length === 0) {
            return false;
        }
        return this.proyecciones.length === this.rango;
    }

}