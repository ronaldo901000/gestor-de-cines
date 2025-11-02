package com.ronaldo.gestor.cines.api.rest.dtos.opiniones;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class OpinionRequest extends EntidadRequest {

       private String codigoEntidad;
       private String idUsuario;
       private String comentario;
       private int calificacion;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonDeserialize(using = LocalDateDeserializer.class)
       private LocalDate fecha;
       private String tipo;

       public String getCodigoEntidad() {
              return codigoEntidad;
       }

       public void setCodigoEntidad(String codigoEntidad) {
              this.codigoEntidad = codigoEntidad;
       }

       public String getIdUsuario() {
              return idUsuario;
       }

       public void setIdUsuario(String idUsuario) {
              this.idUsuario = idUsuario;
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

       public LocalDate getFecha() {
              return fecha;
       }

       public void setFecha(LocalDate fecha) {
              this.fecha = fecha;
       }

       public String getTipo() {
              return tipo;
       }

       public void setTipo(String tipo) {
              this.tipo = tipo;
       }

}
