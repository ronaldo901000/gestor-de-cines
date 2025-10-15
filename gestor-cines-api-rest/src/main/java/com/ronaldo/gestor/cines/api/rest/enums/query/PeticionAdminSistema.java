/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ronaldo.gestor.cines.api.rest.enums.query;

/**
 *
 * @author ronaldo
 */
public enum PeticionAdminSistema {
       CREAR_CINE("insert into cine(codigo, nombre, ubicacion, fecha_creacion) values (?, ?, ?, ?)"),
       BUSCAR_CINE("select * from cine where codigo=?  and activo=1"),
       OBTENER_CINES("select * from cine where activo=1"),
       OBTENER_CINES_POR_CODIGO_NOMBRE("select * from cine where codigo=? or nombre=? and activo=1"),
       OBTENER_CINE_POR_CODIGO("select * from cine where codigo=?"),
       ACTUALIZAR_CINE("update cine set nombre=?, ubicacion=?, fecha_creacion=? where codigo=?"),
       ELIMINAR_CINE("delete from cine where codigo=?");
       private String peticion;

       private PeticionAdminSistema(String peticion) {
              this.peticion = peticion;
       }

       public String getPeticion() {
              return peticion;
       }
       
       
}
