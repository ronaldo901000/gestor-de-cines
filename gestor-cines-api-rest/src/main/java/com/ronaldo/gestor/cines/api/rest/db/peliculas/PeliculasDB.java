package com.ronaldo.gestor.cines.api.rest.db.peliculas;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.peliculas.Pelicula;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class PeliculasDB {

       /**
        *
        * @param pelicula
        * @throws DataBaseException
        */
       public void crear(Pelicula pelicula) throws DataBaseException {
              
              try {
                     Connection connection = DataSourceDBSingleton.getInstance().getConnection();
                     connection.setAutoCommit(false);
                     try (PreparedStatement insert = connection.prepareStatement(PeticionAdminSistema.CREAR_PELICULA.get())) {

                            insert.setString(1, pelicula.getCodigo());
                            insert.setString(2, pelicula.getTitulo());
                            insert.setString(3, pelicula.getSinopsis());
                            insert.setInt(4, pelicula.getDuracion());
                            insert.setString(5, pelicula.getDirector());
                            insert.setString(6, pelicula.getCast());
                            insert.setString(7, pelicula.getClasificacion());
                            insert.setDate(8, Date.valueOf(pelicula.getFechaEstreno()));
                            insert.executeUpdate();
                            //se registran sus categorias y posters
                            insertarCategorias(connection, pelicula.getCodigo(), pelicula.getIdsCategorias());
                            //si no suceden excepciones se confirma la creacion
                            connection.commit();
                            connection.setAutoCommit(true);

                     } catch (SQLException e) {
                            e.printStackTrace();
                            try {
                                   connection.rollback();
                                   connection.setAutoCommit(true);
                            } catch (SQLException e2) {
                                   System.out.println("Error en rollback");
                            }
                            throw new DataBaseException("Error al crear pelicula en la db");
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al crear pelicula en la db");
              }

       }

       private void insertarCategorias(Connection connection, String codigoPelicula, List<String> idsCategorias)
               throws DataBaseException {
              for (String idCategoria : idsCategorias) {
                     try (PreparedStatement insert = connection.prepareStatement(
                             PeticionAdminSistema.CREAR_REGISTRO_CATEGORIAS_PELICULA.get())) {
                            insert.setString(1, idCategoria);
                            insert.setString(2, codigoPelicula);
                            insert.executeUpdate();
                     } catch (SQLException e) {
                            e.printStackTrace();
                            throw new DataBaseException("Error al insertar categoría en la DB");
                     }
              }
       }
}
