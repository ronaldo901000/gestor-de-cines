package com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminCine;

import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class FiltroComentariosSalasRequest extends FiltroReporteRequest {

       private String codigoCine;
       private String codigoSala;

       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigoCine) {
              this.codigoCine = codigoCine;
       }

       public String getCodigoSala() {
              return codigoSala;
       }

       public void setCodigoSala(String codigoSala) {
              this.codigoSala = codigoSala;
       }

       public LocalDate getFechaInicio() {
              return fechaInicio;
       }

       public void setFechaInicio(LocalDate fechaInicio) {
              this.fechaInicio = fechaInicio;
       }

       public LocalDate getFechaFin() {
              return fechaFin;
       }

       public void setFechaFin(LocalDate fechaFin) {
              this.fechaFin = fechaFin;
       }

}
