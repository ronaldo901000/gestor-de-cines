package com.ronaldo.gestor.cines.api.rest.db.peliculas;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.db.categoriaDB.CategoriaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.peliculas.PeliculaUpdateResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.peliculas.Pelicula;
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
public class PeliculasDB {

       /**
        *
        * @param pelicula
        * @throws DataBaseException
        */
       public void crear(Pelicula pelicula) throws DataBaseException {
              
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()){
                     
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
                            try {
                                   connection.rollback();
                                   connection.setAutoCommit(true);
                            } catch (SQLException e2) {
                                   System.out.println("Error en rollback");
                            }
                            throw new DataBaseException("Error al crear pelicula en la db");
                     }
              } catch (SQLException e) {
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
       
       public List<Pelicula> obtenerPeliculasPorRango(int inicio, int fin) throws DataBaseException {
              List<Pelicula> peliculas = new ArrayList<>();
              int contador = 0;
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()){
                    
                     try (PreparedStatement query = connection.prepareStatement(PeticionAdminSistema.OBTENER_PELICULAS.get());) {
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   if (contador >= inicio && contador < fin) {
                                          peliculas.add(new Pelicula(
                                                  resultSet.getString("codigo"),
                                                  resultSet.getString("titulo"),
                                                  resultSet.getString("sinopsis"),
                                                  resultSet.getInt("duracion"),
                                                  resultSet.getString("director"),
                                                  resultSet.getString("cast"),
                                                  resultSet.getString("clasificacion"),
                                                  LocalDate.parse(resultSet.getString("fecha_estreno")))
                                          );
                                   }
                                   contador++;
                            }
                     }
                     
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener cines en la db");
              }
              return peliculas;
       }


       /**
        * 
        * @param codigoPelicula
        * @param dato
        * @return
        * @throws DataBaseException 
        */
       public String obtenerCategoriasPelicula(String codigoPelicula,String dato) throws DataBaseException {
              String categorias = "";
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()){
                     
                     try (PreparedStatement query = connection.prepareStatement(
                             PeticionAdminSistema.OBTENER_CATEGORIAS_PELICULA.get())) {
                            query.setString(1, codigoPelicula);
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   categorias += resultSet.getString(dato)+",";
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al insertar categoría en la DB");
              }
              return categorias;
       }

       public void actualizar(Pelicula pelicula) throws DataBaseException {
              CategoriaDB categoriaDB= new CategoriaDB();
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {

                     connection.setAutoCommit(false);
                     try (PreparedStatement update = connection.prepareStatement(PeticionAdminSistema.ACTUALIZAR_PELICULA.get())) {

                            update.setString(1, pelicula.getTitulo());
                            update.setString(2, pelicula.getSinopsis());
                            update.setInt(3, pelicula.getDuracion());
                            update.setString(4, pelicula.getDirector());
                            update.setString(5, pelicula.getCast());
                            update.setString(6, pelicula.getClasificacion());
                            update.setDate(7, Date.valueOf(pelicula.getFechaEstreno()));
                            update.setString(8, pelicula.getCodigo());
                            update.executeUpdate();
                            //se eliminan los registros anteriores de categorias de la pelicula
                            categoriaDB.eliminarRegistroCategoriaPelicula(pelicula.getCodigo());
                            //se registran sus categorias y posters
                            insertarCategorias(connection, pelicula.getCodigo(), pelicula.getIdsCategorias());
                            //si no suceden excepciones se confirma la creacion
                            connection.commit();
                            connection.setAutoCommit(true);

                     } catch (SQLException e) {
                            try {
                                   connection.rollback();
                                   connection.setAutoCommit(true);
                            } catch (SQLException e2) {
                                   System.out.println("Error en rollback");
                            }
                            throw new DataBaseException("Error al actualizar pelicula en la db");
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al actualizar pelicula en la db");
              }

       }
       
       /**
        * 
        * @param codigo
        * @return
        * @throws DataBaseException 
        */
       public Optional<PeliculaUpdateResponse> obtenerPelicula(String codigo) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.prepareStatement(PeticionAdminSistema.OBTENER_PELICULA.get());) {
                            query.setString(1, codigo);
                            ResultSet resultSet = query.executeQuery();
                            if (resultSet.next()) {
                                   return Optional.of(new PeliculaUpdateResponse(
                                           resultSet.getString("codigo"),
                                           resultSet.getString("titulo"),
                                           resultSet.getString("sinopsis"),
                                           resultSet.getInt("duracion"),
                                           resultSet.getString("director"),
                                           resultSet.getString("cast"),
                                           resultSet.getString("clasificacion"),
                                           LocalDate.parse(resultSet.getString("fecha_estreno"))));
                            }
                     }
              } catch (SQLException e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al obtener cines en la db");
              }
              return Optional.empty();
       }

       /**
        * 
        * @param codigo
        * @throws DataBaseException 
        */
       public void eliminar(String codigo) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement delete = connection.prepareStatement(PeticionAdminSistema.ELIMINAR_PELICULA.get())) {
                            delete.setString(1, codigo);
                            delete.executeUpdate();
                     }
              } catch (Exception e) {
                     e.printStackTrace();
                     throw new DataBaseException("Error al eliminar pelicula en la db");
              }
       }
}
