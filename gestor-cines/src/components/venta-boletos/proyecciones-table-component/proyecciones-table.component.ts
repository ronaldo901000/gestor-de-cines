import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ToastComponent } from '../../toast/toast.component';
import { RouterLink } from '@angular/router';
import { ProyeccionResponse } from '../../../models/proyeccion/proyeccion-response';
import { ProyeccionesServices } from '../../../services/proyeccion/proyeccion.services';
import { DatePipe, CurrencyPipe } from '@angular/common';
@Component({
    selector: 'app-proyecciones-pelicula-table-component',
    imports: [ToastComponent, DatePipe, CurrencyPipe,],
    templateUrl: './proyecciones-table.component.html',
})
export class ProyeccionesPeliculaTableComponent implements OnInit {
    @Input({required:true})
    codigoPelicula!: string
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
        this.proyeccionesServices.obtenerProyeccionesPorCodigoPelicula(this.codigoPelicula, this.inicio).subscribe({
            next: (proyeccionesServer: ProyeccionResponse[]) => {
                this.proyecciones = proyeccionesServer;

            },
            error: (error) => {
                console.log(error.error);
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
        if (this.proyecciones.length==this.rango) {
            return true;
        }
        return false;
    }

}