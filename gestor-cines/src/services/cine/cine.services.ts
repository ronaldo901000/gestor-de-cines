import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Cine } from '../../models/cine/cine';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class CineServices {
    restConstants = new RestConstants();
    private inicio: number = 0;
    private rango: number = 5;
    protected esSiguiente!: boolean;

    constructor(private httpClient: HttpClient) { }

    public crearNuevoCine(cine: Cine): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiURL()}cines`, cine);
    }

public traerCinesPorPagina(inicio: number): Observable<Cine[]> {
    return this.httpClient.get<Cine[]>(`${this.restConstants.getApiURL()}cines/pagina/${inicio}`);
}

public traerCinesPorPalabraClave(palabraClave: String): Observable<Cine[]> {
    return this.httpClient.get<Cine[]>(`${this.restConstants.getApiURL()}cines/palabraClave/${palabraClave}`);
}

}
