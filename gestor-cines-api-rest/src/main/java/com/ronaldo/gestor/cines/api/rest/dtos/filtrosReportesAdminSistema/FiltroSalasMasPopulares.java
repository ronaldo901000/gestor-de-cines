package com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema;

import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminCine.FiltroReporteRequest;
import com.ronaldo.gestor.cines.api.rest.models.filtrosReportes.QueryFiltro;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class FiltroSalasMasPopulares extends FiltroReporteRequest {

       public static final int SIN_FILTRO = 0;
       public static final int CON_FILTRO = 1;

       private String query;
       private int tipo;

       public String getQuery() {
              return query;
       }

       public void setQuery(String query) {
              this.query = query;
       }

       public int getTipo() {
              return tipo;
       }

       public void setTipo(int tipo) {
              this.tipo = tipo;
       }

       @Override
       public LocalDate getFechaInicio() {
              return fechaInicio;
       }

       public void setFechaInicio(LocalDate fechaInicio) {
              this.fechaInicio = fechaInicio;
       }

       @Override
       public LocalDate getFechaFin() {
              return fechaFin;
       }

       public void setFechaFin(LocalDate fechaFin) {
              this.fechaFin = fechaFin;
       }

       public void generarQuery() {
              if (fechaInicio != null && fechaFin != null) {
                     tipo = CON_FILTRO;
                     query = QueryFiltro.BUSCAR_5_SALAS_GUSTADAS_FILTRO_COMPLETO.getQuery();
              } else {
                     query = QueryFiltro.BUSCAR_5_SALAS_GUSTADAS_FILTRO_SALA.getQuery();
                     tipo = SIN_FILTRO;
              }
       }

}
