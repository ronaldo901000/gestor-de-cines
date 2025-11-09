package com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema;

import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminCine.FiltroReporteRequest;
import com.ronaldo.gestor.cines.api.rest.models.filtrosReportes.QueryFiltro;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class FiltroAnunciosComprados extends FiltroReporteRequest {

       public static final int FILTRO_COMPLETO = 0;
       public static final int FILTRO_FECHA = 1;
       public static final int FILTRO_FECHA_Y_TIPO_ANUNCIO = 2;
       public static final int FILTRO_FECHA_Y_PERIODO_TIEMPO = 3;
       public static final int FILTRO_TIPO_ANUNCIO = 4;
       public static final int FILTRO_TIPO_ANUNCIO_Y_PERIODO_TIEMPO = 5;
       public static final int FILTRO_PERIODO_TIEMPO = 6;
       public static final int FILTRO_NULO = 7;

       private String tipoAnuncio;
       private int periodoTiempo;
       private String query;
       private int tipo;

       public String getTipoAnuncio() {
              return tipoAnuncio;
       }

       public void setTipoAnuncio(String tipoAnuncio) {
              this.tipoAnuncio = tipoAnuncio;
       }

       public int getPeriodoTiempo() {
              return periodoTiempo;
       }

       public void setPeriodoTiempo(int periodoTiempo) {
              this.periodoTiempo = periodoTiempo;
       }

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
              if (hayFechas() && StringUtils.isNotBlank(tipoAnuncio) && periodoTiempo > 0) {
                     query = QueryFiltro.BUSCAR_ANUNCIOS_FILTRO_COMPLETO.getQuery();
                     tipo = FILTRO_COMPLETO;

              } else if (hayFechas() && StringUtils.isNotBlank(tipoAnuncio)) {
                     query = QueryFiltro.BUSCAR_ANUNCIOS_FILTRO_FECHAS_Y_TIPO_ANUNCIO.getQuery();
                     tipo = FILTRO_FECHA_Y_TIPO_ANUNCIO;

              } else if (hayFechas() && periodoTiempo > 0) {
                     query = QueryFiltro.BUSCAR_ANUNCIOS_FILTRO_FECHAS_Y_PERIODO_TIEMPO.getQuery();
                     tipo = FILTRO_FECHA_Y_PERIODO_TIEMPO;

              } else if (StringUtils.isNotBlank(tipoAnuncio) && periodoTiempo > 0) {
                     query = QueryFiltro.BUSCAR_ANUNCIOS_FILTRO_TIPO_ANUNCIO_Y_PERIODO_TIEMPO.getQuery();
                     tipo = FILTRO_TIPO_ANUNCIO_Y_PERIODO_TIEMPO;

              } else if (hayFechas()) {
                     query = QueryFiltro.BUSCAR_ANUNCIOS_FILTRO_FECHA.getQuery();
                     tipo = FILTRO_FECHA;

              } else if (StringUtils.isNotBlank(tipoAnuncio)) {
                     query = QueryFiltro.BUSCAR_ANUNCIOS_FILTRO_TIPO_ANUNCIO.getQuery();
                     tipo = FILTRO_TIPO_ANUNCIO;

              } else if (periodoTiempo > 0) {
                     query = QueryFiltro.BUSCAR_ANUNCIOS_FILTRO_PERIODO_TIEMPO.getQuery();
                     tipo = FILTRO_PERIODO_TIEMPO;

              } else {
                     query = QueryFiltro.BUSCAR_ANUNCIOS_FILTRO_NULO.getQuery();
                     tipo = FILTRO_NULO;
              }
       }

       public boolean hayFechas() {
              return fechaInicio != null && fechaFin != null;
       }
}
