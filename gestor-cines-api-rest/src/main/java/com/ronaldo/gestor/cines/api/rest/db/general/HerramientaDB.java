package com.ronaldo.gestor.cines.api.rest.db.general;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ronaldo
 */
public class HerramientaDB {

       /**
        * 
        * @param llavePrimaria
        * @param peticion
        * @return
        * @throws DataBaseException 
        */
       public boolean existeEntidad(String llavePrimaria, String peticion) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()){
                     
                     try (PreparedStatement query = connection.prepareStatement(peticion)) {
                            query.setString(1, llavePrimaria);
                            ResultSet resultSet = query.executeQuery();
                            return resultSet.next();
                     }

              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al buscar la existencia de la entidad");
              }
       }

       public boolean correoDisponibleActualizacion(String correo, String id, String peticion) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {

                     try (PreparedStatement query = connection.prepareStatement(peticion)) {
                            query.setString(1, correo);
                            query.setString(2, id);
                            ResultSet resultSet = query.executeQuery();
                            return resultSet.next();
                     }

              } catch (SQLException e) {
                     throw new DataBaseException("Error al buscar la disponibilidad del correo");
              }
       }

       public boolean existeCategoria(String id) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()){
                     
                     try (PreparedStatement query = connection.prepareStatement(PeticionAdminSistema.OBTENER_CATEGORIA.get())) {
                            query.setString(1, id);
                            ResultSet resultSet = query.executeQuery();
                            return resultSet.next();
                     }

              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al buscar la existencia de la entidad");
              }
       }
}
