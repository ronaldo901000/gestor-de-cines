package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminCine.FiltroComentariosSalasRequest;
import com.ronaldo.gestor.cines.api.rest.enums.rutasJasper.RutasReportesJasper;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.reporteComentarios.ReporteComentarios;
import com.ronaldo.gestor.cines.api.rest.models.reportePeliculasProyectadas.ReportePeliculasProyectadas;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas.ReporteSalasGustadas;
import com.ronaldo.gestor.cines.api.rest.services.reporteAdminsCines.CreadorReportesAdminCine;
import com.ronaldo.gestor.cines.api.rest.services.reporteAdminsCines.CreadorRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author ronaldo
 */
@Path("reportes-admin-cine")
public class ReportesAdminCineResource {

       @Context
       private UriInfo context;

       @GET
       @Path("reporte-comentarios-salas")
       @Produces(MediaType.APPLICATION_OCTET_STREAM)
       public Response generarReporteComentariosSalas(
               @QueryParam("codigoCine") String codigoCine,
               @QueryParam("codigoSala") String codigoSala,
               @QueryParam("fechaInicio") String fechaInicio,
               @QueryParam("fechaFin") String fechaFin
       ) throws JRException {

              CreadorRequest creadorRequest = new CreadorRequest();
              FiltroComentariosSalasRequest request = creadorRequest.obtenerRequest(codigoCine, codigoSala, fechaInicio, fechaFin);

              CreadorReportesAdminCine creador = new CreadorReportesAdminCine();
              ReporteComentarios reporte;

              try {
                     reporte = creador.obtenerReporteComentarios(request);
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).build();
              }

              InputStream reporteCompilado = getClass().getClassLoader()
                      .getResourceAsStream(RutasReportesJasper.REPORTE_COMENTARIOS_DE_SALAS.getRuta());

              JRDataSource source = new JRBeanCollectionDataSource(reporte.getComentarios());

              Map<String, Object> parametros = new HashMap<>();
              parametros.put("nombreCine", reporte.getNombreCine());
              parametros.put("ubicacion", reporte.getUbicacion());
              parametros.put("ds", source);

              JasperPrint printer = JasperFillManager.fillReport(reporteCompilado, parametros, new JREmptyDataSource());

              String fileName = "reporte_comentarios_salas.pdf";

              StreamingOutput fileStream = (java.io.OutputStream output) -> {
                     try {
                            JasperExportManager.exportReportToPdfStream(printer, output);
                            output.flush();
                     } catch (Exception e) {
                            throw new WebApplicationException("Error al generar el reporte");
                     }
              };

              return Response
                      .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                      .header("content-disposition", "attachment; filename=" + fileName)
                      .build();
       }

       
       
       @GET
       @Path("reporte-peliculas-proyectadas")
       @Produces(MediaType.APPLICATION_OCTET_STREAM)
       public Response generarReportePeliculasProyectadas(
               @QueryParam("codigoCine") String codigoCine,
               @QueryParam("codigoSala") String codigoSala,
               @QueryParam("fechaInicio") String fechaInicio,
               @QueryParam("fechaFin") String fechaFin
       ) throws JRException {
              CreadorRequest creadorRequest = new CreadorRequest();
              FiltroComentariosSalasRequest request = creadorRequest.obtenerRequest(codigoCine, codigoSala, fechaInicio, fechaFin);

              CreadorReportesAdminCine creador = new CreadorReportesAdminCine();
              ReportePeliculasProyectadas reporte;

              try {
                     reporte = creador.obtenerReporteProyeccioens(request);
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).build();
              }

              InputStream reporteCompilado = getClass().getClassLoader()
                      .getResourceAsStream(RutasReportesJasper.REPORTE_PELICULAS_PROYECTADAS.getRuta());
              JRDataSource source = new JRBeanCollectionDataSource(reporte.getInfoSalas());

              Map<String, Object> parametros = new HashMap<>();
              parametros.put("nombreCine", reporte.getCine().getNombre());
              parametros.put("ubicacion", reporte.getCine().getUbicacion());

              JasperPrint printer = JasperFillManager.fillReport(reporteCompilado, parametros, source);

              String fileName = "reporte_peliculas_proyectadas.pdf";

              StreamingOutput fileStream = (java.io.OutputStream output) -> {
                     try {
                            JasperExportManager.exportReportToPdfStream(printer, output);
                            output.flush();
                     } catch (Exception e) {
                            throw new WebApplicationException("Error al generar el reporte");
                     }
              };

              return Response
                      .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                      .header("content-disposition", "attachment; filename=" + fileName)
                      .build();
       }
       
       @GET
       @Path("reporte-top-salas-gustadas")
       @Produces(MediaType.APPLICATION_OCTET_STREAM)
       public Response generarREporte5SalasMasGustadas(
               @QueryParam("codigoCine") String codigoCine,
               @QueryParam("codigoSala") String codigoSala,
               @QueryParam("fechaInicio") String fechaInicio,
               @QueryParam("fechaFin") String fechaFin
       ) throws JRException {
              CreadorRequest creadorRequest = new CreadorRequest();
              FiltroComentariosSalasRequest request = creadorRequest.obtenerRequest(codigoCine, codigoSala, fechaInicio, fechaFin);

              CreadorReportesAdminCine creador = new CreadorReportesAdminCine();
              ReporteSalasGustadas reporte;

              try {
                     reporte = creador.obtenerReporteSalasGustadas(request);
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (EntityNotFoundException ex) {
                     return Response.status(Response.Status.NOT_FOUND).build();
              }

              InputStream reporteCompilado = getClass().getClassLoader()
                      .getResourceAsStream(RutasReportesJasper.REPORTE_TOP_SALAS_GUSTADAS.getRuta());
              JRDataSource source = new JRBeanCollectionDataSource(reporte.getSalasGustadas());

              Map<String, Object> parametros = new HashMap<>();
              parametros.put("nombreCine", reporte.getCine().getNombre());
              parametros.put("ubicacion", reporte.getCine().getUbicacion());

              JasperPrint printer = JasperFillManager.fillReport(reporteCompilado, parametros, source);

              String fileName = "reporte_top5_salas_gustadas.pdf";

              StreamingOutput fileStream = (java.io.OutputStream output) -> {
                     try {
                            JasperExportManager.exportReportToPdfStream(printer, output);
                            output.flush();
                     } catch (Exception e) {
                            throw new WebApplicationException("Error al generar el reporte");
                     }
              };

              return Response
                      .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                      .header("content-disposition", "attachment; filename=" + fileName)
                      .build();
       }

}
