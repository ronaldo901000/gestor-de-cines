package com.ronaldo.gestor.cines.api.rest.db.anuncios;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.db.usuarios.UsuariosDB;
import com.ronaldo.gestor.cines.api.rest.dtos.anuncios.AnuncioResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAnunciante;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.anuncios.Anuncio;
import com.ronaldo.gestor.cines.api.rest.models.anuncios.AnuncioImagen;
import com.ronaldo.gestor.cines.api.rest.services.anuncios.CRUDAnuncios;
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

       /**
        * 
        * @param anuncio
        * @param costoTotal
        * @throws DataBaseException 
        */
       public void crearAnuncioImagen(AnuncioImagen anuncio, double costoTotal) throws DataBaseException {
              UsuariosDB usuariosDB = new UsuariosDB();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     connection.setAutoCommit(false);
                     try (PreparedStatement insert = connection.
                             prepareStatement(PeticionesAnunciante.CREAR_ANUNCIO_IMAGEN.get())) {

                            insertarDatosComunesAnuncio(insert, anuncio);
                            insert.setBytes(9, anuncio.getImagen());
                            insert.executeUpdate();
                            //se actualiza el credito del usuario
                            usuariosDB.pagar(anuncio.getIdAnunciante(), costoTotal, connection);
                            //se confirma la transaccion
                            connection.commit();

                     } catch (SQLException ex) {
                            connection.rollback();
                            ex.printStackTrace();
                            throw new DataBaseException("No se pudo crear el anuncio de imagen en la db");
                     } finally {
                            connection.setAutoCommit(true);
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear anuncio en la db");
              }
       }

       /**
        * 
        * @param insert
        * @param anuncio
        * @throws SQLException 
        */
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

       /**
        * 
        * @param idAnunciante
        * @return
        * @throws DataBaseException 
        */
       public List<AnuncioResponse> obtenerAnuncios(String idAnunciante) throws DataBaseException {
              List<AnuncioResponse> anuncios = new ArrayList<>();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionesAnunciante.OBTENER_MIS_ANUNCIOS.get())) {
                            query.setString(1, idAnunciante);
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   anuncios.add(crearAnuncio(resultSet));
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener anuncio en la db");
              }
              return anuncios;
       }

       /**
        * 
        * @param inicio
        * @param fin
        * @return
        * @throws DataBaseException
        */
       public List<AnuncioResponse> obtenerAnunciosPorRango(int inicio, int fin) throws DataBaseException {
              List<AnuncioResponse> anuncios = new ArrayList<>();
              int contador = 0;

              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     connection.setAutoCommit(false);
                     try (PreparedStatement query = connection.prepareStatement(PeticionesAnunciante.OBTENER_ANUNCIOS_POR_RANGO.get())) {
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   if (contador >= inicio && contador < fin) {
                                          AnuncioResponse anuncio = crearAnuncio(resultSet);
                                          anuncios.add(anuncio);
                                          agregarDiaActivo(connection, anuncio);
                                   }
                                   contador++;
                            }
                            //se confirma la transaccion
                            connection.commit();
                     } catch (SQLException ex) {
                            connection.rollback();
                            ex.printStackTrace();
                            throw new DataBaseException("No se pudo hacer transaccion en la db");
                     } finally {
                            connection.setAutoCommit(true);
                     }

              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener anuncios en la db");
              }
              return anuncios;
       }

       /**
        * 
        * @param connection
        * @param anuncio
        * @throws DataBaseException 
        */
       private void agregarDiaActivo(Connection connection, AnuncioResponse anuncio) throws DataBaseException {
              try (PreparedStatement update = connection.
                      prepareStatement(PeticionesAnunciante.AGREGAR_DIA_ACTIVO_ANUNCIO.get())) {
                     update.setString(1, anuncio.getCodigo());
                     update.executeUpdate();

                     if (anuncio.getDiasDuracion() <= anuncio.getDiasActivo() + 1) {
                            desactivarAnuncio(connection, anuncio.getCodigo());
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al agregar dia a anuncio en la base de datos");
              }
       }

       private void desactivarAnuncio(Connection connection, String codigoAnuncio) throws DataBaseException {
              try (PreparedStatement update = connection.
                      prepareStatement(PeticionesAnunciante.DESACTIVAR_ANUNCIO.get())) { 
                     update.setString(1, codigoAnuncio);
                     update.executeUpdate();
              } catch (SQLException e) {
                     throw new DataBaseException("Error al desactivar anuncio en la base de datos");
              }
       }

       /**
        *
        * @param codigo
        * @return
        * @throws DataBaseException 
        */
       public Optional<byte[]> obtenerImagenAnuncio(String codigo) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionesAnunciante.OBTENER_ANUNCIO_IMAGEN.get())) {
                            query.setString(1, codigo);
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   return Optional.of(resultSet.getBytes("imagen"));
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener anuncio para mostrar en la db");
              }
              return Optional.empty();
       }

       /**
        * 
        * @param resultSet
        * @return
        * @throws SQLException 
        */
       public AnuncioResponse crearAnuncio(ResultSet resultSet) throws SQLException {
              AnuncioResponse anuncio = new AnuncioResponse();
              anuncio.setCodigo(resultSet.getString("codigo"));
              anuncio.setIdAnunciante(resultSet.getString("id_anunciante"));
              anuncio.setTitulo(resultSet.getString("titulo"));
              anuncio.setTipo(resultSet.getString("tipo"));
              anuncio.setDescripcion(resultSet.getString("descripcion"));
              anuncio.setFechaRegistro(LocalDate.parse(resultSet.getString("fecha_registro")));
              anuncio.setPrecio(resultSet.getDouble("precio"));
              anuncio.setDiasDuracion(resultSet.getInt("duracion_dias"));
              anuncio.setDiasActivo(resultSet.getInt("dias_activo"));
              anuncio.setActivo(resultSet.getBoolean("activo"));
              return anuncio;
       }

       /**
        * 
        * @param codigo
        * @return
        * @throws DataBaseException 
        */
       public Optional<String> obtenerLink(String codigo) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionesAnunciante.OBTENER_ANUNCIO_IMAGEN.get())) {
                            query.setString(1, codigo);
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   return Optional.of(resultSet.getString("link_video"));
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener link del anuncio para mostrar en la db");
              }
              return Optional.empty();
       }
       
}
