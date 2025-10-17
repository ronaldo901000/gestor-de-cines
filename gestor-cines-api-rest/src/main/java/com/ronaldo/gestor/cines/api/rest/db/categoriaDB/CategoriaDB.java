package com.ronaldo.gestor.cines.api.rest.db.categoriaDB;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.categorias.Categoria;
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
public class CategoriaDB {

       public void crear(Categoria categoria) throws DataBaseException {
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement insert = connection.
                             prepareStatement(PeticionAdminSistema.CREAR_CATEGORIA.get())) {
                            insert.setString(1, categoria.getNombre());
                            insert.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear categoria en la db");
              }
       }
       
       /**
        * 
        * @return
        * @throws DataBaseException 
        */
       public List<Categoria> obtener() throws DataBaseException {
              List<Categoria> categorias = new ArrayList<>();
              
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionAdminSistema.OBTENER_CATEGORIAS.get())) {
                            ResultSet resultSet = query.executeQuery();
                            
                            while (resultSet.next()) {
                                   categorias.add(new Categoria(
                                           resultSet.getInt("id"),
                                           resultSet.getString("nombre"))
                                   );
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener categorias en la db");
              }
              return categorias;
       }
       
}
