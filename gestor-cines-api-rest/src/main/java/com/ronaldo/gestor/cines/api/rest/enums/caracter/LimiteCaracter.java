package com.ronaldo.gestor.cines.api.rest.enums.caracter;

/**
 *
 * @author ronaldo
 */
public enum LimiteCaracter {
       CODIGO_CINE(25),
       NOMBRE_CINE(100),
       UBICACION_CINE(150);
       
       
       private int limite;

       private LimiteCaracter(int limite) {
              this.limite = limite;
       }

       public int getLimite() {
              return limite;
       }
       
       
       
}
