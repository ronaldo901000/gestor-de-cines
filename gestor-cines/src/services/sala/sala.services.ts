import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';
import { Sala } from '../../models/sala/sala';
import { SalaResponse } from '../../models/sala/sala-response';

@Injectable({
    providedIn: 'root',
})
export class SalasServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public crearSala(sala: Sala): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiURL()}salas`, sala);
    }

    public traerSalasPorPagina(codigoCine: string, inicio: number): Observable<SalaResponse[]> {
        return this.httpClient.get<SalaResponse[]>
            (`${this.restConstants.getApiURL()}salas/${codigoCine}/${inicio}`);
    }

    public obtenerSala(codigo: String): Observable<SalaResponse> {
        return this.httpClient.get<SalaResponse>(`${this.restConstants.getApiURL()}salas/${codigo}`);
    }

    public actualizarSala(sala: Sala): Observable<SalaResponse> {
        return this.httpClient.put<SalaResponse>(
            `${this.restConstants.getApiURL()}salas`, sala);
    }
    public actualizarVisibilidadSala(codigoSala: string): Observable<void> {
        return this.httpClient.put<void>(
            `${this.restConstants.getApiURL()}salas/cambiarVisibilidad/${codigoSala}`,{});
    }

    eliminarSala(codigo: string): Observable<void> {
        return this.httpClient.delete<void>(
            `${this.restConstants.getApiURL()}salas/${codigo}`);
    }

}