package com.ronaldo.gestor.cines.api.rest.db.usuarios;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionUsuario;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ronaldo
 */
public class CuentasDB {

       /**
        * 
        * @param usuario
        * @throws DataBaseException 
        */
       public void crear(Usuario usuario) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement insert = connection.prepareStatement(PeticionUsuario.CREAR_CUENTA.get())) {
                            insert.setString(1, usuario.getId());
                            insert.setString(2, usuario.getNombre());
                            insert.setString(3, usuario.getCorreo());
                            insert.setString(4, usuario.getContraseña());
                            insert.setString(5, usuario.getTelefono());
                            insert.executeUpdate();
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error en la creacion de la cuenta en la db");
              }
       }

}
