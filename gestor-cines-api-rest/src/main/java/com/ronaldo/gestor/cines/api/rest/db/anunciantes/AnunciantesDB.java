package com.ronaldo.gestor.cines.api.rest.db.anunciantes;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class AnunciantesDB {

       /**
        * 
        * @param idUsuario
        * @throws DataBaseException 
        */
       public void crearAnunciante(String idUsuario) throws DataBaseException {
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement insert = connection.
                             prepareStatement(PeticionAdminSistema.CREAR_ANUNCIANTE.get())) {
                            
                            insert.setString(1, idUsuario);
                            insert.executeUpdate();
                            
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear anunciante en la base de datos");
              }
       }

       /**
        * 
        * @return
        * @throws DataBaseException 
        */
       public List<Usuario> obtenerAnunciantes() throws DataBaseException {
              List<Usuario> anunciantes = new ArrayList<>();
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionAdminSistema.OBTENER_ANUNCIANTES.get())) {
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   anunciantes.add(new Usuario(
                                           resultSet.getString("id"),
                                           resultSet.getString("nombre"),
                                           resultSet.getString("correo"),
                                           resultSet.getString("telefono"))
                                   );
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener anunciantes en la base de datos");
              }
              return anunciantes;
       }

       /**
        * 
        * @param idUsuario
        * @throws DataBaseException 
        */
       public void eliminar(String idUsuario) throws DataBaseException {
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement delete = connection.
                             prepareStatement(PeticionAdminSistema.ELIMINAR_ANUNCIANTE.get())) {
                            delete.setString(1, idUsuario);
                            delete.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al eliminar anunciante en la base de datos");
              }
       }

}
