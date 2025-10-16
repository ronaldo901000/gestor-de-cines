import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Observable } from 'rxjs';
import { Usuario } from '../../models/usuario/usuario';
import { AdminCine } from '../../models/admin-cine/admin-cine';

@Injectable({
    providedIn: 'root',
})

export class AdminCinesServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public crearAdminCine(adminCine: AdminCine): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiURL()}adminsCines`, adminCine);
    }

    public traerAdminsCine(codigoCine: string): Observable<Usuario[]> {
        return this.httpClient.get<Usuario[]>(`${this.restConstants.getApiURL()}adminsCines/${codigoCine}`);
    }

    eliminarAdminCine(id: string): Observable<void> {
        return this.httpClient.delete<void>(
            `${this.restConstants.getApiURL()}adminsCines/${id}`);
    }

}
