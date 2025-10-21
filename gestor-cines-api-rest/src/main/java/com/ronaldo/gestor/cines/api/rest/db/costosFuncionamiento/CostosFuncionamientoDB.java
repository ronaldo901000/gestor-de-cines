package com.ronaldo.gestor.cines.api.rest.db.costosFuncionamiento;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.costosFuncionamiento.CostoFuncionamiento;
import com.ronaldo.gestor.cines.api.rest.models.costoGlobal.CostoGlobal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CostosFuncionamientoDB {

       /**
        *
        * @param costo
        * @throws DataBaseException
        */
       public void crearRegistro(CostoFuncionamiento costo) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement insert
                             = connection.prepareStatement(PeticionAdminSistema.CREAR_COSTO_FUNCIONAMIENTO_CINE.get())) {
                            insert.setString(1, costo.getCodigoCine());
                            insert.setDate(2, Date.valueOf(costo.getFechaRegistro()));
                            insert.setDouble(3, costo.getCosto());
                            insert.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear nuevo costo de funcionamiento en la db");
              }
       }

       /**
        * 
        * @param costo
        * @throws DataBaseException 
        */
       public void sobreEscribir(CostoFuncionamiento costo) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement insert
                             = connection.prepareStatement(PeticionAdminSistema.SOBREESCRIBIR_COSTO_FUNCIO_CINE.get())) {
                            insert.setDate(1, Date.valueOf(costo.getFechaRegistro()));
                            insert.setDouble(2, costo.getCosto());
                            insert.setString(3, costo.getCodigoCine());
                            insert.setDate(4, Date.valueOf(costo.getFechaRegistro()));
                            insert.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al sobreescribir costo de funcionamiento en la db");
              }
       }

       /**
        * 
        * @param costo
        * @return
        * @throws DataBaseException 
        */
       public boolean existeRegistroCostoEnFecha(CostoFuncionamiento costo) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query
                             = connection.prepareStatement(PeticionAdminSistema.BUSCAR_COSTO_EN_FECHA.get())) {
                            query.setString(1, costo.getCodigoCine());
                            query.setDate(2, Date.valueOf(costo.getFechaRegistro()));

                            ResultSet resultSet = query.executeQuery();
                            return resultSet.next();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtemer respuesta en la db");
              }
       }

       public List<CostoFuncionamiento> obtenerCostosPorNombreOCodigo(String palabra) throws DataBaseException {
              List<CostoFuncionamiento> costos = new ArrayList<>();

              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {

                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionAdminSistema.OBTENER_COSTOS_CINE_POR_CODIGO_O_NOMBRE.get())) {
                            query.setString(1, palabra);
                            query.setString(2, palabra);

                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   costos.add(new CostoFuncionamiento(
                                           resultSet.getInt("id"),
                                           resultSet.getString("codigo_cine"),
                                           LocalDate.parse(resultSet.getString("fecha_registro")),
                                           resultSet.getDouble("costo_dia"))
                                   );
                                   
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al sobreescribir costo de funcionamiento en la db");
              }
              return costos;
       }

       
       public void eliminar(String id) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement insert
                             = connection.prepareStatement(PeticionAdminSistema.ELIMINAR_COSTO_CINE.get())) {
                            insert.setString(1, id);
                            insert.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al sobreescribir costo de funcionamiento en la db");
              }
       }

       public CostoGlobal obtenerCostoGlobal() throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query
                             = connection.prepareStatement(PeticionAdminSistema.OBTENER_COSTO_GLOBAL.get())) {
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   return new CostoGlobal(resultSet.getDouble("costo"));
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener costo global en la db");
              }
              return null;
       }

       /**
        *
        * @param nuevoCosto
        * @throws DataBaseException
        */
       public void actualizarCostoGlobal(double nuevoCosto) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query
                             = connection.prepareStatement(PeticionAdminSistema.ACTUALIZAR_COSTO_GLOBAL.get())) {
                            query.setDouble(1, nuevoCosto);
                            query.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al actualizar costo global en la db");
              }
       }

       public void crearRegistroInicialPelicula(CostoFuncionamiento costo, Connection connection) throws DataBaseException {
              try (PreparedStatement insert
                      = connection.prepareStatement(PeticionAdminSistema.CREAR_COSTO_FUNCIONAMIENTO_CINE.get())) {
                     insert.setString(1, costo.getCodigoCine());
                     insert.setDate(2, Date.valueOf(costo.getFechaRegistro()));
                     insert.setDouble(3, costo.getCosto());
                     insert.executeUpdate();

              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear nuevo costo de funcionamiento en la db");
              }
       }

       public boolean esCostoInicial(String id) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query
                             = connection.prepareStatement(PeticionAdminSistema.OBTENER_FECHAS_REGISTRO_COSTO.get())) {
                            query.setString(1, id);
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   String fechaRegistro = resultSet.getString("fecha_registro");
                                   String fechaCreacion = resultSet.getString("fecha_creacion");
                                   
                                   if(fechaRegistro.equals(fechaCreacion)){
                                          return true;
                                   }
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al comparar fechas del costo de cine en la db");
              }
              return false;
       }
}
