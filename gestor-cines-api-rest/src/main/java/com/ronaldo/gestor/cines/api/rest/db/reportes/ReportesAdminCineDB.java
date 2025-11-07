package com.ronaldo.gestor.cines.api.rest.db.reportes;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.filtrosReportes.FiltroReportesAdminCine;
import com.ronaldo.gestor.cines.api.rest.models.proyecciones.Proyeccion;
import com.ronaldo.gestor.cines.api.rest.models.reporteBoletosVendidos.CompraBoleto;
import com.ronaldo.gestor.cines.api.rest.models.reporteComentarios.Comentario;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas.Calificacion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class ReportesAdminCineDB {
       
       /**
        * 
        * @param filtro
        * @return
        * @throws DataBaseException 
        */
       public List<Comentario> obtenerComentarios(FiltroReportesAdminCine filtro) throws DataBaseException {
              List<Comentario> comentarios= new ArrayList<>();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.prepareStatement(filtro.getQuery())) {
                            query.setString(1, filtro.getCodigoCine());
                            if (filtro.getTipoFiltro() == FiltroReportesAdminCine.FILTRO_COMPLETO) {
                                   query.setString(2, filtro.getCodigoSala());
                                   query.setDate(3, Date.valueOf(filtro.getFechaInicio()));
                                   query.setDate(4, Date.valueOf(filtro.getFechaFin()));
                            } else if (filtro.getTipoFiltro() == FiltroReportesAdminCine.FILTRO_FECHAS) {
                                   query.setDate(2, Date.valueOf(filtro.getFechaInicio()));
                                   query.setDate(3, Date.valueOf(filtro.getFechaFin()));
                            } else if (filtro.getTipoFiltro() == FiltroReportesAdminCine.FILTRO_SALA) {
                                   query.setString(2, filtro.getCodigoSala());
                            }
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   Comentario comentario = new Comentario();
                                   comentario.setNombreSala(resultSet.getString("s.nombre"));
                                   comentario.setNombreUsuario(resultSet.getString("u.nombre"));
                                   comentario.setComentario(resultSet.getString("o.comentario"));
                                   comentario.setCalificacion(resultSet.getInt("o.calificacion"));
                                   comentario.setFecha(LocalDate.parse(resultSet.getString("o.fecha")));
                                   comentarios.add(comentario);
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener comentarios a salas para el reporte");
              }
              return comentarios;
       }
       
       /**
        *
        * @param codigoSala
        * @param filtro
        * @return
        * @throws DataBaseException
        */
       public List<Proyeccion> obtenerProyeccionesPorSala(String codigoSala, FiltroReportesAdminCine filtro) throws DataBaseException {
              List<Proyeccion> proyecciones = new ArrayList<>();
              try (Connection connnection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connnection.
                             prepareStatement(filtro.getQuery())) {
                            query.setString(1, codigoSala);

                            if (filtro.getTipoFiltro() == FiltroReportesAdminCine.FILTRO_COMPLETO || 
                                    filtro.getTipoFiltro()==FiltroReportesAdminCine.FILTRO_FECHAS) {
                                   
                                   query.setDate(2, Date.valueOf(filtro.getFechaInicio()));
                                   query.setDate(3, Date.valueOf(filtro.getFechaFin()));
                            }
                            
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   Proyeccion proyeccion = new Proyeccion();
                                   crearProyeccion(resultSet, proyeccion);
                                   proyecciones.add(proyeccion);
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener Proyecciones por sala en db");
              }
              return proyecciones;
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

       public List<Calificacion> obtenerCalificacionesSala(String codigoSala, FiltroReportesAdminCine filtro) throws DataBaseException {
              List<Calificacion> calificaciones = new ArrayList<>();
              try (Connection connnection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connnection.
                             prepareStatement(filtro.getQuery())) {
                            query.setString(1, codigoSala);

                            if (filtro.getTipoFiltro() == FiltroReportesAdminCine.FILTRO_COMPLETO
                                    || filtro.getTipoFiltro()==FiltroReportesAdminCine.FILTRO_FECHAS) {
                                   query.setDate(2, Date.valueOf(filtro.getFechaInicio()));
                                   query.setDate(3, Date.valueOf(filtro.getFechaFin()));
                            }

                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   Calificacion calificacion = new Calificacion();
                                   calificacion.setIdUsuario(resultSet.getString("id_usuario"));
                                   calificacion.setCalificacion(resultSet.getInt("calificacion"));
                                   calificacion.setFecha(LocalDate.parse(resultSet.getString("fecha")));
                                   calificaciones.add(calificacion);
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener Proyecciones por sala en db");
              }
              return calificaciones;
       }
       
       /**
        *
        * @param codigoSala
        * @param filtro
        * @return
        * @throws DataBaseException
        */
       public List<CompraBoleto> obtenerBoletosComprados(String codigoSala, FiltroReportesAdminCine filtro) throws DataBaseException {
              List<CompraBoleto> boletosComprados = new ArrayList<>();
              try (Connection connnection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connnection.
                             prepareStatement(filtro.getQuery())) {
                            query.setString(1, codigoSala);

                            if (filtro.getTipoFiltro() == FiltroReportesAdminCine.FILTRO_COMPLETO
                                    || filtro.getTipoFiltro() == FiltroReportesAdminCine.FILTRO_FECHAS) {
                                   query.setDate(2, Date.valueOf(filtro.getFechaInicio()));
                                   query.setDate(3, Date.valueOf(filtro.getFechaFin()));
                            }

                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   CompraBoleto compra = new CompraBoleto();
                                   compra.setIdUsario(resultSet.getString("id_usuario"));
                                   compra.setFecha(LocalDate.parse(resultSet.getString("fecha_compra")));
                                   compra.setTotalBoletos(resultSet.getInt("cantidad"));
                                   compra.setCostoTotal(resultSet.getDouble("costo_total"));
                                   boletosComprados.add(compra);
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener Proyecciones por sala en db");
              }
              return boletosComprados;
       }
}
