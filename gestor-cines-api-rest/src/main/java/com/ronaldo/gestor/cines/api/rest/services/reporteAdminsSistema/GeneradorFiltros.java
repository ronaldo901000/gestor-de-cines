package com.ronaldo.gestor.cines.api.rest.services.reporteAdminsSistema;

import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroReporteGananciasAnunciante;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;

/**
 *
 * @author ronaldo
 */
public class GeneradorFiltros {

       /**
        * 
        * @param idAnunciante
        * @param fechaInicio
        * @param fechaFin
        * @return
        * @throws UserDataInvalidException 
        */
       public FiltroReporteGananciasAnunciante obtenerFiltro(String idAnunciante,
               String fechaInicio, String fechaFin) throws UserDataInvalidException {
              FiltroReporteGananciasAnunciante filtro = new FiltroReporteGananciasAnunciante();
              filtro.setIdAnunciante(idAnunciante);
              filtro.setFechaInicio(fechaInicio);
              filtro.setFechaFin(fechaFin);

              filtro.verificarFechas();
              return filtro;
       }

}
