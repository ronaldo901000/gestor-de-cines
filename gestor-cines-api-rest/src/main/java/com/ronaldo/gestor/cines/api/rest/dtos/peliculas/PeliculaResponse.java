package com.ronaldo.gestor.cines.api.rest.dtos.peliculas;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ronaldo.gestor.cines.api.rest.enums.URL.URLAppi;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.peliculas.Pelicula;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class PeliculaResponse extends EntidadResponse {

       private String codigo;
       private String titulo;
       private String sinopsis;
       private int duracion;
       private String director;
       private String cast;
       private String clasificacion;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonSerialize(using = LocalDateSerializer.class)
       private LocalDate fechaEstreno;
       private String idsCategorias;

       public PeliculaResponse(Pelicula pelicula, String categorias) {
              this.codigo = pelicula.getCodigo();
              this.titulo = pelicula.getTitulo();
              this.sinopsis = pelicula.getSinopsis();
              this.duracion = pelicula.getDuracion();
              this.director = pelicula.getDirector();
              this.cast = pelicula.getCast();
              this.clasificacion = pelicula.getClasificacion();
              this.fechaEstreno = pelicula.getFechaEstreno();
              this.idsCategorias = categorias;
       }

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
