package com.ronaldo.gestor.cines.api.rest.db.adminCine;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAdminCine;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.adminCine.AdminCine;
import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ronaldo
 */
public class AdminCineDB {

       /**
        * 
        * @param adminCine
        * @throws DataBaseException 
        */
       public void crearAdmin(AdminCine adminCine) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()){
                     
                     try (PreparedStatement insert = connection.prepareStatement(PeticionAdminSistema.CREAR_ADMIN_CINE.get())) {
                            insert.setString(1, adminCine.getId());
                            insert.setString(2, adminCine.getCodigoCine());
                            insert.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear admin cine en la db");
              }

       }

       /**
        * 
        * @param codigoCine
        * @return
        * @throws DataBaseException 
        */
       public List<Usuario> obtenerAdminsCine(String codigoCine) throws DataBaseException {
              List<Usuario> admins = new ArrayList<>();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()){
                     
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

       /**
        *
        * @param id
        * @throws DataBaseException
        */
       public void eliminar(String id) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()){
                     
                     try (PreparedStatement delete = connection.prepareStatement(PeticionAdminSistema.ELIMINAR_ADMIN_CINE.get())) {
                            delete.setString(1, id);
                            delete.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al eliminar admin cine en la db");
              }

       }

       /**
        * 
        * @param idUsuario
        * @return
        * @throws DataBaseException 
        */
       public Optional<Cine> obtenerCine(String idUsuario) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {

                     try (PreparedStatement query = connection.prepareStatement(PeticionesAdminCine.OBTENER_MI_CINE.get())) {
                            query.setString(1, idUsuario);
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   Cine cine = new Cine();
                                   cine.setCodigo(resultSet.getString("codigo"));
                                   cine.setNombre(resultSet.getString("nombre"));
                                   cine.setUbicacion(resultSet.getString("ubicacion"));
                                   cine.setFechaCreacion(LocalDate.parse(resultSet.getString("fecha_creacion")));
                                   return Optional.of(cine);
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener cine donde trabaja el admin en la db");
              }
              return Optional.empty();
       }

}
