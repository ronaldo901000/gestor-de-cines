package com.ronaldo.gestor.cines.api.rest.services.reporteAdminsCines;

import com.ronaldo.gestor.cines.api.rest.db.cines.CinesDB;
import com.ronaldo.gestor.cines.api.rest.db.reportes.ReportesAdminCineDB;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminCine.FiltroComentariosSalasRequest;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import com.ronaldo.gestor.cines.api.rest.models.filtrosReportes.FiltroComentariosSalas;
import com.ronaldo.gestor.cines.api.rest.models.reporteComentarios.ReporteComentarios;
import java.util.Optional;

/**
 *
 * @author ronaldo
 */
public class CreadorReportesAdminCine {

       /**
        *
        * @param request
        * @return
        * @throws UserDataInvalidException
        * @throws DataBaseException
        * @throws EntityNotFoundException
        */
       public ReporteComentarios obtenerReporteComentarios(FiltroComentariosSalasRequest request)
               throws UserDataInvalidException, DataBaseException, EntityNotFoundException {

              CinesDB cinesDB = new CinesDB();
              ReportesAdminCineDB reportesAdminCineDB = new ReportesAdminCineDB();
              ReporteComentarios reporte = new ReporteComentarios();

              FiltroComentariosSalas filtro = extraer(request);

              filtro.existeCineYSala();

              //se hace la consulta a la db para obtener cine y comentarios
              filtro.generarQuery();
              Optional<Cine> cine = cinesDB.obtenerCinePorCodigo(filtro.getCodigoCine());

              reporte.setCine(cine.get());
              reporte.setComentarios(reportesAdminCineDB.obtenerComentarios(filtro));

              return reporte;
       }

       private FiltroComentariosSalas extraer(FiltroComentariosSalasRequest request) throws UserDataInvalidException {
              FiltroComentariosSalas filtro = new FiltroComentariosSalas();
              filtro.setCodigoCine(request.getCodigoCine());
              filtro.setCodigoSala(request.getCodigoSala());
              filtro.setFechaInicio(request.getFechaInicio());
              filtro.setFechaFin(request.getFechaFin());

              filtro.datoaValidos();
              return filtro;
       }
}
