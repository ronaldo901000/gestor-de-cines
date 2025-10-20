package com.ronaldo.gestor.cines.api.rest.dtos.costosFuncionamiento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ronaldo.gestor.cines.api.rest.models.costosFuncionamiento.CostoFuncionamiento;
import java.time.LocalDate;

/**
 *
 * @author ronaldo
 */
public class CostoFuncionamientoCineResponse {
       private int id;
       private String codigoCine;
       private String nombreCine;
       @JsonFormat(pattern = "yyyy-MM-dd")
       @JsonSerialize(using = LocalDateSerializer.class)
       private LocalDate fechaRegistro;
       private double costo;

       public CostoFuncionamientoCineResponse(CostoFuncionamiento costoFuncionamiento) {
              this.id=costoFuncionamiento.getId();
              this.codigoCine = costoFuncionamiento.getCodigoCine();
              this.fechaRegistro = costoFuncionamiento.getFechaRegistro();
              this.costo = costoFuncionamiento.getCosto();
       }

       public int getId() {
              return id;
       }

       public void setId(int id) {
              this.id = id;
       }

       
       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigoCine) {
              this.codigoCine = codigoCine;
       }

       public String getNombreCine() {
              return nombreCine;
       }

       public void setNombreCine(String nombreCine) {
              this.nombreCine = nombreCine;
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
