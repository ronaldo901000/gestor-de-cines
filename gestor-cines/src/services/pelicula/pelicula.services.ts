import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Cine } from '../../models/cine/cine';
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

    public traerCinesPorPalabraClave(palabraClave: String): Observable<Cine[]> {
        return this.httpClient.get<Cine[]>(`${this.restConstants.getApiURL()}cines/palabraClave/${palabraClave}`);
    }

    public actualizarCine(cine: Cine): Observable<Cine> {
        return this.httpClient.put<Cine>(
            `${this.restConstants.getApiURL()}cines`,
            cine
        );
    }

    eliminarCine(codigo: string): Observable<void> {
        return this.httpClient.delete<void>(
            `${this.restConstants.getApiURL()}cines/${codigo}`);
    }

}
