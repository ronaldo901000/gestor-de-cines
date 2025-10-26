package com.ronaldo.gestor.cines.api.rest.db.usuarios;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionUsuario;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.recargas.Recarga;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author ronaldo
 */
public class UsuariosDB {

       public Optional<Usuario> obtenerUsuarioPorCorreo(String correo) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionUsuario.OBTENER_USUARIO_POR_CORREO.get())) {
                            query.setString(1, correo);

                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   Usuario usuario = new Usuario();
                                   usuario.setId(resultSet.getString("id"));
                                   usuario.setNombre(resultSet.getString("nombre"));
                                   usuario.setCorreo(resultSet.getString("correo"));
                                   usuario.setTelefono(resultSet.getString("telefono"));
                                   usuario.setCreditos(resultSet.getDouble("creditos"));
                                   return Optional.of(usuario);
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener usuario en la db");
              }
              return Optional.empty();
       }

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

       public void recargarCartera(Recarga recarga) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement update = connection.prepareStatement(PeticionUsuario.ACTUALIZAR_SALDO.get())) {
                            update.setDouble(1, recarga.getMonto());
                            update.setString(2, recarga.getLlavePrimaria());
                            update.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al realiza recarga de cartera en la base de datos");
              }
       }

       /**
        * 
        * @param id
        * @return
        * @throws DataBaseException 
        */
       public Optional<Usuario> obteneUsuario(String id) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.prepareStatement(PeticionUsuario.OBTENER_USUARIO.get())) {
                            query.setString(1, id);
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   Usuario usuario = new Usuario();
                                   usuario.setId(resultSet.getString("id"));
                                   usuario.setNombre(resultSet.getString("nombre"));
                                   usuario.setCorreo(resultSet.getString("correo"));
                                   usuario.setTelefono(resultSet.getString("telefono"));
                                   return Optional.of(usuario);
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener usuario en la base de datos");
              }
              return Optional.empty();
       }
       
}
