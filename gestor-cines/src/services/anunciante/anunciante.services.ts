import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Cine } from '../../models/cine/cine';
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
