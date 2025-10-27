package com.ronaldo.gestor.cines.api.rest.db.compraBoletos;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.db.proyeccion.ProyeccionDB;
import com.ronaldo.gestor.cines.api.rest.db.usuarios.UsuariosDB;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionUsuario;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.compraBoletos.CompraBoletos;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ronaldo
 */
public class CompraBoletosDB {
       
       public void crearCompra(CompraBoletos compra, boolean hayDisponibilidad) throws DataBaseException {
              UsuariosDB usuariosDB = new UsuariosDB();
              ProyeccionDB proyeccionDB = new ProyeccionDB();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     connection.setAutoCommit(false);
                     try (PreparedStatement insert = connection.
                             prepareStatement(PeticionUsuario.CREAR_COMPRA_BOLETO.get())) {
                            insert.setString(1, compra.getIdUsuario());
                            insert.setString(2, compra.getCodigoProyeccion());
                            insert.setDate(3, Date.valueOf(compra.getFechaCompra()));
                            insert.setInt(4, compra.getCantidad());
                            insert.setDouble(5, compra.getCostoTotal());
                            insert.executeUpdate();
                            //se descuentan los creditos al usuario
                            usuariosDB.pagar(compra.getIdUsuario(), compra.getCostoTotal(), connection);
                            //se cambia el estado de la proyeccion si ya no hay cupos
                            proyeccionDB.cambiarEstadoDisponibilidad(compra.getCodigoProyeccion(), hayDisponibilidad, connection);

                            //se confirma la transaccion
                            connection.commit();
                            
                     } catch (SQLException ex) {
                            connection.rollback();
                            ex.printStackTrace();
                            throw new DataBaseException("No se pudo crear la compra de boletos en la db, se hizo rollback");
                     } finally {
                            connection.setAutoCommit(true);
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear compra de boletos en la db");
              }
       }


       public int obtenerBoletosVendidos(String codigoProyeccion) throws DataBaseException {
              int contador = 0;
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionUsuario.OBTENER_COMPRA_BOLETOS_POR_PROYECCION.get())) {
                            query.setString(1, codigoProyeccion);
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   contador += resultSet.getInt("cantidad");
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener boletos vendidos en la db");
              }
              return contador;
       }

}
