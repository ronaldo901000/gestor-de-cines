import { Injectable } from '@angular/core';
import { RestConstants } from '../../shared/rest-api/rest-constants';
import { FiltroComentariosSalas } from '../../models/filtros-reportes-admin-cine/filtro-comentarios.-salas';
import { FiltroAnunciosComprados } from '../../models/filtros-reportes-admin-cine/filtro-anunciosComprados';

@Injectable({
    providedIn: 'root',
})
export class ReporteAdminSistemaServices {
    restConstants = new RestConstants();

    public generarURLReporteGananciasPorAnunciante(filtro: FiltroComentariosSalas): string {
        let url = this.restConstants.API_URL + 'reportes-admin-sistema/reporte-ganancias-anunciantes';

        if (filtro.codigoSala) {
            url += '?idAnunciante=' + filtro.codigoSala;
        }

        if (filtro.fechaInicio && filtro.fechaFin) {
            const fechaInicio = new Date(filtro.fechaInicio);
            if (!filtro.codigoSala) {
                url += '?fechaInicio=' + fechaInicio.toISOString();
            }
            else {
                url += '&fechaInicio=' + fechaInicio.toISOString();
            }
            const fechaFin = new Date(filtro.fechaFin);
            url += '&fechaFin=' + fechaFin.toISOString();
        }
        return url;
    }

    public generarURLReporteSalasPopulares(filtro: FiltroComentariosSalas): string {
        let url = this.restConstants.API_URL + 'reportes-admin-sistema/reporte-salas-mas-populares';

        if (filtro.fechaInicio && filtro.fechaFin) {
            const fechaInicio = new Date(filtro.fechaInicio);
            url += '?fechaInicio=' + fechaInicio.toISOString();
            const fechaFin = new Date(filtro.fechaFin);
            url += '&fechaFin=' + fechaFin.toISOString();
        }
        return url;
    }

    public generarURLReporteSalasComentadas(filtro: FiltroComentariosSalas): string {
        let url = this.restConstants.API_URL + 'reportes-admin-sistema/reporte-salas-mas-comentadas';

        if (filtro.fechaInicio && filtro.fechaFin) {
            const fechaInicio = new Date(filtro.fechaInicio);
            url += '?fechaInicio=' + fechaInicio.toISOString();
            const fechaFin = new Date(filtro.fechaFin);
            url += '&fechaFin=' + fechaFin.toISOString();
        }
        return url;
    }

    public generarURLReporteGanancias(filtro: FiltroComentariosSalas): string {
        let url = this.restConstants.API_URL + 'reportes-admin-sistema/reporte-ganancias';
        if (filtro.fechaInicio && filtro.fechaFin) {
            const fechaInicio = new Date(filtro.fechaInicio);
            url += '?fechaInicio=' + fechaInicio.toISOString();
            const fechaFin = new Date(filtro.fechaFin);
            url += '&fechaFin=' + fechaFin.toISOString();
        }
        return url;
    }

    public generarURLReporteAnunciosComprados(filtro: FiltroAnunciosComprados): string {
        let url = this.restConstants.API_URL + 'reportes-admin-sistema/reporte-anuncios-comprados';

        if (filtro.fechaInicio && filtro.fechaFin) {
            const fechaInicio = new Date(filtro.fechaInicio);
            const fechaFin = new Date(filtro.fechaFin);
            url += '?fechaInicio=' + fechaInicio.toISOString();
            url += '&fechaFin=' + fechaFin.toISOString();

            if (filtro.tipoAnuncio) {
                url += '&tipoAnuncio=' + filtro.tipoAnuncio;
            }
            if (filtro.periodoTiempo) {
                url += '&periodoTiempo=' + filtro.periodoTiempo;
            }
        }
        else {
            if (filtro.tipoAnuncio) {
                url += '?tipoAnuncio=' + filtro.tipoAnuncio;
                if (filtro.periodoTiempo) {
                    url += '&periodoTiempo=' + filtro.periodoTiempo;
                }
            }
            else if (filtro.periodoTiempo) {
                url += '?periodoTiempo=' + filtro.periodoTiempo;
            }
        }

        return url;
    }


}