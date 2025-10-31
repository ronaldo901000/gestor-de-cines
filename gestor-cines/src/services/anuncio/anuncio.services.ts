import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Cine } from '../../models/cine/cine';
import { Observable } from 'rxjs';
import { AnuncioTexto } from '../../models/anuncio/anuncio-texto';
import { AnuncioVideo } from '../../models/anuncio/anuncio-video';

@Injectable({
    providedIn: 'root',
})
export class AnuncioServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public crearAnuncioDeTexto(anuncio: AnuncioTexto): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiURL()}anuncios/texto/`, anuncio);
    }

    public crearAnuncioDeVideo(anuncio: AnuncioVideo): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiURL()}anuncios/video/`, anuncio);
    }

    public crearAnuncioImagen(anuncio: FormData): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiURL()}anuncios/imagen/`, anuncio);
    }

    public traerCinesPorPagina(inicio: number): Observable<Cine[]> {
        return this.httpClient.get<Cine[]>(`${this.restConstants.getApiURL()}cines/pagina/${inicio}`);
    }


    desactivarAnuncio(codigo: string): Observable<void> {
        return this.httpClient.delete<void>(
            `${this.restConstants.getApiURL()}cines/${codigo}`);
    }

}
