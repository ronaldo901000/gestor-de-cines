import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';
import { Recarga } from '../../models/recarga/recarga';
import { UsuarioRequest } from '../../models/usuario/usuario-request';

@Injectable({
    providedIn: 'root',
})
export class UsuarioServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public obtenerSaldoActual(idUsuario: string): Observable<number> {
        return this.httpClient.get<number>(`${this.restConstants.getApiURL()}usuarios/saldo/${idUsuario}`);
    }

    public recargarCartera(recarga: Recarga): Observable<number> {
        return this.httpClient.put<number>(
            `${this.restConstants.getApiURL()}usuarios/recargas`, recarga
        );
    }

    public crearCuenta(usuario: UsuarioRequest): Observable<void> {
        return this.httpClient.post<void>(
            `${this.restConstants.getApiURL()}usuarios`, usuario
        );
    }

}