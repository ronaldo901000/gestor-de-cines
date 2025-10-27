import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';
import { Pelicula } from '../../models/pelicula/pelicula';

@Injectable({
    providedIn: 'root',
})
export class PeliculaServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public crearNuevaPelicula(pelicula: Pelicula): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiURL()}peliculas`, pelicula);
    }



    public traerPeliculasPorPagina(inicio: number): Observable<Pelicula[]> {
        return this.httpClient.get<Pelicula[]>(`${this.restConstants.getApiURL()}peliculas/paginacion/${inicio}`);
    }
    public traerPeliculasPorTituloOCategoria(cadena: string): Observable<Pelicula[]> {
        return this.httpClient.get<Pelicula[]>(`${this.restConstants.getApiURL()}peliculas/especifico/${cadena}`);
    }

    public traerPeliculaPorCodigo(codigo: String): Observable<Pelicula> {
        return this.httpClient.get<Pelicula>(`${this.restConstants.getApiURL()}peliculas/pelicula/${codigo}`);
    }

    public actualizarPelicula(pelicula: Pelicula): Observable<Pelicula> {
        return this.httpClient.put<Pelicula>(`${this.restConstants.getApiURL()}peliculas`, pelicula);
    }

    public eliminarPelicula(codigo: string): Observable<void> {
        return this.httpClient.delete<void>(
            `${this.restConstants.getApiURL()}peliculas/${codigo}`);
    }

}
