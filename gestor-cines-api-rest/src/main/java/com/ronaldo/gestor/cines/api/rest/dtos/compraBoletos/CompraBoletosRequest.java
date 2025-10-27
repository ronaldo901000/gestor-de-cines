package com.ronaldo.gestor.cines.api.rest.dtos.compraBoletos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class CompraBoletosRequest extends EntidadRequest {

       private String idUsuario;
       private String codigoProyeccion;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonDeserialize(using = LocalDateDeserializer.class)
       private LocalDate fechaCompra;
       private int cantidad;

       public String getIdUsuario() {
              return idUsuario;
       }

       public void setIdUsuario(String idUsuario) {
              this.idUsuario = idUsuario;
       }

       public String getCodigoProyeccion() {
              return codigoProyeccion;
       }

       public void setCodigoProyeccion(String codigoProyeccion) {
              this.codigoProyeccion = codigoProyeccion;
       }

       public LocalDate getFechaCompra() {
              return fechaCompra;
       }

       public void setFechaCompra(LocalDate fechaCompra) {
              this.fechaCompra = fechaCompra;
       }

       public int getCantidad() {
              return cantidad;
       }

       public void setCantidad(int cantidad) {
              this.cantidad = cantidad;
       }


}
