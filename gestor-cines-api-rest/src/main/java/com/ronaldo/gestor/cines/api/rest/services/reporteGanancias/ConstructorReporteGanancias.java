package com.ronaldo.gestor.cines.api.rest.services.reporteGanancias;

import com.ronaldo.gestor.cines.api.rest.db.cines.CinesDB;
import com.ronaldo.gestor.cines.api.rest.db.costosFuncionamiento.CostosFuncionamientoDB;
import com.ronaldo.gestor.cines.api.rest.db.reportes.ReportesAdminSistemaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioResponse;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroGanancias;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import com.ronaldo.gestor.cines.api.rest.models.costosFuncionamiento.CostoFuncionamiento;
import com.ronaldo.gestor.cines.api.rest.models.pagosBloqueo.PagoBloqueo;
import com.ronaldo.gestor.cines.api.rest.models.reporteGanancias.BalanceFinanciero;
import com.ronaldo.gestor.cines.api.rest.models.reporteGanancias.CostoCine;
import com.ronaldo.gestor.cines.api.rest.models.reporteGanancias.ReporteGanancias;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ConstructorReporteGanancias {

       /**
        * 
        * @param filtro
        * @return
        * @throws DataBaseException 
        */
       public ReporteGanancias construirReporte(FiltroGanancias filtro) throws DataBaseException {
              ReportesAdminSistemaDB adminSistemaDB = new ReportesAdminSistemaDB();

              ReporteGanancias reporte = new ReporteGanancias();
              
              List<CostoCine> costosCines = obtenerCostos(filtro);
              List<AnuncioResponse> anuncioResponses = adminSistemaDB.obtenerAnuncios(filtro);
              List<PagoBloqueo> pagosBloqueo = adminSistemaDB.obtenerPagosPorBloqueo(filtro);
              BalanceFinanciero balance = new BalanceFinanciero();
              reporte.setCostosCines(costosCines);
              reporte.setAnuncios(anuncioResponses);
              reporte.setPagosBloqueo(pagosBloqueo);

              // se crea  el balance
              balance.setCostos(reporte.calcularCostosTotal());
              balance.setIngresos(reporte.calcularIngresosTotales());
              balance.calcularGanancia();
              reporte.setBalanceFinanciero(balance);

              return reporte;
       }

       /**
        * 
        * @param filtro
        * @return
        * @throws DataBaseException 
        */
       private List<CostoCine> obtenerCostos(FiltroGanancias filtro) throws DataBaseException {
              CinesDB cinesDB = new CinesDB();

              CostosFuncionamientoDB costosFuncionamientoDB = new CostosFuncionamientoDB();
              List<CostoCine> costos = new ArrayList<>();

              int totalCines = cinesDB.contarTotalCines();
              List<Cine> cines = cinesDB.obtenerCinesPaginacion(0, totalCines);
              for (int i = 0; i < cines.size(); i++) {
                     costos.add(obtenerUnCosto(cines.get(i), filtro, costosFuncionamientoDB));
              }
              return costos;
       }

       /**
        * 
        * @param cine
        * @param filtro
        * @param costosFuncionamientoDB
        * @return
        * @throws DataBaseException 
        */
       private CostoCine obtenerUnCosto(
               Cine cine,
               FiltroGanancias filtro,
               CostosFuncionamientoDB costosFuncionamientoDB) throws DataBaseException {

              CostoCine costoCine = new CostoCine();

              costoCine.setCine(cine);
              List<CostoFuncionamiento> costos = costosFuncionamientoDB.obtenerCostosPorNombreOCodigo(cine.getCodigo());
              costos = ordenarCostoFuncionamiento(costos);
              if (filtro.hayFiltro()) {
                     costoCine.setCosto(calcularCostoTotalConFiltro(costos, filtro));
              } else {
                     //si no hay fitro de fechas y solo hay un registro de costo se asigna 0
                     if (costos.size() <= 1) {
                            costoCine.setCosto(0);
                     } else {
                            costoCine.setCosto(calcularCostoTotalSinFiltro(costos));
                     }

              }
              return costoCine;

       }

       /**
        *
        * @param costos
        * @return
        */
       private List<CostoFuncionamiento> ordenarCostoFuncionamiento(List<CostoFuncionamiento> costos) {
              int totalCostos = costos.size();
              for (int i = 0; i < totalCostos - 1; i++) {
                     for (int j = 0; j < totalCostos - 1 - i; j++) {
                            CostoFuncionamiento costo1 = costos.get(j);
                            CostoFuncionamiento costo2 = costos.get(j + 1);
                            if (costo2.getFechaRegistro().isBefore(costo1.getFechaRegistro())) {
                                   costos.set(j, costo2);
                                   costos.set(j + 1, costo1);
                            }
                     }
              }
              return costos;
       }

       /**
        * calculo de costo para cuando no hay rango de fechas
        *
        * @param costos
        * @return
        */
       private double calcularCostoTotalSinFiltro(List<CostoFuncionamiento> costos) {
              double costoTotal = 0;

              for (int i = 0; i < costos.size() - 1; i++) {
                     LocalDate fechaInicio = costos.get(i).getFechaRegistro();
                     LocalDate fechaFin = costos.get(i + 1).getFechaRegistro();

                     long diasVigencia = fechaFin.toEpochDay() - fechaInicio.toEpochDay();
                     costoTotal += costos.get(i).getCosto() * diasVigencia;
              }
              return costoTotal;
       }

       /**
        *
        * @param costos
        * @param filtro
        * @return
        */
       private double calcularCostoTotalConFiltro(List<CostoFuncionamiento> costos, FiltroGanancias filtro) {
              double costoTotal = 0;
              if (costos == null || costos.isEmpty()) {
                     return 0;
              }
              LocalDate fechaFiltroInicio = filtro.getFechaInicio();
              LocalDate fechaFiltroFin = filtro.getFechaFin();
              LocalDate primerCosto = costos.get(0).getFechaRegistro();

              //filtro con fechas fuera del rango
              if (fechaFiltroFin.isBefore(primerCosto)) {
                     return 0;
              }
              int numeroCostos = costos.size();
              for (int i = 0; i < numeroCostos; i++) {
                     LocalDate inicioCosto = costos.get(i).getFechaRegistro();
                     LocalDate siguienteCosto;
                     if (i < numeroCostos - 1) {
                            siguienteCosto = costos.get(i + 1).getFechaRegistro();
                     } else {
                            siguienteCosto = fechaFiltroFin.plusDays(1);
                     }
                     LocalDate inicioVigencia;
                     if (inicioCosto.isAfter(fechaFiltroInicio)) {
                            inicioVigencia = inicioCosto;
                     } else {
                            inicioVigencia = fechaFiltroInicio;
                     }
                     LocalDate finVigencia;
                     if (siguienteCosto.isBefore(fechaFiltroFin.plusDays(1))) {
                            finVigencia = siguienteCosto;
                     } else {
                            finVigencia = fechaFiltroFin.plusDays(1);
                     }
                     long diasVigencia = finVigencia.toEpochDay() - inicioVigencia.toEpochDay();
                     if (diasVigencia > 0) {
                            costoTotal += costos.get(i).getCosto() * diasVigencia;
                     }
              }
              return costoTotal;
       }

}
