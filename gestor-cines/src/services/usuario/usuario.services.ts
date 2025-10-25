import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class UsuarioServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public obtenerSaldoActual(idUsuario: string): Observable<number> {
        return this.httpClient.get<number>(`${this.restConstants.getApiURL()}usuarios/saldo/${idUsuario}`);
    }

}