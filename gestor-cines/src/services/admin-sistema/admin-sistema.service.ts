import { Injectable } from "@angular/core";
import { RestConstants } from "../../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { AdminSistema } from "../../models/admin-sistema/admin-sistema";
import { Observable } from "rxjs";
import { Usuario } from "../../models/usuario/usuario";

@Injectable({
    providedIn: 'root',
})

export class AdminSistemaServices {
    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public crearAdminSistema(adminSistema: AdminSistema): Observable<void> {
        return this.httpClient.post<void>
            (`${this.restConstants.getApiURL()}adminsSistema`, adminSistema);
    }

    public traerAdminsSistema(): Observable<Usuario[]> {
        return this.httpClient.get<Usuario[]>
            (`${this.restConstants.getApiURL()}adminsSistema`);
    }

    eliminarAdminSistema(id: string): Observable<void> {
        return this.httpClient.delete<void>(
            `${this.restConstants.getApiURL()}adminsSistema/${id}`);
    }
}