package com.ronaldo.gestor.cines.api.rest.enums.query;

/**
 *
 * @author ronaldo
 */
public enum PeticionesAdminCine {

       BUSCAR_SALA("select * from sala where codigo=?"),
       CREAR_SALA("insert into sala (codigo, codigo_cine, nombre, filas, columnas)"
               + "values(?, ?, ?, ?,? )"),
       OBTENER_SALAS("select * from sala s join cine c on s.codigo_cine = c.codigo where c.codigo=?"),
       OBTENER_SALA("select * from sala s join cine c on s.codigo_cine = c.codigo where s.codigo=?"),
       VERIFICAR_SALA_OCUPADA("select * from proyeccion where codigo_sala=?"),
       ACTUALIZAR_SALA("update sala set nombre = ?, filas = ?, columnas = ? where codigo = ?"),
       
       ;
       private String peticion;

       private PeticionesAdminCine(String peticion) {
              this.peticion = peticion;
       }

       public String get() {
              return peticion;
       }

}
