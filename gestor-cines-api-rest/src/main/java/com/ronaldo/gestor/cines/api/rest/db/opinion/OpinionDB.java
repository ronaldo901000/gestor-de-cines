package com.ronaldo.gestor.cines.api.rest.db.opinion;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.dtos.filtrosReportesAdminSistema.FiltroSalasMasPopulares;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionUsuario;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.opinion.Opinion;
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
public class OpinionDB {


       /**
        * 
        * @param opinion
        * @param query
        * @throws DataBaseException 
        */
       public void crearOpinion(Opinion opinion, String query) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement insert = connection.prepareStatement(query)) {
                            insert.setString(1, opinion.getCodigoEntidad());
                            insert.setString(2, opinion.getIdUsuario());
                            insert.setString(3, opinion.getComentario());
                            insert.setInt(4, opinion.getCalificacion());
                            insert.setDate(5, Date.valueOf(opinion.getFecha()));
                            insert.executeUpdate();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al agregar opinion a la db");
              }
       }

       /**
        * 
        * @param opinion
        * @param consulta
        * @return
        * @throws DataBaseException 
        */
       public boolean existeOpinion(Opinion opinion, String consulta) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.prepareStatement(consulta)) {
                            query.setString(1, opinion.getCodigoEntidad());
                            query.setString(2, opinion.getIdUsuario());
                            ResultSet resultSet = query.executeQuery();
                            return resultSet.next();
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al buscar opinion a la db");
              }
       }

       /**
        * 
        * @param codigoEntidad
        * @param consulta
        * @param esPelicula
        * @return
        * @throws DataBaseException 
        */
       public List<Opinion> obtenerOpinion(String codigoEntidad, String consulta, boolean esPelicula) throws DataBaseException {
              List<Opinion> opiniones = new ArrayList<>();

              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.prepareStatement(consulta)) {
                            query.setString(1, codigoEntidad);
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   Opinion opinion = new Opinion();
                                   if (esPelicula) {
                                          opinion.setCodigoEntidad(resultSet.getString("codigo_pelicula"));
                                   } else {
                                          opinion.setCodigoEntidad(resultSet.getString("codigo_sala"));
                                   }
                                   opinion.setIdUsuario(resultSet.getString("id_usuario"));
                                   opinion.setComentario(resultSet.getString("comentario"));
                                   opinion.setCalificacion(resultSet.getInt("calificacion"));
                                   opinion.setFecha(LocalDate.parse(resultSet.getString("fecha")));
                                   opinion.setId(resultSet.getString("id"));
                                   opiniones.add(opinion);
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener opiniones a la db");
              }
              return opiniones;
       }

       /**
        *
        * @param codigoSala
        * @param filtro
        * @return
        * @throws DataBaseException
        */
       public List<Opinion> obtenerOpiniones(String codigoSala, FiltroSalasMasPopulares filtro) throws DataBaseException {
              List<Opinion> opiniones = new ArrayList<>();
              filtro.generarQuerySalasComentadas();
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
                                   Opinion opinion = new Opinion();
                                   opinion.setIdUsuario(resultSet.getString("id_usuario"));
                                   opinion.setComentario(resultSet.getString("comentario"));
                                   opinion.setFecha(LocalDate.parse(resultSet.getString("fecha")));
                                   opiniones.add(opinion);
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener opiniones por sala en db");
              }
              return opiniones;
       }

       /**
        * 
        * @return
        * @throws DataBaseException 
        */
       public List<String> obtenerCodigoSalaOpiniones() throws DataBaseException {
              List<String> codigosSalas = new ArrayList<>();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionUsuario.OBTENER_TODAS_LAS_OPINIONES_SALA.get())) {

                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   codigosSalas.add(resultSet.getString("codigo_sala"));
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener codigo de salas en tabla opiniones en la db");
              }
              return codigosSalas;
       }

}
