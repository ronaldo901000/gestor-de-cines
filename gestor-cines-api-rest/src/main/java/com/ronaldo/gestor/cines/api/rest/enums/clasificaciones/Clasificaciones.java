package com.ronaldo.gestor.cines.api.rest.enums.clasificaciones;

/**
 *
 * @author ronaldo
 */
public enum Clasificaciones {
       A("A"),
       B("B"),
       B_12("B-12"),
       B_15("B-15"),
       C("C");

       private String txt;

       private Clasificaciones(String txt) {
              this.txt = txt;
       }

       public String get() {
              return txt;
       }

}
