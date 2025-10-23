import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';
import { Sala } from '../../models/sala/sala';
import { SalaResponse } from '../../models/sala/sala-response';
import { Proyeccion } from '../../models/proyeccion/proyeccion';
import { ProyeccionResponse } from '../../models/proyeccion/proyeccion-response';

@Injectable({
    providedIn: 'root',
})
export class ProyeccionesServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public crearProyeccion(proyeccion: Proyeccion): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiURL()}proyecciones`, proyeccion);
    }

    public obtenerProyeccionesPorPagina(codigoCine: string, inicio: number): Observable<ProyeccionResponse[]> {
        return this.httpClient.get<ProyeccionResponse[]>
            (`${this.restConstants.getApiURL()}proyecciones/${codigoCine}/${inicio}`);
    }

    public obtenerProyecion(codigo: String): Observable<ProyeccionResponse> {
        return this.httpClient.get<ProyeccionResponse>(`${this.restConstants.getApiURL()}proyecciones/${codigo}`);
    }

    public actualizarProyeccion(proyeccion: Proyeccion): Observable<ProyeccionResponse> {
        return this.httpClient.put<ProyeccionResponse>(
            `${this.restConstants.getApiURL()}proyecciones`, proyeccion);
    }

    eliminarProyeccion(codigo: string): Observable<void> {
        return this.httpClient.delete<void>(
            `${this.restConstants.getApiURL()}proyecciones/${codigo}`);
    }

}