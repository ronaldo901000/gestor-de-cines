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
}
