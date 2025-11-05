package com.ronaldo.gestor.cines.api.rest.models.filtrosReportes;

import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class FiltroReporte {

       protected LocalDate fechaInicio;
       protected LocalDate fechaFin;

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
