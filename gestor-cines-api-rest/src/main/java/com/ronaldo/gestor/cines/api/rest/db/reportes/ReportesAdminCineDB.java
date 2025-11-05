package com.ronaldo.gestor.cines.api.rest.db.reportes;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.filtrosReportes.FiltroComentariosSalas;
import com.ronaldo.gestor.cines.api.rest.models.reporteComentarios.Comentario;
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
public class ReportesAdminCineDB {
       
       /**
        * 
        * @param filtro
        * @return
        * @throws DataBaseException 
        */
       public List<Comentario> obtenerComentarios(FiltroComentariosSalas filtro) throws DataBaseException {
              List<Comentario> comentarios= new ArrayList<>();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.prepareStatement(filtro.getQuery())) {
                            query.setString(1, filtro.getCodigoCine());
                            if (filtro.getTipoFiltro() == FiltroComentariosSalas.FILTRO_COMPLETO) {
                                   query.setString(2, filtro.getCodigoSala());
                                   query.setDate(3, Date.valueOf(filtro.getFechaInicio()));
                                   query.setDate(4, Date.valueOf(filtro.getFechaFin()));
                            } else if (filtro.getTipoFiltro() == FiltroComentariosSalas.FILTRO_FECHAS) {
                                   query.setDate(2, Date.valueOf(filtro.getFechaInicio()));
                                   query.setDate(3, Date.valueOf(filtro.getFechaFin()));
                            } else if (filtro.getTipoFiltro() == FiltroComentariosSalas.FILTRO_SALA) {
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
}
