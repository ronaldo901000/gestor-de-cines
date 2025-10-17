import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { Cine } from '../../models/cine/cine';
import { Observable } from 'rxjs';
import { Categoria } from '../../models/categoria/categoria';
import { CategoriaResponse } from '../../models/categoria/categoria-response';

@Injectable({
    providedIn: 'root',
})
export class CategoriaService {

    restConstants = new RestConstants();

    constructor(private httpClient: HttpClient) { }

    public crearCategoria(categoria: Categoria): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiURL()}categorias`, categoria);
    }

    public traerCategorias(): Observable<CategoriaResponse[]> {
        return this.httpClient.get<CategoriaResponse[]>(`${this.restConstants.getApiURL()}categorias`);
    }

}
