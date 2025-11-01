package com.ronaldo.gestor.cines.api.rest.enums.query;

/**
 *
 * @author ronaldo
 */
public enum RangoBusquedaElemento {
       CINES(5),
       PELICULAS(6),
       SALAS(5),
       PROYECCIONES(5),
       ANUNCIOS(2),
       MIS_COMPRAS(5),
       ;
       private int rango;

       private RangoBusquedaElemento(int rango) {
              this.rango = rango;
       }

       public int getRango() {
              return rango;
       }
       
       

}
