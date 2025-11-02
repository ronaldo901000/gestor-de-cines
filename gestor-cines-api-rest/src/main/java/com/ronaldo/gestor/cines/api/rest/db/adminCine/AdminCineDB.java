package com.ronaldo.gestor.cines.api.rest.db.adminCine;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.db.cines.CinesDB;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAdminCine;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.adminCine.AdminCine;
import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import com.ronaldo.gestor.cines.api.rest.models.pagosBloqueo.PagoBloqueo;
import com.ronaldo.gestor.cines.api.rest.models.usuario.Usuario;
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
public class AdminCineDB {

       /**
        * 
        * @param adminCine
        * @throws DataBaseException 
        */
       public void crearAdmin(AdminCine adminCine) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()){
                     
                     try (PreparedStatement insert = connection.prepareStatement(PeticionAdminSistema.CREAR_ADMIN_CINE.get())) {
                            insert.setString(1, adminCine.getId());
                            insert.setString(2, adminCine.getCodigoCine());
                            insert.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear admin cine en la db");
              }

       }

       /**
        * 
        * @param codigoCine
        * @return
        * @throws DataBaseException 
        */
       public List<Usuario> obtenerAdminsCine(String codigoCine) throws DataBaseException {
              List<Usuario> admins = new ArrayList<>();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()){
                     
                     try (PreparedStatement query = connection.prepareStatement(PeticionAdminSistema.OBTENER_TODOS_ADMINS_DE_CINE.get())) {
                            query.setString(1, codigoCine);
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   admins.add(new Usuario(
                                           resultSet.getString("id"),
                                           resultSet.getString("nombre"),
                                           resultSet.getString("correo"),
                                           resultSet.getString("telefono"))
                                   );
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener admins de cine en la db");
              }
              return admins;
       }

       /**
        *
        * @param id
        * @throws DataBaseException
        */
       public void eliminar(String id) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     
                     try (PreparedStatement delete = connection.prepareStatement(PeticionAdminSistema.ELIMINAR_ADMIN_CINE.get())) {
                            delete.setString(1, id);
                            delete.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al eliminar admin cine en la db");
              }
              
       }

       /**
        *
        * @param idUsuario
        * @return
        * @throws DataBaseException
        */
       public Optional<Cine> obtenerCine(String idUsuario) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     
                     try (PreparedStatement query = connection.prepareStatement(PeticionesAdminCine.OBTENER_MI_CINE.get())) {
                            query.setString(1, idUsuario);
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   Cine cine = new Cine();
                                   cine.setCodigo(resultSet.getString("codigo"));
                                   cine.setNombre(resultSet.getString("nombre"));
                                   cine.setUbicacion(resultSet.getString("ubicacion"));
                                   cine.setFechaCreacion(LocalDate.parse(resultSet.getString("fecha_creacion")));
                                   return Optional.of(cine);
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener cine donde trabaja el admin en la db");
              }
              return Optional.empty();
       }

       /**
        *
        * @param pago
        * @throws DataBaseException
        */
       public void comprarBloqueoAnuncio(PagoBloqueo pago) throws DataBaseException {
              CinesDB cinesDB = new CinesDB();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     connection.setAutoCommit(false);
                     try (PreparedStatement insert
                             = connection.prepareStatement(PeticionAdminSistema.PAGAR_BLOQUEO_ANUNCIOS.get())) {
                            insert.setString(1, pago.getCodigoCine());
                            insert.setDate(2, Date.valueOf(pago.getFechaPago()));
                            insert.setInt(3, pago.getTotalDias());
                            insert.setDouble(4, pago.getCosto());
                            insert.executeUpdate();

                            //se descuentan los creditos al cine
                            cinesDB.pagar(pago.getCodigoCine(), pago.getCosto(), connection);

                            //se confirma la compra del bloqueo
                            connection.commit();
                     } catch (SQLException ex) {
                            connection.rollback();
                            ex.printStackTrace();
                            throw new DataBaseException("No se pudo comprar el  bloqueo de anuncio en la db");
                     } finally {
                            connection.setAutoCommit(true);
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al comprar blqueo en la db");
              }
       }

       /**
        *
        * @param codigoCine
        * @return
        * @throws DataBaseException
        */
       public boolean tieneBloqueadorAnuncios(String codigoCine) throws DataBaseException {
              boolean hayBloqueador = false;
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     connection.setAutoCommit(false);
                     try (PreparedStatement query = connection.prepareStatement(PeticionesAdminCine.OBTENER_PAGO_BLOQUEO.get())) {
                            query.setString(1, codigoCine);
                            ResultSet resultSet = query.executeQuery();

                            if (resultSet.next()) {

                                   hayBloqueador = true;

                                   int totalDias = resultSet.getInt("total_dias");
                                   int diasActivo = resultSet.getInt("dias_activo");
                                   int id = resultSet.getInt("id");

                                   //se agrega 1 dia activo
                                   agregarUnDiaActivo(id, connection);

                                   //se verifica si ya se llego al limite para cambiar el estado activo
                                   boolean nuevoEstado = true;
                                   if (diasActivo + 1 >= totalDias) {
                                          nuevoEstado = false;
                                   }

                                   //se cambia el estado activo si es necesario
                                   cambiarEstadoActivo(id, nuevoEstado, connection);

                                   //se confirma la transaccion
                                   connection.commit();
                            }
                     } catch (SQLException ex) {
                            connection.rollback();
                            ex.printStackTrace();
                            throw new DataBaseException("No se pudo obtener pago por  bloqueo de anuncio en la db");
                     } finally {
                            connection.setAutoCommit(true);
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener pago por bloqueo de anuncios en la db");
              }
              return hayBloqueador;
       }

       /**
        *
        * @param id
        * @param connection
        * @throws DataBaseException 
        */
       private void agregarUnDiaActivo(int id, Connection connection) throws DataBaseException {
              try (PreparedStatement update = connection.prepareStatement(PeticionesAdminCine.AGREGAR_DIA_ACTIVO_BLOQUEADOR.get())) {
                     
                     update.setInt(1, id);
                     update.executeUpdate();
                     
              } catch (SQLException e) {
                     throw new DataBaseException("Error al eliminar admin cine en la db");
              }
       }
       
       /**
        * 
        * @param id
        * @param nuevoEstado
        * @param connection
        * @throws DataBaseException 
        */
       private void cambiarEstadoActivo(int id, boolean nuevoEstado, Connection connection) throws DataBaseException {
              try (PreparedStatement update = connection.prepareStatement(
                      PeticionesAdminCine.CAMBIAR_ESTADO_ACTIVO_BLOQUEADOR.get())) {
                     update.setBoolean(1, nuevoEstado);
                     update.setInt(2, id);
                     update.executeUpdate();
                     
              } catch (SQLException e) {
                     throw new DataBaseException("Error al eliminar admin cine en la db");
              }
       }
}
