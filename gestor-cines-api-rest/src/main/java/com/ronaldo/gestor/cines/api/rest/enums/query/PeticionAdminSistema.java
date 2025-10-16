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
       ELIMINAR_CINE("delete from cine where codigo=?"),
       OBTENER_USUARIO("select * from usuario where id=?"),
       OBTENER_ADMIN_CINE("select * from admin_cine where id_usuario=?"),
       CREAR_ADMIN_CINE("insert into admin_cine (id_usuario, codigo_cine) values(?,?)"),
       OBTENER_ADMIN_SISTEMA("select * from admin_sistema where id_usuario=?"),
       OBTENER_TODOS_ADMINS_DE_CINE("select * from admin_cine join usuario on id_usuario=id where codigo_cine=?"),
       ELIMINAR_ADMIN_CINE("delete from admin_cine where id_usuario=?");
       
       private String peticion;

       private PeticionAdminSistema(String peticion) {
              this.peticion = peticion;
       }

       public String get() {
              return peticion;
       }
       
       
}
