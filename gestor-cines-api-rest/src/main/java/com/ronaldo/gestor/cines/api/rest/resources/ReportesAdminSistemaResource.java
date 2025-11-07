package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroReporteGananciasAnunciante;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroSalasMasPopulares;
import com.ronaldo.gestor.cines.api.rest.enums.rutasJasper.RutasReportesJasper;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.reporteGananciasAnunciantes.ReporteGananciaAnunciantes;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas.SalasGustadas;
import com.ronaldo.gestor.cines.api.rest.services.reporteAdminsSistema.GeneradorFiltros;
import com.ronaldo.gestor.cines.api.rest.services.reporteAdminsSistema.GeneradorReportes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * REST Web Service
 *
 * @author ronaldo
 */
@Path("reportes-admin-sistema")
public class ReportesAdminSistemaResource {

       @Context
       private UriInfo context;

       @GET
       @Path("reporte-ganancias-anunciantes")
       @Produces(MediaType.APPLICATION_OCTET_STREAM)
       public Response generarReporteGananciasAnunciante(
               @QueryParam("idAnunciante") String idAnunciante,
               @QueryParam("fechaInicio") String fechaInicio,
               @QueryParam("fechaFin") String fechaFin) throws JRException {
              GeneradorFiltros generador = new GeneradorFiltros();
              GeneradorReportes generadorReportes = new GeneradorReportes();
              FiltroReporteGananciasAnunciante request;

              ReporteGananciaAnunciantes reporte;

              try {
                     request = generador.obtenerFiltro(idAnunciante, fechaInicio, fechaFin);
                     reporte = generadorReportes.obtenerReporteGananaciasAnunciantes(request);
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              }catch(EntityNotFoundException ex){
                     return Response.status(Response.Status.NOT_FOUND).build();
              }

              InputStream reporteCompilado = getClass().getClassLoader()
                      .getResourceAsStream(RutasReportesJasper.REPORTE_GANANCIAS_ANUNCIANTES.getRuta());

              JRDataSource source = new JRBeanCollectionDataSource(reporte.getAnuncianteaReport());

              JasperPrint printer = JasperFillManager.fillReport(reporteCompilado, null, source);

              String fileName = "reporte_ganancias_anunciantes.pdf";

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
       @Path("reporte-salas-mas-populares")
       @Produces(MediaType.APPLICATION_OCTET_STREAM)
       public Response generarReporteSalasMasPopulares(
               @QueryParam("fechaInicio") String fechaInicio,
               @QueryParam("fechaFin") String fechaFin) throws JRException {
              GeneradorFiltros generador = new GeneradorFiltros();
              GeneradorReportes generadorReportes = new GeneradorReportes();
              FiltroSalasMasPopulares request;

              List<SalasGustadas> reporte;

              try {
                     request = generador.generarFiltroSalasPopulares(fechaInicio, fechaFin);
                     reporte = generadorReportes.generarReporteSalasPopulares(request);
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              }

              InputStream reporteCompilado = getClass().getClassLoader()
                      .getResourceAsStream(RutasReportesJasper.REPORTE_SALAS_POPULARES.getRuta());

              JRDataSource source = new JRBeanCollectionDataSource(reporte);

              JasperPrint printer = JasperFillManager.fillReport(reporteCompilado, null, source);

              String fileName = "reporte_top_5_salas_populares.pdf";

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
