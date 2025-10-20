import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';
import { CostoGlobal } from '../../models/costo-funcionamiento/costo-global';

@Injectable({
    providedIn: 'root',
})
export class CostoGlobalServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public obtenerCostoGlobal(): Observable<CostoGlobal> {
        return this.httpClient.get<CostoGlobal>
            (`${this.restConstants.getApiURL()}costoGlobal`);
    }

    public actualizarCosto(costo: CostoGlobal): Observable<void> {
        return this.httpClient.put<void>(
            `${this.restConstants.getApiURL()}costoGlobal`, costo);
    }


}
