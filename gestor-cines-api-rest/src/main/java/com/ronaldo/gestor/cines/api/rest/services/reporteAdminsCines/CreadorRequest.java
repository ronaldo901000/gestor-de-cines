package com.ronaldo.gestor.cines.api.rest.services.reporteAdminsCines;

import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminCine.FiltroComentariosSalasRequest;

/**
 *
 * @author ronaldo
 */
public class CreadorRequest {

       public FiltroComentariosSalasRequest obtenerRequest(String codigoCine,
               String codigoSala, String fechaInicio, String fechaFin) {
              
              FiltroComentariosSalasRequest request = new FiltroComentariosSalasRequest();
              
              request.setCodigoCine(codigoCine);
              request.setCodigoSala(codigoSala);
              request.setFechaInicio(fechaInicio);
              request.setFechaFin(fechaFin);
              return request;
       }
}
