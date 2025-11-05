package com.ronaldo.gestor.cines.api.rest.models.reporteComentarios;

import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class Comentario {

       private String nombreSala;
       private String nombreUsuario;
       private LocalDate fecha;
       private String comentario;
       private int calificacion;

       public String getNombreSala() {
              return nombreSala;
       }

       public void setNombreSala(String nombreSala) {
              this.nombreSala = nombreSala;
       }

       public String getNombreUsuario() {
              return nombreUsuario;
       }

       public void setNombreUsuario(String nombreUsuario) {
              this.nombreUsuario = nombreUsuario;
       }

       public LocalDate getFecha() {
              return fecha;
       }

       public void setFecha(LocalDate fecha) {
              this.fecha = fecha;
       }

       public String getComentario() {
              return comentario;
       }

       public void setComentario(String comentario) {
              this.comentario = comentario;
       }

       public int getCalificacion() {
              return calificacion;
       }

       public void setCalificacion(int calificacion) {
              this.calificacion = calificacion;
       }

}
