package com.ronaldo.gestor.cines.api.rest.dtos.salas;

import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.salas.Sala;

/**
 *
 * @author ronaldo
 */
public class SalaResponse extends EntidadResponse {

       private String codigo;
       private String nombre;
       private String codigoCine;
       private String nombreCine;
       private int filas;
       private int columnas;
       private String comentariosYCalificacionesHabilitados;
       private String activo;
       private int capacidad;

       public SalaResponse(Sala sala) {
              this.codigo = sala.getCodigo();
              this.nombre = sala.getNombre();
              this.codigoCine = sala.getCodigoCine();
              this.nombreCine = sala.getNombreCine();
              this.filas = sala.getFilas();
              this.columnas = sala.getColumnas();
              calcularCapacidad();
              this.comentariosYCalificacionesHabilitados = definirbooleans(sala.isComentariosYCalificacionesHabilitados());
              this.activo = definirbooleans(sala.isActivo());
       }

       public String definirbooleans(boolean bool) {
              if (bool) {
                     return "SÍ";

              }
              return "NO";
       }

       public SalaResponse() {
       }

       public String getCodigo() {
              return codigo;
       }

       public void setCodigo(String codigo) {
              this.codigo = codigo;
       }

       public String getNombre() {
              return nombre;
       }

       public void setNombre(String nombre) {
              this.nombre = nombre;
       }

       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigoCine) {
              this.codigoCine = codigoCine;
       }

       public String getNombreCine() {
              return nombreCine;
       }

       public void setNombreCine(String nombreCine) {
              this.nombreCine = nombreCine;
       }

       public int getFilas() {
              return filas;
       }

       public void setFilas(int filas) {
              this.filas = filas;
       }

       public int getColumnas() {
              return columnas;
       }

       public void setColumnas(int columnas) {
              this.columnas = columnas;
       }

       public String getComentariosYCalificacionesHabilitados() {
              return comentariosYCalificacionesHabilitados;
       }

       public void setComentariosYCalificacionesHabilitados(String comentariosYCalificacionesHabilitados) {
              this.comentariosYCalificacionesHabilitados = comentariosYCalificacionesHabilitados;
       }

       public String getActivo() {
              return activo;
       }

       public void setActivo(String activo) {
              this.activo = activo;
       }

       public int getCapacidad() {
              return capacidad;
       }

       public void setCapacidad(int capacidad) {
              this.capacidad = capacidad;
       }

       public void calcularCapacidad() {
              this.capacidad = filas * columnas;
       }
}
