package com.ronaldo.gestor.cines.api.rest.dtos.anuncios;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class AnunciosRequest extends EntidadRequest {

       private String codigo;
       private String idAnunciante;
       private String titulo;
       private String tipo;
       private String descripcion;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonDeserialize(using = LocalDateDeserializer.class)
       private LocalDate fechaRegistro;
       private double precio;
       private int duracion;

       public String getCodigo() {
              return codigo;
       }

       public void setCodigo(String codigo) {
              this.codigo = codigo;
       }

       public String getIdAnunciante() {
              return idAnunciante;
       }

       public void setIdAnunciante(String idAnunciante) {
              this.idAnunciante = idAnunciante;
       }

       public String getTitulo() {
              return titulo;
       }

       public void setTitulo(String titulo) {
              this.titulo = titulo;
       }

       public String getTipo() {
              return tipo;
       }

       public void setTipo(String tipo) {
              this.tipo = tipo;
       }

       public String getDescripcion() {
              return descripcion;
       }

       public void setDescripcion(String descripcion) {
              this.descripcion = descripcion;
       }

       public LocalDate getFechaRegistro() {
              return fechaRegistro;
       }

       public void setFechaRegistro(LocalDate fechaRegistro) {
              this.fechaRegistro = fechaRegistro;
       }

       public double getPrecio() {
              return precio;
       }

       public void setPrecio(double precio) {
              this.precio = precio;
       }

       public int getDuracion() {
              return duracion;
       }

       public void setDuracion(int duracion) {
              this.duracion = duracion;
       }

}
