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
       CREAR_PELICULA("insert into pelicula (codigo, titulo, sinopsis, duracion, director, cast, clasificacion, fecha_estreno,poster) "
               + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)"),
       CREAR_REGISTRO_CATEGORIAS_PELICULA("insert into registro_categoria_pelicula (id_categoria, codigo_pelicula)"
               + "values (?,?)"),
       OBTENER_PELICULAS("select * from pelicula where activa=1"),
       OBTENER_CATEGORIAS_PELICULA("select * from registro_categoria_pelicula join categoria_pelicula "
               + "on id_categoria=id where codigo_pelicula=?"),
       ACTUALIZAR_PELICULA("UPDATE pelicula SET titulo = ?, sinopsis = ?, duracion = ?, director = ?, cast = ?,"
               + " clasificacion = ?, fecha_estreno = ? WHERE codigo = ?"),
       ELIMINAR_REGISTRO_CATEGORIAS_PELICULA("delete from registro_categoria_pelicula where codigo_pelicula=?"),
       ELIMINAR_PELICULA("delete from pelicula where codigo=?"),
       CREAR_COSTO_FUNCIONAMIENTO_CINE("insert into costo_funcionamiento (codigo_cine, fecha_registro, costo_dia)"
               + "values(?, ?, ?)"),
       SOBREESCRIBIR_COSTO_FUNCIO_CINE("update costo_funcionamiento set fecha_registro=?, costo_dia=? where codigo_cine=? and fecha_registro=?"),
       BUSCAR_COSTO_EN_FECHA("select * from costo_funcionamiento where codigo_cine=? and fecha_registro=?"),
       OBTENER_COSTOS_CINE_POR_CODIGO_O_NOMBRE("select * from costo_funcionamiento join cine on codigo_cine=codigo "
               + "where codigo=? or nombre=?"),
       ELIMINAR_COSTO_CINE("delete from costo_funcionamiento where id=?"),
       BUSCAR_COSTO_POR_ID("select * from costo_funcionamiento where id=?"),
       
       OBTENER_COSTO_GLOBAL("select * from costo_global"),
       ACTUALIZAR_COSTO_GLOBAL("update costo_global set costo=? where id=1"),
       CREAR_ADMIN_SISTEMA("insert into admin_sistema (id_usuario) values(?)"),
       OBTENER_ADMINS_SISTEMA("select * from admin_sistema join usuario on id_usuario=id where activo=1"),
       ELIMINAR_ADMINS_SISTEMA("delete from admin_sistema where id_usuario=?"),
       OBTENER_FECHAS_REGISTRO_COSTO("select * from costo_funcionamiento join cine on codigo_cine=codigo where id=?"),
       OBTENER_UN_ANUNCIANTE("select * from anunciante where id_usuario=?"),
       ACTUALIZAR_COSTO_BLOQUEO_ANUNCIO("update costo_bloqueo_anuncio set costo=?"),
       OBTENER_COSTO_BLOQUEO_ANUNCIO("select * from costo_bloqueo_anuncio"),
       PAGAR_BLOQUEO_ANUNCIOS("insert into pago_bloqueo_anuncio (codigo_cine, fecha_pago, total_dias, costo) values(?, ?, ?, ?)"),
       OBTENER_TODOS_LOS_BOLETOS_VENDIDOS_POR_SALA("select * from compra_boletos c join proyeccion p on c.codigo_proyeccion = p.codigo where p.codigo_sala=?");
       
       private String peticion;

       private PeticionAdminSistema(String peticion) {
              this.peticion = peticion;
       }

       public String get() {
              return peticion;
       }
       
       
}
