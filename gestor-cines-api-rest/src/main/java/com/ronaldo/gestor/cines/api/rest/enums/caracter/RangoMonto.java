package com.ronaldo.gestor.cines.api.rest.enums.caracter;

/**
 *
 * @author ronaldo
 */
public enum RangoMonto {

       RECARGA(1, 50000);
       private int minimo;
       private int maximo;

       private RangoMonto(int minimo, int maximo) {
              this.minimo = minimo;
              this.maximo = maximo;
       }

       public int getMinimo() {
              return minimo;
       }

       public int getMaximo() {
              return maximo;
       }
       
       

}
