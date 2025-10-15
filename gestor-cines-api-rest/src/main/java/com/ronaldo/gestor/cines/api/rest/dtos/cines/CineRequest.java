package com.ronaldo.gestor.cines.api.rest.dtos.cines;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class CineRequest extends EntidadRequest{

       private String codigo;
       private String nombre;
       private String ubicacion;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonDeserialize(using = LocalDateDeserializer.class)
       private LocalDate fechaCreacion;

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

       public String getUbicacion() {
              return ubicacion;
       }

       public void setUbicacion(String ubicacion) {
              this.ubicacion = ubicacion;
       }

       public LocalDate getFechaCreacion() {
              return fechaCreacion;
       }


       public void setFechaCreacion(LocalDate fechaCreacion) {
              this.fechaCreacion = fechaCreacion;
       }

}
