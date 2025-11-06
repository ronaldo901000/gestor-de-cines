import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';
import { Sala } from '../../models/sala/sala';
import { SalaResponse } from '../../models/sala/sala-response';
import { FiltroComentariosSalas } from '../../models/filtros-reportes-admin-cine/filtro-comentarios.-salas';

@Injectable({
    providedIn: 'root',
})
export class ReporteAdminCineServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public crearReporteComentariosSalas(filtro: FiltroComentariosSalas): Observable<Blob> {
        return this.httpClient.post(
            `${this.restConstants.getApiURL()}reportes-admin-cine/reporte-comentarios-salas`,
            filtro, { responseType: 'blob' }
        );
    }

    public generarURLReporteComentariosSalas(filtro: FiltroComentariosSalas, endPoint:string): string {
        let url = this.restConstants.API_URL + 'reportes-admin-cine/'+endPoint+'?codigoCine=' + filtro.codigoCine;

        if (filtro.codigoSala) {
            url += '&codigoSala=' + filtro.codigoSala;
        }

        if (filtro.fechaInicio && filtro.fechaFin) {
            const fechaInicio = new Date(filtro.fechaInicio);
            url += '&fechaInicio=' + fechaInicio.toISOString();
            const fechaFin = new Date(filtro.fechaFin);
            url += '&fechaFin=' + fechaFin.toISOString();
        }

        return url;
    }

}