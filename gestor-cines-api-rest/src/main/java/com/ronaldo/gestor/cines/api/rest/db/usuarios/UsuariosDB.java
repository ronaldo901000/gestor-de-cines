package com.ronaldo.gestor.cines.api.rest.db.usuarios;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionUsuario;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ronaldo
 */
public class UsuariosDB {

       public void actualizarCreditos(String idUsuario, double saldoCreditosAnunciante, Connection connection) throws DataBaseException {
              try (PreparedStatement insert = connection.
                      prepareStatement(PeticionUsuario.ACTUALIZAR_SALDO.get())) {
                     insert.setDouble(1, saldoCreditosAnunciante);
                     insert.setString(2, idUsuario);
                     insert.executeUpdate();

              } catch (SQLException e) {
                     throw new DataBaseException("Error al actualizar creditos usuario");
              }
       }

       /**
        * 
        * @param idUsuario
        * @return
        * @throws DataBaseException 
        */
       public double obtenerCreditos(String idUsuario) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {

                     try (PreparedStatement query = connection.prepareStatement(PeticionUsuario.OBTENER_USUARIO.get())) {
                            query.setString(1, idUsuario);
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   return resultSet.getDouble("creditos");
                            }
                     }

              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener creditos del usuario en la db");
              }
              return -1;
       }
}
