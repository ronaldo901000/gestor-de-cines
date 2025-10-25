package com.ronaldo.gestor.cines.api.rest.services.usuarios;

import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.db.usuarios.UsuariosDB;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;

/**
 *
 * @author ronaldo
 */
public class CRUDUsuarios {

       public double obtenerSaldoActual(String idUsuario) throws DataBaseException, EntityNotFoundException {
              HerramientaDB herramientaDB = new HerramientaDB();
              UsuariosDB usuariosDB = new UsuariosDB();

              if (!herramientaDB.existeEntidad(idUsuario, PeticionAdminSistema.OBTENER_USUARIO.get())) {
                     throw new EntityNotFoundException("Usuario no encontrado en la base de datos");
              }
              return usuariosDB.obtenerCreditos(idUsuario);
       }
}
