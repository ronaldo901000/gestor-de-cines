package com.ronaldo.gestor.cines.api.rest.dtos.salas;

import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;

/**
 *
 * @author ronaldo
 */
public class SalaRequest extends EntidadRequest {

       private String codigo;
       private String codigoCine;
       private String nombre;
       private int filas;
       private int columnas;

       public String getCodigo() {
              return codigo;
       }

       public void setCodigo(String codigo) {
              this.codigo = codigo;
       }

       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigoCine) {
              this.codigoCine = codigoCine;
       }

       public String getNombre() {
              return nombre;
       }

       public void setNombre(String nombre) {
              this.nombre = nombre;
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

}
