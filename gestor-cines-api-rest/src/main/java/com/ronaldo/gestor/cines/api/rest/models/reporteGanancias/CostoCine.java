package com.ronaldo.gestor.cines.api.rest.models.reporteGanancias;

import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;

/**
 *
 * @author ronaldo
 */
public class CostoCine {

       private Cine cine;
       private double costo;

       public String getNombre() {
              return cine.getNombre();
       }

       public String getCodigo() {
              return cine.getCodigo();
       }

       public Cine getCine() {
              return cine;
       }

       public void setCine(Cine cine) {
              this.cine = cine;
       }

       public double getCosto() {
              return costo;
       }

       public void setCosto(double costo) {
              this.costo = costo;
       }

}
