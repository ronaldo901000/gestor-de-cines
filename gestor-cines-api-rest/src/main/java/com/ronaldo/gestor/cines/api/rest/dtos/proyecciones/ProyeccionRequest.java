package com.ronaldo.gestor.cines.api.rest.dtos.proyecciones;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author ronaldo
 */
public class ProyeccionRequest extends EntidadRequest {

       private String codigo;
       private String codigoPelicula;
       private String codigoSala;

       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonDeserialize(using = LocalDateDeserializer.class)
       private LocalDate fecha;

       @JsonFormat(pattern = "HH:mm")
       @JsonDeserialize(using = LocalTimeDeserializer.class)
       private LocalTime horaInicio;

       @JsonFormat(pattern = "HH:mm")
       @JsonDeserialize(using = LocalTimeDeserializer.class)
       private LocalTime horaFin;

       private double precio;

       public String getCodigo() {
              return codigo;
       }

       public void setCodigo(String codigo) {
              this.codigo = codigo;
       }

       public String getCodigoPelicula() {
              return codigoPelicula;
       }

       public void setCodigoPelicula(String codigoPelicula) {
              this.codigoPelicula = codigoPelicula;
       }

       public String getCodigoSala() {
              return codigoSala;
       }

       public void setCodigoSala(String codigoSalon) {
              this.codigoSala = codigoSalon;
       }

       public LocalDate getFecha() {
              return fecha;
       }

       public void setFecha(LocalDate fecha) {
              this.fecha = fecha;
       }

       public LocalTime getHoraInicio() {
              return horaInicio;
       }

       public void setHoraInicio(LocalTime horaInicio) {
              this.horaInicio = horaInicio;
       }

       public LocalTime getHoraFin() {
              return horaFin;
       }

       public void setHoraFin(LocalTime horaFin) {
              this.horaFin = horaFin;
       }

       public double getPrecio() {
              return precio;
       }

       public void setPrecio(double precio) {
              this.precio = precio;
       }

}
