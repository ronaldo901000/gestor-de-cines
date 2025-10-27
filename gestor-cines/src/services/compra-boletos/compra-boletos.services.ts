import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';
import { CompraBoletos } from '../../models/compraBoletos/compra-boletos';

@Injectable({
    providedIn: 'root',
})
export class CompraBoletosServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public crearNuevaCompra(compra: CompraBoletos): Observable<void> {
        return this.httpClient.post<void>
        (`${this.restConstants.getApiURL()}compraBoletos`, compra);
    }
}
