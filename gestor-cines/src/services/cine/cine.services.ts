import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Cine } from '../../models/cine/cine';
import { Observable } from 'rxjs';
import { RecargaCine } from '../../models/recarga/recarga-cine';

@Injectable({
    providedIn: 'root',
})
export class CineServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public crearNuevoCine(cine: Cine): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiURL()}cines`, cine);
    }

    public traerCinesPorPagina(inicio: number): Observable<Cine[]> {
        return this.httpClient.get<Cine[]>(`${this.restConstants.getApiURL()}cines/pagina/${inicio}`);
    }

    public traerCinesPorPalabraClave(palabraClave: string): Observable<Cine[]> {
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

    recargarSaldo(recarga: RecargaCine): Observable<number> {
        return this.httpClient.put<number>(
            `${this.restConstants.getApiURL()}cines/recargas`, recarga
        );
    }

    public obtenerSaldoActual(codigoCine: string): Observable<number> {
        return this.httpClient.get<number>(`${this.restConstants.getApiURL()}cines/saldo-actual/${codigoCine}`);
    }

    public tieneBloqueadorAnuncios(codigoCine: string): Observable<boolean> {
        return this.httpClient.get<boolean>(`${this.restConstants.getApiURL()}cines/bloqueadores/${codigoCine}`);
    }
}
