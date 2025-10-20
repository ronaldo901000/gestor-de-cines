package com.ronaldo.gestor.cines.api.rest.models.costoGlobal;

/**
 *
 * @author ronaldo
 */
public class CostoGlobal {

       private double costo;

       public CostoGlobal() {
       }
       
       public CostoGlobal(double costo) {
              this.costo = costo;
       }

       public double getCosto() {
              return costo;
       }

       public void setCosto(double costo) {
              this.costo = costo;
       }

}
