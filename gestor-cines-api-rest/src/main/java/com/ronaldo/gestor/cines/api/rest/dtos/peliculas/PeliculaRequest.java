package com.ronaldo.gestor.cines.api.rest.dtos.peliculas;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class PeliculaRequest extends EntidadRequest {

       private String codigo;
       private String titulo;
       private String sinopsis;
       private int duracion;
       private String director;
       private String cast;
       private String clasificacion;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonDeserialize(using = LocalDateDeserializer.class)
       private LocalDate fechaEstreno;
       private String idsCategorias;

       public String getCodigo() {
              return codigo;
       }

       public void setCodigo(String codigo) {
              this.codigo = codigo;
       }

       public String getTitulo() {
              return titulo;
       }

       public void setTitulo(String titulo) {
              this.titulo = titulo;
       }

       public String getSinopsis() {
              return sinopsis;
       }

       public void setSinopsis(String sinopsis) {
              this.sinopsis = sinopsis;
       }

       public int getDuracion() {
              return duracion;
       }

       public void setDuracion(int duracion) {
              this.duracion = duracion;
       }

       
       public String getDirector() {
              return director;
       }

       public void setDirector(String director) {
              this.director = director;
       }

       public String getCast() {
              return cast;
       }

       public void setCast(String cast) {
              this.cast = cast;
       }

       public String getClasificacion() {
              return clasificacion;
       }

       public void setClasificacion(String clasificacion) {
              this.clasificacion = clasificacion;
       }

       public LocalDate getFechaEstreno() {
              return fechaEstreno;
       }

       public void setFechaEstreno(LocalDate fechaEstreno) {
              this.fechaEstreno = fechaEstreno;
       }

       public String getIdsCategorias() {
              return idsCategorias;
       }

       public void setIdsCategorias(String idsCategorias) {
              this.idsCategorias = idsCategorias;
       }

       

}
