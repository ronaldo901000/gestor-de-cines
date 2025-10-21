package com.ronaldo.gestor.cines.api.rest.enums.query;

/**
 *
 * @author ronaldo
 */
public enum PeticionesAdminCine {

       BUSCAR_SALA("select * from sala where codigo=?"),
       CREAR_SALA("insert into sala (codigo, codigo_cine, nombre, filas, columnas)"
               + "values(?, ?, ?, ?,? )"),
       OBTENER_SALAS("select * from sala s join cine c on s.codigo_cine = c.codigo where c.codigo=?");
       private String peticion;

       private PeticionesAdminCine(String peticion) {
              this.peticion = peticion;
       }

       public String get() {
              return peticion;
       }

}
