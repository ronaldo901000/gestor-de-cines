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
       ELIMINAR_SALA("delete from sala where codigo=?"),
       OBTENER_PROYECCION("select * from proyeccion where codigo=?"),
       BUSCAR_PELICULA("select * from pelicula where codigo=?"),
       VERIFICAR_DISPONIBILIDAD_SALA("select * from proyeccion where codigo_sala = ? and fecha = ? "
               + "and hora_inicio < ? and hora_fin > ?"),
       CREAR_PROYECCION("insert into proyeccion (codigo, codigo_pelicula, codigo_sala, fecha, hora_inicio, hora_fin, precio) "
               + "values(?, ?, ?, ?, ?, ?, ?)"),
       OBTENER_PROYECCIONES_POR_RANGO("select * from proyeccion p join sala s on p.codigo_sala=s.codigo where s.codigo_cine=?"),
       OBTENER_PROYECCION_POR_CODIGO("select * from proyeccion p join sala s on p.codigo_sala=s.codigo where p.codigo=?"),
       ACTUALIZAR_PROYECCION("update proyeccion set codigo_pelicula=?, codigo_sala=?, fecha=?, hora_inicio=?, hora_fin=?, "
               + "precio=? where codigo=?"),
       VERIFICAR_DISPONIBILIDAD_SALA_EN_ACTUALIZACION("select * from proyeccion where codigo_sala = ? and fecha = ? "
               + "and hora_inicio < ? and hora_fin > ? and codigo!=?"),
       ELIMINAR_PROYECCION("delete from proyeccion where codigo=?"),
       CAMBIAR_VISIBILIDAD_SALA("update sala set activo=? where codigo=?"),
       OBTENER_SALDO_CINE("select * from cine where codigo=?"),
       RECARGAR_CARTERA_CINE("update cine set creditos_cartera = creditos_cartera + ? where codigo=?"),
       OBTENER_MI_CINE("select * from admin_cine join cine on codigo_cine=codigo where id_usuario=?"),
       ACTUALIZAR_ESTADO_PROYECCION("update proyeccion set disponible=? where codigo=?"),
       PAGAR("update cine set creditos_cartera=creditos_cartera-? where codigo=?"),
       OBTENER_PAGO_BLOQUEO("select * from pago_bloqueo_anuncio where activo=1 and codigo_cine=?"),
       AGREGAR_DIA_ACTIVO_BLOQUEADOR("update pago_bloqueo_anuncio set dias_activo=dias_activo+1 where id=?"),
       CAMBIAR_ESTADO_ACTIVO_BLOQUEADOR("update pago_bloqueo_anuncio set activo=? where id=?"),
       OBTENER_PROYECCIONES_POR_SALA("select * from proyeccion p join sala s on p.codigo_sala=s.codigo where s.codigo=?"),
       ;
       private String peticion;

       private PeticionesAdminCine(String peticion) {
              this.peticion = peticion;
       }

       public String get() {
              return peticion;
       }

}
