package com.ronaldo.gestor.cines.api.rest.db.adminSistema;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.adminsSistema.AdminSistema;
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
public class AdminSistemaDB {
       
       /**
        * 
        * @param admin
        * @throws DataBaseException 
        */
       public void crearAdmin(AdminSistema admin) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement insert = connection.
                             prepareStatement(PeticionAdminSistema.CREAR_ADMIN_SISTEMA.get())) {
                            insert.setString(1, admin.getId());
                            insert.executeUpdate();
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al crear admin de sistema en la base de datos");
              }
       }

       /**
        * 
        * @return
        * @throws DataBaseException 
        */
       public List<Usuario> obtenerAdmins() throws DataBaseException {
              List<Usuario> admins = new ArrayList<>();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionAdminSistema.OBTENER_ADMINS_SISTEMA.get())) {
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   admins.add(new Usuario(
                                           resultSet.getString("id"),
                                           resultSet.getString("nombre"),
                                           resultSet.getString("correo"),
                                           resultSet.getString("telefono")
                                   ));
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener admins de sistema en la base de datos");
              }
              return admins;
       }

       /**
        * 
        * @param id
        * @throws DataBaseException 
        */
       public void eliminar(String id) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement delete = connection.
                             prepareStatement(PeticionAdminSistema.ELIMINAR_ADMINS_SISTEMA.get())) {
                            delete.setString(1, id);
                            delete.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al eliminar admin de sistema en la base de datos");
              }
       }

}
