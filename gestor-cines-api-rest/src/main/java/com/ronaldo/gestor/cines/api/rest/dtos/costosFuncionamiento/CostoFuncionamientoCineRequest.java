package com.ronaldo.gestor.cines.api.rest.dtos.costosFuncionamiento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class CostoFuncionamientoCineRequest extends EntidadRequest {

       private String codigoCine;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonDeserialize(using = LocalDateDeserializer.class)
       private LocalDate fechaRegistro;
       private double costo;

       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigoCine) {
              this.codigoCine = codigoCine;
       }

       public LocalDate getFechaRegistro() {
              return fechaRegistro;
       }

       public void setFechaRegistro(LocalDate fechaRegistro) {
              this.fechaRegistro = fechaRegistro;
       }

       public double getCosto() {
              return costo;
       }

       public void setCosto(double costo) {
              this.costo = costo;
       }

}
