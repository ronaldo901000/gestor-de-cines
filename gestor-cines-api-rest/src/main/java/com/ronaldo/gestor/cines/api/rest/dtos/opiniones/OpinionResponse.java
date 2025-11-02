package com.ronaldo.gestor.cines.api.rest.dtos.opiniones;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ronaldo.gestor.cines.api.rest.dtos.usuarios.UsuarioResponse;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class OpinionResponse {

       private String codigoEntidad;
       private UsuarioResponse usuario;
       private String comentario;
       private int calificacion;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonSerialize(using = LocalDateSerializer.class)
       private LocalDate fecha;

       public String getCodigoEntidad() {
              return codigoEntidad;
       }

       public void setCodigoEntidad(String codigoEntidad) {
              this.codigoEntidad = codigoEntidad;
       }

       public UsuarioResponse getUsuario() {
              return usuario;
       }

       public void setUsuario(UsuarioResponse usuario) {
              this.usuario = usuario;
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

}
