package com.ronaldo.gestor.cines.api.rest.services.reporteAdminsSistema;

import com.ronaldo.gestor.cines.api.rest.db.anunciantes.AnunciantesDB;
import com.ronaldo.gestor.cines.api.rest.db.reportes.ReportesAdminCineDB;
import com.ronaldo.gestor.cines.api.rest.db.reportes.ReportesAdminSistemaDB;
import com.ronaldo.gestor.cines.api.rest.db.salas.SalasDB;
import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioResponse;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroReporteGananciasAnunciante;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroSalasMasPopulares;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.models.filtrosReportes.FiltroReportesAdminCine;
import com.ronaldo.gestor.cines.api.rest.models.reporteBoletosVendidos.CompraBoleto;
import com.ronaldo.gestor.cines.api.rest.models.reporteBoletosVendidos.CompraBoletosSala;
import com.ronaldo.gestor.cines.api.rest.models.reporteGananciasAnunciantes.AnuncianteReport;
import com.ronaldo.gestor.cines.api.rest.models.reporteGananciasAnunciantes.ReporteGananciaAnunciantes;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas.Calificacion;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas.SalasGustadas;
import com.ronaldo.gestor.cines.api.rest.models.salas.Sala;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import com.ronaldo.gestor.cines.api.rest.services.reporteAdminsCines.CalculadorTopSalasGustadas;
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

       /**
        * 
        * @param filtro
        * @return
        * @throws DataBaseException 
        */
       public List<SalasGustadas> generarReporteSalasPopulares(FiltroSalasMasPopulares filtro) throws DataBaseException {
              ReportesAdminSistemaDB reportesAdminSistemaDB = new ReportesAdminSistemaDB();
              List<SalasGustadas> salasPopulares = obtenerSalasPopulares(filtro);
              for (int i = 0; i < salasPopulares.size(); i++) {
                     salasPopulares.get(i).setBoletosComprados(reportesAdminSistemaDB.obtenerBoletosComprados(
                             salasPopulares.get(i).getSala().getCodigo())
                     );
              }
              return salasPopulares;
       }

       private List<SalasGustadas> obtenerSalasPopulares(FiltroSalasMasPopulares filtro) throws DataBaseException {

              SalasDB salasDB = new SalasDB();
              ReportesAdminSistemaDB reportesAdminSistemaDB = new ReportesAdminSistemaDB();
              CalculadorTopSalasGustadas calculador = new CalculadorTopSalasGustadas();
              List<SalasGustadas> salasGustadas = new ArrayList<>();

              List<Sala> salas = salasDB.obtenerTodasLasSalas();

              for (int i = 0; i < salas.size(); i++) {
                     SalasGustadas salasGustada = generarSalaGustada(salas.get(i), filtro, reportesAdminSistemaDB);
                     if (!salasGustada.getCalificaciones().isEmpty()) {
                            salasGustadas.add(salasGustada);
                     }
              }
              List<SalasGustadas> topCincoPopulares = calculador.obtenerTopSalasGustadas(salasGustadas);
              
              return topCincoPopulares;

       }

       /**
        *
        * @param sala
        * @param filtro
        * @param reportesAdminSistemaDB
        * @return
        * @throws DataBaseException
        */
       private SalasGustadas generarSalaGustada(Sala sala, FiltroSalasMasPopulares filtro, ReportesAdminSistemaDB reporteAdminSistemaDB)
               throws DataBaseException {
              filtro.generarQuery();
              List<Calificacion> calificaciones = reporteAdminSistemaDB.obtenerCalificacionesSala(sala.getCodigo(), filtro);
              SalasGustadas salasGustadas = new SalasGustadas();
              salasGustadas.setSala(sala);
              salasGustadas.setCalificaciones(calificaciones);
              return salasGustadas;
       }

}
