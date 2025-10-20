package com.ronaldo.gestor.cines.api.rest.models.costosFuncionamiento;

import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ronaldo
 */
public class CostoFuncionamiento implements Editable {
       private int id;
       private static final double COSTO_MAXIMO = 100000000;
       private String codigoCine;
       private LocalDate fechaRegistro;
       private double costo;

       public CostoFuncionamiento(int id,String codigoCine, LocalDate fechaRegistro, double costo) {
              this.id=id;
              this.codigoCine = codigoCine;
              this.fechaRegistro = fechaRegistro;
              this.costo = costo;
       }

       public CostoFuncionamiento() {
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

       public boolean datosValidos() {
              return StringUtils.isNotBlank(codigoCine)
                      && fechaRegistro != null
                      && costo >= 0 && costo <= COSTO_MAXIMO;
       }

}
