package com.ronaldo.gestor.cines.api.rest.db.proyeccion;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAdminCine;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.proyecciones.Proyeccion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ronaldo
 */
public class ProyeccionDB {

       public void crear(Proyeccion proyeccion) throws DataBaseException {
              try (Connection connnection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connnection.
                             prepareStatement(PeticionesAdminCine.CREAR_PROYECCION.get())) {
                            query.setString(1, proyeccion.getCodigo());
                            query.setString(2, proyeccion.getCodigoPelicula());
                            query.setString(3, proyeccion.getCodigoSala());
                            query.setDate(4, Date.valueOf(proyeccion.getFecha()));
                            query.setTime(5, Time.valueOf(proyeccion.getHoraInicio()));
                            query.setTime(6, Time.valueOf(proyeccion.getHoraFin()));
                            query.setDouble(7, proyeccion.getPrecio());
                            query.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear proyeccion en la db");
              }
       }

       public void actualizar(Proyeccion proyeccion) throws DataBaseException {
              try (Connection connnection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connnection.
                             prepareStatement(PeticionesAdminCine.ACTUALIZAR_PROYECCION.get())) {
                            query.setString(1, proyeccion.getCodigoPelicula());
                            query.setString(2, proyeccion.getCodigoSala());
                            query.setDate(3, Date.valueOf(proyeccion.getFecha()));
                            query.setTime(4, Time.valueOf(proyeccion.getHoraInicio()));
                            query.setTime(5, Time.valueOf(proyeccion.getHoraFin()));
                            query.setDouble(6, proyeccion.getPrecio());
                            query.setString(7, proyeccion.getCodigo());
                            query.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear proyeccion en la db");
              }
       }

       /**
        *
        * @param proyeccion
        * @return
        * @throws DataBaseException 
        */
       public boolean existeProyeccionEnSalon(Proyeccion proyeccion) throws DataBaseException {
              try (Connection connnection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connnection.
                             prepareStatement(PeticionesAdminCine.VERIFICAR_DISPONIBILIDAD_SALA.get())) {
                            query.setString(1, proyeccion.getCodigoSala());
                            query.setDate(2, Date.valueOf(proyeccion.getFecha()));
                            query.setTime(3, Time.valueOf(proyeccion.getHoraFin()));
                            query.setTime(4, Time.valueOf(proyeccion.getHoraInicio()));
                            ResultSet resultSet = query.executeQuery();
                            return resultSet.next();
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al buscar disponibilidad de sala");
              }
       }
       
       /**
        * 
        * @param codigoCine
        * @param inicio
        * @param fin
        * @return
        * @throws DataBaseException 
        */
       public List<Proyeccion> obtenerProyeccionesPorRango(String codigoCine, String peticion,int inicio, int fin) throws DataBaseException {
              List<Proyeccion> proyecciones = new ArrayList<>();
              int contador = 0;
              try (Connection connnection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connnection.
                             prepareStatement(peticion)) {
                            query.setString(1, codigoCine);
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   if (contador >= inicio && contador < fin) {
                                          Proyeccion proyeccion = new Proyeccion();
                                          crearProyeccion(resultSet, proyeccion);
                                          proyecciones.add(proyeccion);
                                   }
                                   contador++;
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener Proyecciones en db");
              }
              return proyecciones;
       }

       /**
        *
        * @param codigo
        * @return
        * @throws DataBaseException
        */
       public Optional<Proyeccion> obtenerProyeccion(String codigo) throws DataBaseException {
              try (Connection connnection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connnection.
                             prepareStatement(PeticionesAdminCine.OBTENER_PROYECCION_POR_CODIGO.get())) {
                            query.setString(1, codigo);
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   Proyeccion proyeccion = new Proyeccion();
                                   crearProyeccion(resultSet, proyeccion);
                                   return Optional.of(proyeccion);
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener proyeccion el la db");
              }
              return Optional.empty();
       }

       public void eliminar(String codigo) throws DataBaseException {
              try (Connection connnection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connnection.
                             prepareStatement(PeticionesAdminCine.ELIMINAR_PROYECCION.get())) {
                            query.setString(1, codigo);
                            query.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al eliminar proyeccion en la db");
              }
       }

       /**
        *
        * @param proyeccion
        * @return
        * @throws DataBaseException 
        */
       public boolean existeProyeccionEnSalonActualizacion(Proyeccion proyeccion) throws DataBaseException {
              try (Connection connnection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connnection.
                             prepareStatement(PeticionesAdminCine.VERIFICAR_DISPONIBILIDAD_SALA_EN_ACTUALIZACION.get())) {
                            query.setString(1, proyeccion.getCodigoSala());
                            query.setDate(2, Date.valueOf(proyeccion.getFecha()));
                            query.setTime(3, Time.valueOf(proyeccion.getHoraFin()));
                            query.setTime(4, Time.valueOf(proyeccion.getHoraInicio()));
                            query.setString(5, proyeccion.getCodigo());
                            ResultSet resultSet = query.executeQuery();
                            return resultSet.next();
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al buscar disponibilidad de sala");
              }
       }

       public void cambiarEstadoDisponibilidad(String codigo, boolean nuevoEstado, Connection connnection) throws DataBaseException {
              try (PreparedStatement update = connnection.
                      prepareStatement(PeticionesAdminCine.ACTUALIZAR_ESTADO_PROYECCION.get())) {
                     update.setBoolean(1, nuevoEstado);
                     update.setString(2, codigo);
                     update.executeUpdate();

              } catch (SQLException e) {

                     throw new DataBaseException("Error al actualizar estado de proyeccion en la db");
              }
       }

       /**
        *
        * @param resultSet
        * @param proyeccion
        * @throws SQLException
        */
       private void crearProyeccion(ResultSet resultSet, Proyeccion proyeccion) throws SQLException {
              proyeccion.setCodigo(resultSet.getString("p.codigo"));
              proyeccion.setCodigoPelicula(resultSet.getString("p.codigo_pelicula"));
              proyeccion.setCodigoSala(resultSet.getString("p.codigo_sala"));
              proyeccion.setFecha(LocalDate.parse(resultSet.getString("p.fecha")));
              proyeccion.setHoraInicio(LocalTime.parse(resultSet.getString("p.hora_inicio")));
              proyeccion.setHoraFin(LocalTime.parse(resultSet.getString("p.hora_fin")));
              proyeccion.setPrecio(resultSet.getDouble("p.precio"));
              proyeccion.setDisponible(resultSet.getBoolean("p.disponible"));
       }
}
