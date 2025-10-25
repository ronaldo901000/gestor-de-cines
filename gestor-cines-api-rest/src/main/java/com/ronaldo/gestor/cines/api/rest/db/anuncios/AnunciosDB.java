package com.ronaldo.gestor.cines.api.rest.db.anuncios;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.db.usuarios.UsuariosDB;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.anuncios.Anuncio;
import com.ronaldo.gestor.cines.api.rest.services.anuncios.CRUDAnuncios;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ronaldo
 */
public class AnunciosDB {

       public void crearAnuncioTXTOVideo(Anuncio anuncio, String linkVideo, double saldoCreditosAnunciante, String query, int tipo) throws DataBaseException {
              UsuariosDB usuariosDB = new UsuariosDB();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     connection.setAutoCommit(false);
                     try (PreparedStatement insert = connection.
                             prepareStatement(query)) {

                            insertarDatosComunesAnuncio(insert, anuncio);
                            if (tipo == CRUDAnuncios.ID_PRECIO_ANUNCIO_VIDEO) {
                                   insert.setString(9, linkVideo);
                            }
                            insert.executeUpdate();
                            //se actualiza el credito del usuario
                            usuariosDB.actualizarCreditos(anuncio.getIdAnunciante(), saldoCreditosAnunciante, connection);
                            //se confirma la transaccion
                            connection.commit();

                     } catch (SQLException ex) {
                            connection.rollback();
                            ex.printStackTrace();
                            throw new DataBaseException("No se pudo crear el anuncio de texto en la db");
                     } finally {
                            connection.setAutoCommit(true);
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear anuncio en la db");
              }
       }


       public void insertarDatosComunesAnuncio(PreparedStatement insert, Anuncio anuncio) throws SQLException {
              insert.setString(1, anuncio.getCodigo());
              insert.setString(2, anuncio.getIdAnunciante());
              insert.setString(3, anuncio.getTitulo());
              insert.setString(4, anuncio.getTipo());
              insert.setString(5, anuncio.getDescripcion());
              insert.setDate(6, Date.valueOf(anuncio.getFechaRegistro()));
              insert.setDouble(7, anuncio.getPrecio());
              insert.setInt(8, anuncio.getDuracion());
       }

}
