package com.ronaldo.gestor.cines.api.rest.db.adminCine;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.adminCine.AdminCine;
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
public class AdminCineDB {

       public void crearAdmin(AdminCine adminCine) throws DataBaseException {
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement insert = connection.prepareStatement(PeticionAdminSistema.CREAR_ADMIN_CINE.get())) {
                            insert.setString(1, adminCine.getId());
                            insert.setString(2, adminCine.getCodigoCine());
                            insert.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear admin cine en la db");
              }

       }

       public List<Usuario> obtenerAdminsCine(String codigoCine) throws DataBaseException {
              List<Usuario> admins = new ArrayList<>();
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement query = connection.prepareStatement(PeticionAdminSistema.OBTENER_TODOS_ADMINS_DE_CINE.get())) {
                            query.setString(1, codigoCine);
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   admins.add(new Usuario(
                                           resultSet.getString("id"),
                                           resultSet.getString("nombre"),
                                           resultSet.getString("correo"),
                                           resultSet.getString("telefono"))
                                   );
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener admins de cine en la db");
              }
              return admins;
       }
       
}
