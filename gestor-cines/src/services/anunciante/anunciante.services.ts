import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';
import { Anunciante } from '../../models/anunciante/anunciante';
import { Usuario } from '../../models/usuario/usuario';

@Injectable({
    providedIn: 'root',
})
export class AnuncianteServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public crearNuevoAnunciante(anunciante: Anunciante): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiURL()}anunciantes`, anunciante);
    }

    public traerAnunciantes(): Observable<Usuario[]> {
        return this.httpClient.get<Usuario[]>(`${this.restConstants.getApiURL()}anunciantes`);
    }

    eliminarAnunciante(id: string): Observable<void> {
        return this.httpClient.delete<void>(
            `${this.restConstants.getApiURL()}anunciantes/${id}`);
    }

}
