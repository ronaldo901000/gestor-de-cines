package com.ronaldo.gestor.cines.api.rest.services.reporteAdminsSistema;

import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroAnunciosComprados;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroGanancias;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroReporteGananciasAnunciante;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroSalasMasPopulares;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import org.apache.commons.lang3.StringUtils;

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

       public FiltroSalasMasPopulares generarFiltroSalasPopulares(
               String fechaInicio, String fechaFin) throws UserDataInvalidException {
              FiltroSalasMasPopulares filtro = new FiltroSalasMasPopulares();
              filtro.setFechaInicio(fechaInicio);
              filtro.setFechaFin(fechaFin);

              filtro.verificarFechas();
              return filtro;
       }

       public FiltroGanancias generarFiltroGanancias(
               String fechaInicio, String fechaFin) throws UserDataInvalidException {
              FiltroGanancias filtro = new FiltroGanancias();
              filtro.setFechaInicio(fechaInicio);
              filtro.setFechaFin(fechaFin);

              filtro.verificarFechas();
              return filtro;
       }

       public FiltroAnunciosComprados generarFiltroAnunciosComprados(
               String fechaInicio, String fechaFin, String tipoAnuncio, String periodoTiempo) throws UserDataInvalidException {

              FiltroAnunciosComprados filtro = new FiltroAnunciosComprados();
              filtro.setFechaInicio(fechaInicio);
              filtro.setFechaFin(fechaFin);
              filtro.setTipoAnuncio(tipoAnuncio);
              try {
                     if (StringUtils.isNotBlank(periodoTiempo)) {
                            filtro.setPeriodoTiempo(Integer.parseInt(periodoTiempo));
                     }

              } catch (NumberFormatException e) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }

              filtro.verificarFechas();
              return filtro;
       }

}
