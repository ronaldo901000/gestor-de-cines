package com.ronaldo.gestor.cines.api.rest.services.reporteAdminsSistema;

import com.ronaldo.gestor.cines.api.rest.db.anunciantes.AnunciantesDB;
import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioResponse;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroReporteGananciasAnunciante;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.models.reporteGananciasAnunciantes.AnuncianteReport;
import com.ronaldo.gestor.cines.api.rest.models.reporteGananciasAnunciantes.ReporteGananciaAnunciantes;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class GeneradorReportes {

       public ReporteGananciaAnunciantes obtenerReporteGananaciasAnunciantes(
               FiltroReporteGananciasAnunciante filtro) throws DataBaseException, EntityNotFoundException {
              
              ReporteGananciaAnunciantes reporte = new ReporteGananciaAnunciantes();
              GeneradorAnunciosAnunciante generador = new GeneradorAnunciosAnunciante();
              AnunciantesDB anunciantesDB = new AnunciantesDB();

              if(!filtro.esAnunciante()){
                     throw new EntityNotFoundException("El usuario no es anunciante");
              }
              List<AnuncianteReport> anunciantesReport = new ArrayList<>();

              //se buscan los anuncios de un solo anunciante
              if (StringUtils.isNotBlank(filtro.getIdAnunciante())) {
                     List<AnuncioResponse> anuncios = generador.obtenerAnunciosPorAnunciante(filtro);
                     AnuncianteReport anunciante = new AnuncianteReport();
                     anunciante.setAnuncios(anuncios);
                     anunciante.setUsuario(generador.generarUsuarioAnunciante(filtro.getIdAnunciante()));
                     anunciantesReport.add(anunciante);
              } else {
                     List<Usuario> anunciantes = anunciantesDB.obtenerAnunciantes();
                     for (int i = 0; i < anunciantes.size(); i++) {
                            filtro.setIdAnunciante(anunciantes.get(i).getId());
                            List<AnuncioResponse> anuncios = generador.obtenerAnunciosPorAnunciante(filtro);
                            AnuncianteReport anunciante = new AnuncianteReport();
                            anunciante.setAnuncios(anuncios);
                            anunciante.setUsuario(anunciantes.get(i));
                            if (!anunciante.getAnuncios().isEmpty()) {
                                   anunciantesReport.add(anunciante);
                            }

                     }
              }
              reporte.setAnuncianteaReport(anunciantesReport);
              return reporte;
       }

}
