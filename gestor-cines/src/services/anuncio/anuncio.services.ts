import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Cine } from '../../models/cine/cine';
import { Observable } from 'rxjs';
import { AnuncioTexto } from '../../models/anuncio/anuncio-texto';
import { AnuncioVideo } from '../../models/anuncio/anuncio-video';
import { AnuncioResponse } from '../../models/anuncio/anuncio-response';

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

    public obtenerAnuncios(idAnuciante: string): Observable<AnuncioResponse[]> {
        return this.httpClient.get<AnuncioResponse[]>
            (`${this.restConstants.getApiURL()}anuncios/${idAnuciante}`);
    }


    public eliminarAnuncio(codigo: string): Observable<void> {
        return this.httpClient.delete<void>(
            `${this.restConstants.getApiURL()}anuncios/${codigo}`);
    }

    public obtenerAnunciosParaMostrar(inicio: number) {
        return this.httpClient.get<AnuncioResponse[]>
            (`${this.restConstants.getApiURL()}anuncios/por-rango/${inicio}`);
    }

    public obtenerImagenUrl(codigo: string): string {
        return `${this.restConstants.getApiURL()}anuncios/imagen/${codigo}`;
    }

    public obtenerLink(codigoAnuncio: string): Observable<string> {
        return this.httpClient.get(
            `${this.restConstants.getApiURL()}anuncios/link/${codigoAnuncio}`,
            { responseType: 'text' }
        ) as Observable<string>;
    }

    public desactivarAnuncio(codigoAnuncio: string): Observable<void> {
        return this.httpClient.put<void>(`${this.restConstants.getApiURL()}anuncios/desactivaciones/${codigoAnuncio}`, {});
    }

    public obtenerTodosLosAnuncios(): Observable<AnuncioResponse[]> {
        return this.httpClient.get<AnuncioResponse[]>
            (`${this.restConstants.getApiURL()}anuncios/todos-los-anuncios`);
    }
}
