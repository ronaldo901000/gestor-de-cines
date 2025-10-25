package com.ronaldo.gestor.cines.api.rest.dtos.recargas;

/**
 *
 * @author ronaldo
 */
public class RecargaCineRequest {

       private String codigoCine;
       private double monto;

       public String getCodigoCine() {
              return codigoCine;
       }

       public void setCodigoCine(String codigoCine) {
              this.codigoCine = codigoCine;
       }

       public double getMonto() {
              return monto;
       }

       public void setMonto(double monto) {
              this.monto = monto;
       }

}
