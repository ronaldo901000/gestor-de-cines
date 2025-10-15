package com.ronaldo.gestor.cines.api.rest.db.general;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
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
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
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
}
