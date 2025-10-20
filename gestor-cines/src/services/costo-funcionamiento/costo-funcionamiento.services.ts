import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';
import { CostoFuncionamiento } from '../../models/costo-funcionamiento/costo-funcionamiento';
import { CostoFuncionamientoResponse } from '../../models/costo-funcionamiento/costo-funcionamiento-response';

@Injectable({
    providedIn: 'root',
})
export class CostoFuncionamientoServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public crearNuevoCosto(costo: CostoFuncionamiento): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiURL()}costosFuncionamiento`, costo);
    }

    public obtenerCostosPorPalabra(palabra: String): Observable<CostoFuncionamientoResponse[]> {
        return this.httpClient.get<CostoFuncionamientoResponse []>
        (`${this.restConstants.getApiURL()}costosFuncionamiento/palabraClave/${palabra}`);
    }

    public eliminarCosto(id: string): Observable<void> {
        return this.httpClient.delete<void>(
            `${this.restConstants.getApiURL()}costosFuncionamiento/${id}`);
    }

}
