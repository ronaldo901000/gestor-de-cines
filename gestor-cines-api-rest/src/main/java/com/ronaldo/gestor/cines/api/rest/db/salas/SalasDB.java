package com.ronaldo.gestor.cines.api.rest.db.salas;

import com.ronaldo.gestor.cines.api.rest.db.DataSourceDBSingleton;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAdminCine;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.models.salas.Sala;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class SalasDB {

       /**
        * 
        * @param sala
        * @throws DataBaseException 
        */
       public void crear(Sala sala) throws DataBaseException {
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement insert = connection.
                             prepareStatement(PeticionesAdminCine.CREAR_SALA.get())) {
                            insert.setString(1, sala.getCodigo());
                            insert.setString(2, sala.getCodigoCine());
                            insert.setString(3, sala.getNombre());
                            insert.setInt(4, sala.getFilas());
                            insert.setInt(5, sala.getColumnas());
                            insert.executeUpdate();

                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al crear sala en la base de datos");
              }
       }

       public List<Sala> obtenerSalas(String codigoCine, int inicio, int fin) throws DataBaseException {
              List<Sala> salas = new ArrayList<>();
              int contador = 0;
              try (Connection connection = DataSourceDBSingleton.getInstance().getConnection()) {
                     try (PreparedStatement query = connection.
                             prepareStatement(PeticionesAdminCine.OBTENER_SALAS.get())) {
                            query.setString(1, codigoCine);
                            ResultSet resultSet = query.executeQuery();
                            while (resultSet.next()) {
                                   if (contador >= inicio && contador < fin) {
                                          salas.add(construirSala(resultSet));
                                   }
                                   contador++;
                            }
                     }
              } catch (SQLException e) {
                     throw new DataBaseException("Error al obtener salas en la base de datos");
              }
              return salas;
       }

       /**
        *
        * @param resultSet
        * @return
        * @throws SQLException
        */
       private Sala construirSala(ResultSet resultSet) throws SQLException {
              Sala sala = new Sala();
              sala.setCodigo(resultSet.getString("s.codigo"));
              sala.setNombre(resultSet.getString("s.nombre"));
              sala.setCodigoCine(resultSet.getString("c.codigo"));
              sala.setNombreCine(resultSet.getString("c.nombre"));
              sala.setFilas(resultSet.getInt("s.filas"));
              sala.setColumnas(resultSet.getInt("s.columnas"));
              sala.setComentariosYCalificacionesHabilitados(resultSet.getBoolean("s.habilitado_com_y_cal"));
              sala.setActivo(resultSet.getBoolean("s.activo"));
              return sala;
       }

}
