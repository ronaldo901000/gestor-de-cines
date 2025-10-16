package com.ronaldo.gestor.cines.api.rest.db.cines;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import java.sql.Connection;
import java.sql.Date;
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
public class CinesDB {

       /**
        * 
        * @param cine
        * @throws DataBaseException 
        */
       public void crearCine(Cine cine) throws DataBaseException {
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement insert = connection.prepareStatement(PeticionAdminSistema.CREAR_CINE.get());) {
                            insert.setString(1, cine.getCodigo());
                            insert.setString(2, cine.getNombre());
                            insert.setString(3, cine.getUbicacion());
                            insert.setDate(4, Date.valueOf(cine.getFechaCreacion()));
                            insert.executeUpdate();
                     }

              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al crear cine en la db");
              }
       }

       /**
        * 
        * @param inicio
        * @param fin
        * @return
        * @throws DataBaseException 
        */
       public List<Cine> obtenerCinesPaginacion(int inicio, int fin) throws DataBaseException {
              List<Cine> cines = new ArrayList<>();
              int contador = 0;
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement query = connection.prepareStatement(PeticionAdminSistema.OBTENER_CINES.get());) {
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   if (contador >= inicio && contador < fin) {
                                          cines.add(reconstruirCine(resultSet));
                                   }
                                   contador++;
                            }
                     }

              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener cines en la db");
              }
              return cines;
       }

       /**
        * 
        * @param elementoDeBusqueda
        * @return
        * @throws DataBaseException 
        */
       public List<Cine> obtenerCinePorCodigoONombre(String elementoDeBusqueda) throws DataBaseException {
              List<Cine> cines = new ArrayList<>();
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement query = connection.prepareStatement(PeticionAdminSistema.OBTENER_CINES_POR_CODIGO_NOMBRE.get())) {
                            query.setString(1, elementoDeBusqueda);
                            query.setString(2, elementoDeBusqueda);
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   cines.add(reconstruirCine(resultSet));

                            }
                     }

              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener cines en la db");
              }
              return cines;
       }

       /**
        * 
        * @param codigo
        * @return
        * @throws DataBaseException 
        */
       public Optional<Cine> obtenerCinePorCodigo(String codigo) throws DataBaseException {
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement query = connection.prepareStatement(PeticionAdminSistema.OBTENER_CINE_POR_CODIGO.get())) {
                            query.setString(1, codigo);
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   return Optional.of(reconstruirCine(resultSet));
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener cine por codigo en la db");
              }
              return Optional.empty();
       }

       /**
        * 
        * @param cine
        * @throws DataBaseException 
        */
       public void updateCine(Cine cine) throws DataBaseException {
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement query = connection.prepareStatement(PeticionAdminSistema.ACTUALIZAR_CINE.get())) {
                            query.setString(1, cine.getNombre());
                            query.setString(2, cine.getUbicacion());
                            query.setDate(3, Date.valueOf(cine.getFechaCreacion()));
                            query.setString(4, cine.getCodigo());
                            query.executeUpdate();
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener cine por codigo en la db");
              }
       }

       private Cine reconstruirCine(ResultSet resultSet) throws SQLException {
              return new Cine(
                      resultSet.getString("codigo"),
                      resultSet.getString("nombre"),
                      resultSet.getString("ubicacion"),
                      LocalDate.parse(resultSet.getString("fecha_creacion")));
       }

       /**
        * 
        * @param codigo
        * @throws DataBaseException 
        */
       public void eliminarCine(String codigo) throws DataBaseException {
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     try (PreparedStatement query = connection.prepareStatement(PeticionAdminSistema.ELIMINAR_CINE.get())) {
                            query.setString(1, codigo);
                            query.executeUpdate();
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al eliminar cine en la db");
              }
       }

}
