package com.ronaldo.gestor.cines.api.rest.db.preciosAnunciosDB;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAnunciante;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.preciosAnuncios.PrecioAnuncio;
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
public class PreciosAnunciosDB {

       /**
        * 
        * @return
        * @throws DataBaseException 
        */
       public List<PrecioAnuncio> obtenerPrecios() throws DataBaseException {
              List<PrecioAnuncio> precios = new ArrayList<>();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()){
                     
                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionAdminSistema.OBTENER_PRECIOS_ANUNCIOS.get())) {
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   precios.add(new PrecioAnuncio(
                                           resultSet.getInt("id"),
                                           resultSet.getString("tipo"),
                                           resultSet.getDouble("precio_venta_dia"),
                                           resultSet.getDouble("precio_bloqueo_dia"))
                                   );
                            }

                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener precios de anuncios en la db");
              }
              return precios;
       }

       public double obtenerPrecio(int id) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {

                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionesAnunciante.OBTENER_COSTO_ANUNCIO.get())) {
                            query.setInt(1, id);
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   return resultSet.getDouble("precio_venta_dia");
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener precios de anuncios en la db");
              }
              return -1;
       }

       /**
        * 
        * @param precioAnuncio
        * @throws DataBaseException 
        */
       public void actualizar(PrecioAnuncio precioAnuncio) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()){
                     
                     try (PreparedStatement update = connection.
                             prepareStatement(PeticionAdminSistema.ACTUALIZAR_PRECIOS_ANUNCIO.get())) {
                            update.setDouble(1, precioAnuncio.getPrecioVentaPorDia());
                            update.setDouble(2, precioAnuncio.getPrecioBloqueoPorDia());
                            update.setInt(3, precioAnuncio.getId());
                            update.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al actualizar precios del anuncio en la db");
              }
       }

       public void actualizarCostoBloqueo(double costo) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {

                     try (PreparedStatement update = connection.
                             prepareStatement(PeticionAdminSistema.ACTUALIZAR_COSTO_BLOQUEO_ANUNCIO.get())) {
                            update.setDouble(1, costo);
                            update.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al actualizar costo de bloqueo de anuncio en la db");
              }
       }

       public double obtenerCostoBloqueo() throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {

                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionAdminSistema.OBTENER_COSTO_BLOQUEO_ANUNCIO.get())) {
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   return resultSet.getDouble("costo");
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener costo de bloqueo de anuncio en la db");
              }
              return -1;
       }
}
