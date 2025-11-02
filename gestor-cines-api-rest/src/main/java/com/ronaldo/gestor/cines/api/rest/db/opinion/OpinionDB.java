package com.ronaldo.gestor.cines.api.rest.db.opinion;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
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
                            System.out.println("codigo: " + codigoEntidad);
                            System.out.println("consulta: " + consulta);
                            System.out.println("es pelicula: " + esPelicula);
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
                            System.out.println("total opiniones en db: "+ opiniones.size());
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener opiniones a la db");
              }
              return opiniones;
       }

}
