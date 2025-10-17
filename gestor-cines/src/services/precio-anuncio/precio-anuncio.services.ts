import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';
import { PrecioAnuncio } from '../../models/precio-anuncio/precio-anuncio';

@Injectable({
    providedIn: 'root',
})
export class PrecioAnunciosServices {
    restConstants = new RestConstants();
    constructor(private httpClient: HttpClient) { }

    public traerPrecios(): Observable<PrecioAnuncio[]> {
        return this.httpClient.get<PrecioAnuncio[]>(`${this.restConstants.getApiURL()}precioAnuncios`);
    }
    
    public actualizarPrecios(precioAnuncios: PrecioAnuncio): Observable<PrecioAnuncio> {
        return this.httpClient.put<PrecioAnuncio>(
            `${this.restConstants.getApiURL()}precioAnuncios`,precioAnuncios);
    }

}
