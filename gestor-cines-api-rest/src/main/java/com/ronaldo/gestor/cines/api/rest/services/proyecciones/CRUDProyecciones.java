package com.ronaldo.gestor.cines.api.rest.services.proyecciones;

import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.db.proyeccion.ProyeccionDB;
import com.ronaldo.gestor.cines.api.rest.dtos.proyecciones.ProyeccionRequest;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAdminCine;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.models.proyecciones.Proyeccion;
import com.ronaldo.gestor.cines.api.rest.services.CRUD;

/**
 *
 * @author ronaldo
 */
public class CRUDProyecciones extends CRUD {

       @Override
       public Editable crear(EntidadRequest entidadRequest) throws UserDataInvalidException,
               EntityAlreadyExistsException, DataBaseException, EntityNotFoundException {
              ProyeccionDB proyeccionDB = new ProyeccionDB();
              Proyeccion proyeccion = (Proyeccion) extraer(entidadRequest);
              //se hacen las verificaciones en la db
              hacerVerificaciones(proyeccion);

              //se guarda
              proyeccionDB.crear(proyeccion);
              return proyeccion;
       }

       @Override
       protected Editable extraer(EntidadRequest entidadRequest) throws UserDataInvalidException {
              ProyeccionRequest request = (ProyeccionRequest) entidadRequest;
              Proyeccion proyeccion = new Proyeccion();
              proyeccion.setCodigo(request.getCodigo());
              proyeccion.setCodigoPelicula(request.getCodigoPelicula());
              proyeccion.setCodigoSala(request.getCodigoSala());
              proyeccion.setFecha(request.getFecha());
              proyeccion.setHoraInicio(request.getHoraInicio());
              proyeccion.setHoraFin(request.getHoraFin());
              proyeccion.setPrecio(request.getPrecio());

              if (!proyeccion.datosValidos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              return proyeccion;
       }

       private void hacerVerificaciones(Proyeccion proyeccion) throws DataBaseException, EntityAlreadyExistsException, UserDataInvalidException {
              HerramientaDB herramientaDB = new HerramientaDB();
              ProyeccionDB proyeccionDB = new ProyeccionDB();
              
              if (herramientaDB.existeEntidad(proyeccion.getCodigo(), PeticionesAdminCine.OBTENER_PROYECCION.get())) {
                     throw new EntityAlreadyExistsException(
                             "El codigo " + proyeccion.getCodigo() + " no esta disponible, usa otro"
                     );
              }
              if (!herramientaDB.existeEntidad(proyeccion.getCodigoSala(), PeticionesAdminCine.BUSCAR_SALA.get())) {
                     throw new EntityAlreadyExistsException(
                             "La sala inexistente, verifica"
                     );
              }
              if (!herramientaDB.existeEntidad(proyeccion.getCodigoPelicula(), PeticionesAdminCine.BUSCAR_PELICULA.get())) {
                     throw new EntityAlreadyExistsException(
                             "La pelicula ingresada no existe, verifica"
                     );
              }

              if (proyeccion.getHoraFin().isBefore(proyeccion.getHoraInicio())) {
                     throw new UserDataInvalidException("La hora de inicio no puede ser posterior a la hora final");
              }

              if (proyeccionDB.existeProyeccionEnSalon(proyeccion)) {
                     throw new UserDataInvalidException("La sala ya tiene una proyeccion a esa hora");
              }

       }

       @Override
       protected EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

       @Override
       protected void eliminar(String codigo) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       }

}
