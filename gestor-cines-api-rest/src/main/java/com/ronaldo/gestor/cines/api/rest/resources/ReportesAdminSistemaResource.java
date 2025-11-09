package com.ronaldo.gestor.cines.api.rest.resources;

import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioResponse;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroAnunciosComprados;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroGanancias;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroReporteGananciasAnunciante;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroSalasMasPopulares;
import com.ronaldo.gestor.cines.api.rest.enums.rutasJasper.RutasReportesJasper;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.reporteGanancias.ReporteGanancias;
import com.ronaldo.gestor.cines.api.rest.models.reporteGananciasAnunciantes.ReporteGananciaAnunciantes;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasComentadas.ReporteSalasComentadas;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
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

       @GET
       @Path("reporte-salas-mas-comentadas")
       @Produces(MediaType.APPLICATION_OCTET_STREAM)
       public Response generarReporteSalasMasComentadas(
               @QueryParam("fechaInicio") String fechaInicio,
               @QueryParam("fechaFin") String fechaFin) throws JRException {

              GeneradorFiltros generador = new GeneradorFiltros();
              GeneradorReportes generadorReportes = new GeneradorReportes();

              FiltroSalasMasPopulares request;

              ReporteSalasComentadas reporte;

              try {
                     request = generador.generarFiltroSalasPopulares(fechaInicio, fechaFin);
                     reporte = generadorReportes.obtenerReporteSalasComentadas(request);
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              }

              InputStream reporteCompilado = getClass().getClassLoader()
                      .getResourceAsStream(RutasReportesJasper.REPORTE_SALAS_COMENTADAS.getRuta());

              JRDataSource source = new JRBeanCollectionDataSource(reporte.getSalasComentadas());

              JasperPrint printer = JasperFillManager.fillReport(reporteCompilado, null, source);

              String fileName = "reporte_top_5_salas_comentadas.pdf";

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
       @Path("reporte-ganancias")
       @Produces(MediaType.APPLICATION_OCTET_STREAM)
       public Response generarReporteGanancias(
               @QueryParam("fechaInicio") String fechaInicio,
               @QueryParam("fechaFin") String fechaFin) throws JRException {

              GeneradorFiltros generador = new GeneradorFiltros();
              GeneradorReportes generadorReportes = new GeneradorReportes();

              FiltroGanancias request;

              ReporteGanancias reporte;

              try {
                     request = generador.generarFiltroGanancias(fechaInicio, fechaFin);
                     reporte = generadorReportes.generarReporteGanancias(request);
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              }

              InputStream reporteCompilado = getClass().getClassLoader()
                      .getResourceAsStream(RutasReportesJasper.REPORTE_GANANCIAS.getRuta());

              JRDataSource sourceCostos = new JRBeanCollectionDataSource(reporte.getCostosCines());
              JRDataSource sourceAnuncios = new JRBeanCollectionDataSource(reporte.getAnuncios());
              JRDataSource sourcePagosBloqueo = new JRBeanCollectionDataSource(reporte.getPagosBloqueo());
              Map<String, Object> parametros = new HashMap<>();
              parametros.put("costosCines", sourceCostos);
              parametros.put("anuncios", sourceAnuncios);
              parametros.put("pagosBloqueo", sourcePagosBloqueo);
              parametros.put("costos", reporte.getBalanceFinanciero().getCostos());
              parametros.put("ingresos", reporte.getBalanceFinanciero().getIngresos());
              parametros.put("ganancias", reporte.getBalanceFinanciero().getGanancia());
              parametros.put("fechaInicio", request.getFechaInicio());
              parametros.put("fechaFin", request.getFechaFin());
              JasperPrint printer = JasperFillManager.fillReport(reporteCompilado, parametros, new JREmptyDataSource());

              String fileName = "reporte_ganancias.pdf";

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
       @Path("reporte-anuncios-comprados")
       @Produces(MediaType.APPLICATION_OCTET_STREAM)
       public Response generarReporteAnunciosComprados(
               @QueryParam("fechaInicio") String fechaInicio,
               @QueryParam("fechaFin") String fechaFin,
               @QueryParam("tipoAnuncio") String tipoAnuncio,
               @QueryParam("periodoTiempo") String periodoTiempo
       ) throws JRException {

              GeneradorFiltros generador = new GeneradorFiltros();
              GeneradorReportes generadorReportes = new GeneradorReportes();

              FiltroAnunciosComprados request;

              List<AnuncioResponse> listaAnuncios;

              try {
                     request = generador.generarFiltroAnunciosComprados(fechaInicio, fechaFin, tipoAnuncio, periodoTiempo);
                     listaAnuncios = generadorReportes.obtenerReporteAnuncios(request);
              } catch (DataBaseException ex) {
                     return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
              } catch (UserDataInvalidException ex) {
                     return Response.status(Response.Status.BAD_REQUEST).build();
              }

              InputStream reporteCompilado = getClass().getClassLoader()
                      .getResourceAsStream(RutasReportesJasper.REPORTE_ANUNCIOS_COMPRADOS.getRuta());

              JRDataSource source = new JRBeanCollectionDataSource(listaAnuncios);

              Map<String, Object> parametros = new HashMap<>();
              parametros.put("anuncios", source);

              JasperPrint printer = JasperFillManager.fillReport(reporteCompilado, parametros, new JREmptyDataSource());

              String fileName = "reporte_anuncios_comprados.pdf";

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
