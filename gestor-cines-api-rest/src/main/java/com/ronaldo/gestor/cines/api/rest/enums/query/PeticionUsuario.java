package com.ronaldo.gestor.cines.api.rest.enums.query;

/**
 *
 * @author ronaldo
 */
public enum PeticionUsuario {
       ACTUALIZAR_SALDO("update usuario set creditos=? where id=?"),
       OBTENER_USUARIO("select * from usuario where id=?")
       ;
       
       private String peticion;

       private PeticionUsuario(String peticion) {
              this.peticion = peticion;
       }

       public String get() {
              return peticion;
       }
}
