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
       ACTUALIZAR_CUENTA("update usuario set nombre=?, correo=?, telefono=? where id=?")
       ;
       
       private String peticion;

       private PeticionUsuario(String peticion) {
              this.peticion = peticion;
       }

       public String get() {
              return peticion;
       }
}
