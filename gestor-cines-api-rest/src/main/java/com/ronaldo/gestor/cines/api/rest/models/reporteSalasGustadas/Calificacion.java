package com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas;

import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class Calificacion {

       private String idUsuario;
       private int calificacion;
       private LocalDate fecha;

       public String getIdUsuario() {
              return idUsuario;
       }

       public void setIdUsuario(String idUsuario) {
              this.idUsuario = idUsuario;
       }

       public int getCalificacion() {
              return calificacion;
       }

       public void setCalificacion(int calificacion) {
              this.calificacion = calificacion;
       }

       public LocalDate getFecha() {
              return fecha;
       }

       public void setFecha(LocalDate fecha) {
              this.fecha = fecha;
       }

}
