package com.ronaldo.gestor.cines.api.rest.services.reporteAdminsCines;

import com.ronaldo.gestor.cines.api.rest.db.cines.CinesDB;
import com.ronaldo.gestor.cines.api.rest.db.reportes.ReportesAdminCineDB;
import com.ronaldo.gestor.cines.api.rest.db.salas.SalasDB;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminCine.FiltroComentariosSalasRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.proyecciones.ProyeccionResponse;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import com.ronaldo.gestor.cines.api.rest.models.filtrosReportes.FiltroReportesAdminCine;
import com.ronaldo.gestor.cines.api.rest.models.filtrosReportes.QueryFiltro;
import com.ronaldo.gestor.cines.api.rest.models.reporteBoletosVendidos.CompraBoleto;
import com.ronaldo.gestor.cines.api.rest.models.reporteBoletosVendidos.CompraBoletosSala;
import com.ronaldo.gestor.cines.api.rest.models.reporteBoletosVendidos.ReporteBoletosVendidos;
import com.ronaldo.gestor.cines.api.rest.models.reporteComentarios.ReporteComentarios;
import com.ronaldo.gestor.cines.api.rest.models.reportePeliculasProyectadas.ProyeccionesSala;
import com.ronaldo.gestor.cines.api.rest.models.reportePeliculasProyectadas.ReportePeliculasProyectadas;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas.Calificacion;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas.ReporteSalasGustadas;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas.SalasGustadas;
import com.ronaldo.gestor.cines.api.rest.models.salas.Sala;
import com.ronaldo.gestor.cines.api.rest.services.proyecciones.CreadorProyeccionesResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

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

              FiltroReportesAdminCine filtro = extraer(request);

              filtro.existeCineYSala();

              List<String> querys = new ArrayList<>();
              querys.add(QueryFiltro.BUSCAR_COMENTARIOS_FILTRO_COMPLETO.getQuery());
              querys.add(QueryFiltro.BUSCAR_COMENTARIOS_FILTRO_NULO.getQuery());
              querys.add(QueryFiltro.BUSCAR_COMENTARIOS_FILTRO_SALA.getQuery());
              querys.add(QueryFiltro.BUSCAR_COMENTARIOS_FILTRO_FECHAS.getQuery());
              filtro.generarQuery(querys);
              //se hace la consulta a la db para obtener cine y comentarios
              Optional<Cine> cine = cinesDB.obtenerCinePorCodigo(filtro.getCodigoCine());

              reporte.setCine(cine.get());
              reporte.setComentarios(reportesAdminCineDB.obtenerComentarios(filtro));

              return reporte;
       }

       private FiltroReportesAdminCine extraer(FiltroComentariosSalasRequest request) throws UserDataInvalidException {
              FiltroReportesAdminCine filtro = new FiltroReportesAdminCine();
              filtro.setCodigoCine(request.getCodigoCine());
              filtro.setCodigoSala(request.getCodigoSala());
              filtro.setFechaInicio(request.getFechaInicio());
              filtro.setFechaFin(request.getFechaFin());

              filtro.datoaValidos();
              return filtro;
       }

       public ReportePeliculasProyectadas obtenerReporteProyeccioens(FiltroComentariosSalasRequest request) throws UserDataInvalidException,
               DataBaseException, EntityNotFoundException {

              SalasDB salasDB = new SalasDB();
              CinesDB cinesDB = new CinesDB();
              ReportePeliculasProyectadas reporte = new ReportePeliculasProyectadas();

              FiltroReportesAdminCine filtro = extraer(request);

              filtro.existeCineYSala();

              //se cargan las posibles querys para la db y se crea la que indique el usuario
              List<String> querys = new ArrayList<>();
              querys.add(QueryFiltro.BUSCAR_PELICULAS_PROYECTADAS_FILTRO_COMPLETO.getQuery());
              querys.add(QueryFiltro.BUSCAR_PELICULAS_PROYECTADAS_FILTRO_SALA.getQuery());
              querys.add(QueryFiltro.BUSCAR_PELICULAS_PROYECTADAS_FILTRO_SALA.getQuery());
              querys.add(QueryFiltro.BUSCAR_PELICULAS_PROYECTADAS_FILTRO_COMPLETO.getQuery());
              filtro.generarQuery(querys);

              //se hace la consulta a la db para obtener cine
              Optional<Cine> cine = cinesDB.obtenerCinePorCodigo(filtro.getCodigoCine());

              reporte.setCine(cine.get());
              List<ProyeccionesSala> salas = new ArrayList<>();
              //si ingresa codigo para una sala, solo se obtinen proyecciones de esa sala
              if (StringUtils.isNotBlank(filtro.getCodigoSala())) {
                     Optional<Sala> sala = salasDB.obtenerSala(filtro.getCodigoSala());
                     salas.add(obtenerInfoSalas(sala.get(), filtro));
                     reporte.setSalas(salas);
                     return reporte;
              } else {
                     List<Sala> salasCine = salasDB.obtenerSalaPorCine(filtro.getCodigoCine());
                     for (int i = 0; i < salasCine.size(); i++) {
                            ProyeccionesSala proyeccionesSala = obtenerInfoSalas(salasCine.get(i), filtro);
                            if (!proyeccionesSala.getProyecciones().isEmpty()) {
                                   salas.add(proyeccionesSala);
                            }

                     }
                     reporte.setSalas(salas);

              }
              return reporte;
       }

       private ProyeccionesSala obtenerInfoSalas(Sala sala, FiltroReportesAdminCine filtro) throws DataBaseException {
              ProyeccionesSala proyeccionesSala = new ProyeccionesSala();
              proyeccionesSala.setSala(sala);
              proyeccionesSala.setProyecciones(obtenerProyeccionesSala(sala.getCodigo(), filtro));
              return proyeccionesSala;
       }

       private List<ProyeccionResponse> obtenerProyeccionesSala(String codigoSala, FiltroReportesAdminCine filtro) throws DataBaseException {
              ReportesAdminCineDB adminCineDB = new ReportesAdminCineDB();
              CreadorProyeccionesResponse creador = new CreadorProyeccionesResponse();
              return creador.crearListaResponse(adminCineDB.obtenerProyeccionesPorSala(codigoSala, filtro));
       }

       /**
        *
        * @param request
        * @return
        * @throws UserDataInvalidException
        * @throws DataBaseException
        * @throws EntityNotFoundException
        */
       public ReporteSalasGustadas obtenerReporteSalasGustadas(FiltroComentariosSalasRequest request) throws UserDataInvalidException,
               DataBaseException, EntityNotFoundException {
              ReportesAdminCineDB reportesAdminCineDB = new ReportesAdminCineDB();
              SalasDB salasDB = new SalasDB();
              CinesDB cinesDB = new CinesDB();
              CalculadorTopSalasGustadas calculador = new CalculadorTopSalasGustadas();
              ReporteSalasGustadas reporte = new ReporteSalasGustadas();

              FiltroReportesAdminCine filtro = extraer(request);

              filtro.existeCineYSala();

              //se cargan las posibles querys para la db y se crea la que indique el usuario
              List<String> querys = new ArrayList<>();
              querys.add(QueryFiltro.BUSCAR_5_SALAS_GUSTADAS_FILTRO_COMPLETO.getQuery());
              querys.add(QueryFiltro.BUSCAR_5_SALAS_GUSTADAS_FILTRO_SALA.getQuery());
              querys.add(QueryFiltro.BUSCAR_5_SALAS_GUSTADAS_FILTRO_SALA.getQuery());
              querys.add(QueryFiltro.BUSCAR_5_SALAS_GUSTADAS_FILTRO_COMPLETO.getQuery());
              filtro.generarQuery(querys);

              //se hace la consulta a la db para obtener cine
              Optional<Cine> cine = cinesDB.obtenerCinePorCodigo(filtro.getCodigoCine());

              reporte.setCine(cine.get());

              List<SalasGustadas> salasGustadas = new ArrayList<>();
              //si ingresa codigo para una sala, solo se obtinen proyecciones de esa sala
              if (StringUtils.isNotBlank(filtro.getCodigoSala())) {
                     Optional<Sala> sala = salasDB.obtenerSala(filtro.getCodigoSala());
                     //se obtienen las calificaciones de la sala en la db
                     List<Calificacion> calificaciones = reportesAdminCineDB.obtenerCalificacionesSala(sala.get().getCodigo(), filtro);
                     SalasGustadas salaGustada = new SalasGustadas();
                     salaGustada.setSala(sala.get());
                     salaGustada.setCalificaciones(calificaciones);
                     salasGustadas.add(salaGustada);
                     reporte.setSalasGustadas(salasGustadas);
              } else {

                     List<Sala> salasCine = salasDB.obtenerSalaPorCine(filtro.getCodigoCine());
                     for (int i = 0; i < salasCine.size(); i++) {
                            SalasGustadas salaGustada = generarSalaGustada(salasCine.get(i), filtro, reportesAdminCineDB);
                            if (!salaGustada.getCalificaciones().isEmpty()) {
                                   salasGustadas.add(salaGustada);
                            }

                     }
                     reporte.setSalasGustadas(salasGustadas);
                     //se obtiene las 5 salas mas gustadas 
                     reporte.setSalasGustadas(calculador.obtenerTopSalasGustadas(reporte.getSalasGustadas()));

              }
              return reporte;
       }

       /**
        *
        * @param sala
        * @param filtro
        * @param reportesAdminCineDB
        * @return
        * @throws DataBaseException
        */
       private SalasGustadas generarSalaGustada(Sala sala, FiltroReportesAdminCine filtro, ReportesAdminCineDB reportesAdminCineDB)
               throws DataBaseException {

              List<Calificacion> calificaciones = reportesAdminCineDB.obtenerCalificacionesSala(sala.getCodigo(), filtro);
              SalasGustadas salasGustadas = new SalasGustadas();
              salasGustadas.setSala(sala);
              salasGustadas.setCalificaciones(calificaciones);
              return salasGustadas;
       }

       /**
        *
        * @param request
        * @return
        * @throws UserDataInvalidException
        * @throws DataBaseException
        * @throws EntityNotFoundException
        */
       public ReporteBoletosVendidos obtenerReporteBoletosVendidos(FiltroComentariosSalasRequest request) throws UserDataInvalidException, DataBaseException, EntityNotFoundException {
              ReportesAdminCineDB reportesAdminCineDB = new ReportesAdminCineDB();
              SalasDB salasDB = new SalasDB();
              CinesDB cinesDB = new CinesDB();
              ReporteBoletosVendidos reporte = new ReporteBoletosVendidos();

              FiltroReportesAdminCine filtro = extraer(request);

              filtro.existeCineYSala();

              //se hace la consulta a la db para obtener cine
              Optional<Cine> cine = cinesDB.obtenerCinePorCodigo(filtro.getCodigoCine());

              reporte.setCine(cine.get());
              //se cargan las posibles querys para la db y se crea la que indique el usuario
              List<String> querys = new ArrayList<>();
              querys.add(QueryFiltro.BUSCAR_COMPRA_BOLETOS_VENDIDOS_FILTRO_COMPLETO.getQuery());
              querys.add(QueryFiltro.BUSCAR_COMPRA_BOLETOS_VENDIDOS_FILTRO_SALA.getQuery());
              querys.add(QueryFiltro.BUSCAR_COMPRA_BOLETOS_VENDIDOS_FILTRO_SALA.getQuery());
              querys.add(QueryFiltro.BUSCAR_COMPRA_BOLETOS_VENDIDOS_FILTRO_COMPLETO.getQuery());
              filtro.generarQuery(querys);

              List<CompraBoletosSala> compraBoletosTodasLasSalas = new ArrayList<>();
              //si ingresa codigo para una sala, solo se obtinen proyecciones de esa sala
              if (StringUtils.isNotBlank(filtro.getCodigoSala())) {
                     Optional<Sala> sala = salasDB.obtenerSala(filtro.getCodigoSala());
                     //se obtienen las calificaciones de la sala en la db
                     List<CompraBoleto> boletosComprados = reportesAdminCineDB.obtenerBoletosComprados(sala.get().getCodigo(), filtro);
                     CompraBoletosSala comprasBoletosSala = new CompraBoletosSala();
                     comprasBoletosSala.setSala(sala.get());
                     comprasBoletosSala.setCompras(boletosComprados);
                     comprasBoletosSala.calcularTotalRecaudado();
                     //se agregan solo las salas que tengan boletos vendidos
                     if (!comprasBoletosSala.getCompras().isEmpty()) {
                            compraBoletosTodasLasSalas.add(comprasBoletosSala);
                     }
                     reporte.setComprasPorSalas(compraBoletosTodasLasSalas);
              } else {

                     List<Sala> salasCine = salasDB.obtenerSalaPorCine(filtro.getCodigoCine());
                     for (int i = 0; i < salasCine.size(); i++) {
                            CompraBoletosSala comprasEnSala = generarCompraBoletosPorSala(salasCine.get(i), filtro, reportesAdminCineDB);
                            if (!comprasEnSala.getCompras().isEmpty()) {
                                   compraBoletosTodasLasSalas.add(comprasEnSala);
                            }
                     }
                     reporte.setComprasPorSalas(compraBoletosTodasLasSalas);

              }
              return reporte;
       }

       /**
        *
        * @param sala
        * @param filtro
        * @param reportesAdminCineDB
        * @return
        * @throws DataBaseException
        */
       private CompraBoletosSala generarCompraBoletosPorSala(Sala sala, FiltroReportesAdminCine filtro, ReportesAdminCineDB reportesAdminCineDB)
               throws DataBaseException {
              CompraBoletosSala compraBoletosSala = new CompraBoletosSala();

              List<CompraBoleto> comprasUsuarios = reportesAdminCineDB.obtenerBoletosComprados(sala.getCodigo(), filtro);

              compraBoletosSala.setSala(sala);
              compraBoletosSala.setCompras(comprasUsuarios);
              compraBoletosSala.calcularTotalRecaudado();
              return compraBoletosSala;
       }
}
