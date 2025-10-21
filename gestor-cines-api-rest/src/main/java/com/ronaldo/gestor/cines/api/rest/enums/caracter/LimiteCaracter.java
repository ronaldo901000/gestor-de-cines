package com.ronaldo.gestor.cines.api.rest.enums.caracter;

/**
 *
 * @author ronaldo
 */
public enum LimiteCaracter {
       CODIGO(25),
       NOMBRE(100),
       UBICACION_CINE(150),
       TITULO_PELICULA(100),
       SINOPSIS(300),
       DIRECTOR(100),
       CAST(200),
       ;
       
       
       private int limite;

       private LimiteCaracter(int limite) {
              this.limite = limite;
       }

       public int get() {
              return limite;
       }
       
       
       
}
