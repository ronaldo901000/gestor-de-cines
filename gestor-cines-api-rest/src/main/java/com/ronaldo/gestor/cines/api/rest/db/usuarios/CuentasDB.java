package com.ronaldo.gestor.cines.api.rest.db.usuarios;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionUsuario;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.credenciales.Credencial;
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

       /**
        * 
        * @param correo
        * @return
        * @throws DataBaseException 
        */
       public Optional<Credencial> obtenerCredencial(String correo) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionUsuario.OBTENER_USUARIO_POR_CORREO.get())) {
                            query.setString(1, correo);

                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   Credencial credencial = new Credencial();
                                   credencial.setCorreo(resultSet.getString("correo"));
                                   credencial.setContraseña(resultSet.getString("contraseña"));
                                   return Optional.of(credencial);
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener la credencial en la db");
              }
              return Optional.empty();
       }

}
