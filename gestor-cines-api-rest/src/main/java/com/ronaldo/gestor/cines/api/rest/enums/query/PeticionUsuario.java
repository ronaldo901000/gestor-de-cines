package com.ronaldo.gestor.cines.api.rest.enums.query;

/**
 *
 * @author ronaldo
 */
public enum PeticionUsuario {
       ACTUALIZAR_SALDO("update usuario set creditos=? where id=?"),
       OBTENER_USUARIO("select * from usuario where id=?"),
       CREAR_CUENTA("insert into usuario (id, nombre, correo, contraseña, telefono)"
               + " values (?, ?, ?, ?, ?)"),
       OBTENER_USUARIO_POR_CORREO("select * from usuario where correo=?"),
       VERIFICAR_DISPONIBILIDAD_CORREO("select * from usuario where correo = ? and id != ?"),
       ACTUALIZAR_CUENTA("update usuario set nombre=?, correo=?, telefono=? where id=?"),
       OBTENER_PROYECCIONES_POR_PELICULA("select * from proyeccion p join sala s on p.codigo_sala=s.codigo where p.codigo_pelicula=?"),
       OBTENER_PELICULAS_POR_CATEGORIA_O_TITULO("select distinct pelicula.* from pelicula join registro_categoria_pelicula "
               + "on pelicula.codigo = registro_categoria_pelicula.codigo_pelicula join categoria_pelicula "
               + "on registro_categoria_pelicula.id_categoria = categoria_pelicula.id where pelicula.titulo = ? or categoria_pelicula.nombre = ?"),
       OBTENER_COMPRA_BOLETOS_POR_PROYECCION("select * from compra_boletos where codigo_proyeccion=?"),
       CREAR_COMPRA_BOLETO("insert into compra_boletos (id_usuario, codigo_proyeccion, fecha_compra, cantidad, costo_total)"
               + " values(?, ?, ?, ?, ?)"),
       PAGAR_TRANSACCION("update usuario set creditos=creditos-? where id=?"),
       OBTENER_MIS_BOLETOS("select * from compra_boletos where id_usuario=?"),
       ;
       
       private String peticion;

       private PeticionUsuario(String peticion) {
              this.peticion = peticion;
       }

       public String get() {
              return peticion;
       }
}
