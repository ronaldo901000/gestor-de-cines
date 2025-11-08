package com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema;

import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminCine.FiltroReporteRequest;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class FiltroGanancias extends FiltroReporteRequest {
       public static final int FILTRO_CON_FECHAS=1;
       private String queryAnuncios;
       private String queryPagosBloqueo;
       private int tipoFiltroAnuncios;
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

       public boolean hayFiltro() {
              return fechaInicio != null && fechaFin != null;
       }

       public String getQueryAnuncios() {
              return queryAnuncios;
       }

       public void setQueryAnuncios(String queryAnuncios) {
              this.queryAnuncios = queryAnuncios;
       }

       public int getTipoFiltroAnuncios() {
              return tipoFiltroAnuncios;
       }

       public void setTipoFiltroAnuncios(int tipoFiltroAnuncios) {
              this.tipoFiltroAnuncios = tipoFiltroAnuncios;
       }

       public String getQueryPagosBloqueo() {
              return queryPagosBloqueo;
       }

       public void setQueryPagosBloqueo(String queryPagosBloqueo) {
              this.queryPagosBloqueo = queryPagosBloqueo;
       }

       
       public void generarQueryAnuncios() {
              if (hayFiltro()) {
                     tipoFiltroAnuncios = FILTRO_CON_FECHAS;
                     queryAnuncios = "select * from anuncio where fecha_registro>=? and fecha_registro<=?";
              } else {
                     queryAnuncios = "select * from anuncio";
              }

       }

       public void generarQueryPagosBloqueo() {
              if (hayFiltro()) {
                     tipoFiltroAnuncios = FILTRO_CON_FECHAS;
                     queryPagosBloqueo = "select * from pago_bloqueo_anuncio where fecha_pago>=? and fecha_pago<=?";
              } else {
                     queryPagosBloqueo = "select * from pago_bloqueo_anuncio";
              }
       }
}
