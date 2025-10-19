import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ToastComponent } from '../../toast/toast.component';
import { Status } from '../../../shared/status/status';
import { PeliculaServices } from '../../../services/pelicula/pelicula.services';
import { Pelicula } from '../../../models/pelicula/pelicula';
import { DatePipe } from '@angular/common';
import { DuracionPipe } from '../../../shared/duracion-pipe/durarion-pipe';
import { RouterLink } from '@angular/router';
@Component({
    selector: 'app-peliculas-table-component',
    imports: [ToastComponent, DatePipe, DuracionPipe, RouterLink],
    templateUrl: './pelicula-table.component.html',
    styleUrl: './pelicula-table.component.css'
})
export class PeliculasTableComponent implements OnInit {

    @ViewChild
        ('toast') toast!: ToastComponent;
    protected inicio: number = 0;
    protected rango: number = 6;

    peliculas: Pelicula[] = []

    constructor(private peliculasService: PeliculaServices) {

    }

    ngOnInit(): void {
        this.traerPeliculas();
    }

    traerPeliculas() {
        this.peliculasService.traerPeliculasPorPagina(this.inicio).subscribe({
            next: (peliculasServer: Pelicula[]) => {
                this.peliculas = peliculasServer;
            },
            error: (error) => {
                this.toast.titulo = 'Error al obtener Peliculas';
                this.toast.tipo = 'danger';
                if (error.status == Status.INTERNAL_SERVER_ERROR) {
                    this.toast.mensaje = 'Error al obtener peliculas en la base de datos';
                }
                else {
                    this.toast.mensaje = 'Ocurrio un error desconocido';
                }
                this.toast.mostrar();
            }
        })
    }

    confirmarEliminacion(codigoPelicula: string): void {
        const confirmado = confirm('¿Estas seguro de que deseas eliminar esta pelicula?');
        if (confirmado) {
            this.eliminarPelicula(codigoPelicula);
        }
    }

    eliminarPelicula(codigoPelicula: string) {
        this.peliculasService.eliminarPelicula(codigoPelicula).subscribe({
            next: () => {
                this.traerPeliculas();
                this.toast.titulo = 'Eliminacion';
                this.toast.tipo = 'success';
                this.toast.mensaje = 'Elimacion exitosa';
                this.toast.mostrar();
            },
            error: (error) => {
                this.toast.titulo = 'Error';
                this.toast.tipo = 'danger';
                if(error.status==Status.INTERNAL_SERVER_ERROR){
                    this.toast.mensaje=error.error;
                }
                else if(error.status==Status.NOT_FOUND){
                    this.toast.mensaje=error.error;
                }
                else if(error.status==Status.BAD_REQUEST){
                    this.toast.mensaje=error.error;
                }
                this.toast.mostrar();
            }
        });
    }

    avanzarUnaPagina() {
        this.inicio += this.rango;
        this.traerPeliculas();
    }
    retrocederUnaPagina() {
        this.inicio -= this.rango;
        this.traerPeliculas();
    }

    hayMasCines(): boolean {
        if (!this.peliculas || this.peliculas.length === 0) {
            return false;
        }
        return this.peliculas.length === this.rango;
    }

}