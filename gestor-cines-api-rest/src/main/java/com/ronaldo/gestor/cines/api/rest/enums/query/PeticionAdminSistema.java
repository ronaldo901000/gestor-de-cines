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
       ELIMINAR_ADMIN_CINE("delete from admin_cine where id_usuario=?"),
       BUSCAR_ANUNCIANTE("select * from anunciante where id_usuario=?"),
       CREAR_ANUNCIANTE("insert into anunciante (id_usuario) values (?)"),
       OBTENER_ANUNCIANTES("select * from anunciante join usuario on id_usuario=id where anunciante.activo=1"),
       ELIMINAR_ANUNCIANTE("delete from anunciante where id_usuario=?"),
       OBTENER_PRECIOS_ANUNCIOS("select * from precio_anuncio"),
       OBTENER_PRECIO_ANUNCIO("select * from precio_anuncio where id=?"),
       ACTUALIZAR_PRECIOS_ANUNCIO("update precio_anuncio set precio_venta_dia=?, precio_bloqueo_dia=? where id=?"),
       CREAR_CATEGORIA("insert into categoria_pelicula (nombre) values(?)"),
       BUSCAR_CATEGORIA("select * from categoria_pelicula where nombre=?"),
       OBTENER_CATEGORIAS("select * from categoria_pelicula"),
       OBTENER_CATEGORIA("select * from categoria_pelicula where id=?"),
       OBTENER_PELICULA("select * from pelicula where codigo=?"),
       CREAR_PELICULA("insert into pelicula (codigo, titulo, sinopsis, duracion, director, cast, clasificacion, fecha_estreno) "
               + "values (?, ?, ?, ?, ?, ?, ?, ?)"),
       CREAR_REGISTRO_CATEGORIAS_PELICULA("insert into registro_categoria_pelicula (id_categoria, codigo_pelicula)"
               + "values (?,?)"),
       OBTENER_PELICULAS("select * from pelicula where activa=1"),
       OBTENER_CATEGORIAS_PELICULA("select * from registro_categoria_pelicula join categoria_pelicula "
               + "on id_categoria=id where codigo_pelicula=?"),
       ;
       
       private String peticion;

       private PeticionAdminSistema(String peticion) {
              this.peticion = peticion;
       }

       public String get() {
              return peticion;
       }
       
       
}
