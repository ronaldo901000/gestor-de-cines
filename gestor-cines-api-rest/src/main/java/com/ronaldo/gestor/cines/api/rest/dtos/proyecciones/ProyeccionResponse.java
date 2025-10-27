package com.ronaldo.gestor.cines.api.rest.dtos.proyecciones;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.ronaldo.gestor.cines.api.rest.dtos.peliculas.PeliculaUpdateResponse;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.salas.Sala;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author ronaldo
 */
public class ProyeccionResponse extends EntidadResponse{

       private String codigo;
       private PeliculaUpdateResponse pelicula;
       private Sala sala;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonSerialize(using = LocalDateSerializer.class)
       private LocalDate fecha;
       @JsonFormat(pattern = "HH:mm")
       @JsonSerialize(using = LocalTimeSerializer.class)
       private LocalTime horaInicio;
       @JsonFormat(pattern = "HH:mm")
       @JsonSerialize(using = LocalTimeSerializer.class)
       private LocalTime horaFin;
       private double precio;
       private boolean disponible;

       public String getCodigo() {
              return codigo;
       }

       public void setCodigo(String codigo) {
              this.codigo = codigo;
       }

       public PeliculaUpdateResponse getPelicula() {
              return pelicula;
       }

       public void setPelicula(PeliculaUpdateResponse pelicula) {
              this.pelicula = pelicula;
       }

       public Sala getSala() {
              return sala;
       }

       public void setSala(Sala sala) {
              this.sala = sala;
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

       public boolean isDisponible() {
              return disponible;
       }

       public void setDisponible(boolean disponible) {
              this.disponible = disponible;
       }
       
       

}
