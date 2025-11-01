package com.ronaldo.gestor.cines.api.rest.dtos.compraBoletos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ronaldo.gestor.cines.api.rest.dtos.proyecciones.ProyeccionResponse;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class CompraBoletosResponse extends EntidadResponse {

       private String idUsuario;
       private ProyeccionResponse proyeccion;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonSerialize(using = LocalDateSerializer.class)
       private LocalDate fechaCompra;
       private int cantidad;
       private double costoTotal;

       public String getIdUsuario() {
              return idUsuario;
       }

       public void setIdUsuario(String idUsuario) {
              this.idUsuario = idUsuario;
       }

       public ProyeccionResponse getProyeccion() {
              return proyeccion;
       }

       public void setProyeccion(ProyeccionResponse proyeccion) {
              this.proyeccion = proyeccion;
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

       public double getCostoTotal() {
              return costoTotal;
       }

       public void setCostoTotal(double costoTotal) {
              this.costoTotal = costoTotal;
       }

}
