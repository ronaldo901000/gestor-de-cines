import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';
import { Recarga } from '../../models/recarga/recarga';
import { UsuarioRequest } from '../../models/usuario/usuario-request';
import { Credencial } from '../../models/credencial/credencial';
import { PropiedadesUsuario } from '../../models/propiedades-usuario/propiedades-usuario';
import { Usuario } from '../../models/usuario/usuario';
import { Opinion } from '../../models/opinion/opinion';
import { OpinionResponse } from '../../models/opinion/opinion-response';

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

    public iniciarSesion(credencial: Credencial): Observable<PropiedadesUsuario> {
        return this.httpClient.post<PropiedadesUsuario>(
            `${this.restConstants.getApiURL()}autenticaciones/login`, credencial
        );
    }

    public obtenerUsuario(idUsuario: string): Observable<Usuario> {
        return this.httpClient.get<Usuario>(
            `${this.restConstants.getApiURL()}usuarios/usuario/${idUsuario}`);
    }

    public actualizarCuenta(usuario: UsuarioRequest): Observable<UsuarioRequest> {
        return this.httpClient.put<UsuarioRequest>(
            `${this.restConstants.getApiURL()}usuarios/actualizacion`, usuario
        );
    }

    public publicarOpinion(opinion: Opinion): Observable<PropiedadesUsuario> {
        return this.httpClient.post<PropiedadesUsuario>(
            `${this.restConstants.getApiURL()}opiniones`, opinion
        );
    }

    public obtenerOpiniones(codigo: string, esPelicula: boolean): Observable<OpinionResponse[]> {
        return this.httpClient.get<OpinionResponse[]>(
            `${this.restConstants.getApiURL()}opiniones/${codigo}/${esPelicula}`);
    }
}