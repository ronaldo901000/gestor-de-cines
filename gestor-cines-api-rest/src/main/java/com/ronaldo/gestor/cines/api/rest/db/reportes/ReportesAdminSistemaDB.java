package com.ronaldo.gestor.cines.api.rest.db.reportes;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroSalasMasPopulares;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.reporteBoletosVendidos.CompraBoleto;
import com.ronaldo.gestor.cines.api.rest.models.reporteSalasGustadas.Calificacion;
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
public class ReportesAdminSistemaDB {

       /**
        * 
        * @param codigoSala
        * @param filtro
        * @return
        * @throws DataBaseException 
        */
       public List<Calificacion> obtenerCalificacionesSala(String codigoSala, FiltroSalasMasPopulares filtro) throws DataBaseException {
              List<Calificacion> calificaciones = new ArrayList<>();
              try (Connection connnection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connnection.
                             prepareStatement(filtro.getQuery())) {
                            query.setString(1, codigoSala);

                            if (filtro.getTipo() == FiltroSalasMasPopulares.CON_FILTRO) {
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
                     throw new DataBaseException("Error al obtener calificaciones por sala en db");
              }
              return calificaciones;
       }

       public List<CompraBoleto> obtenerBoletosComprados(String codigoSala) throws DataBaseException {
              List<CompraBoleto> boletosComprados = new ArrayList<>();
              try (Connection connnection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connnection.
                             prepareStatement(PeticionAdminSistema.OBTENER_TODOS_LOS_BOLETOS_VENDIDOS_POR_SALA.get())) {
                            query.setString(1, codigoSala);

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
