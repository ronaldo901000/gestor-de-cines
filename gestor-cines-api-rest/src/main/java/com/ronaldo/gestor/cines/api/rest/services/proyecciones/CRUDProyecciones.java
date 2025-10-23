package com.ronaldo.gestor.cines.api.rest.services.proyecciones;

import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.db.proyeccion.ProyeccionDB;
import com.ronaldo.gestor.cines.api.rest.dtos.proyecciones.ProyeccionRequest;
import com.ronaldo.gestor.cines.api.rest.dtos.proyecciones.ProyeccionResponse;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionesAdminCine;
import com.ronaldo.gestor.cines.api.rest.enums.query.RangoBusquedaElemento;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadResponse;
import com.ronaldo.gestor.cines.api.rest.models.interfaces.Editable;
import com.ronaldo.gestor.cines.api.rest.models.proyecciones.Proyeccion;
import com.ronaldo.gestor.cines.api.rest.services.CRUD;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ronaldo
 */
public class CRUDProyecciones extends CRUD {

       @Override
       public Editable crear(EntidadRequest entidadRequest) throws UserDataInvalidException,
               EntityAlreadyExistsException, DataBaseException, EntityNotFoundException {
              ProyeccionDB proyeccionDB = new ProyeccionDB();
              HerramientaDB herramientaDB= new HerramientaDB();
              Proyeccion proyeccion = (Proyeccion) extraer(entidadRequest);
              //se hacen las verificaciones en la db
              if (herramientaDB.existeEntidad(proyeccion.getCodigo(), PeticionesAdminCine.OBTENER_PROYECCION.get())) {
                     throw new EntityAlreadyExistsException(
                             "El codigo " + proyeccion.getCodigo() + " no esta disponible, usa otro"
                     );
              }
              //se hacen demas verificaciones
              hacerVerificaciones(proyeccion);

              //se vetifica disponibilidad de la sala
              if (proyeccionDB.existeProyeccionEnSalon(proyeccion)) {
                     throw new UserDataInvalidException("La sala ya tiene una proyeccion a esa hora");
              }
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

       private void hacerVerificaciones(Proyeccion proyeccion) throws DataBaseException, UserDataInvalidException, EntityNotFoundException {
              HerramientaDB herramientaDB = new HerramientaDB();
              
              if (!herramientaDB.existeEntidad(proyeccion.getCodigoSala(), PeticionesAdminCine.BUSCAR_SALA.get())) {
                     throw new EntityNotFoundException(
                             "La sala inexistente, verifica"
                     );
              }
              if (!herramientaDB.existeEntidad(proyeccion.getCodigoPelicula(), PeticionesAdminCine.BUSCAR_PELICULA.get())) {
                     throw new EntityNotFoundException(
                             "La pelicula ingresada no existe, verifica"
                     );
              }

              if (proyeccion.getHoraFin().isBefore(proyeccion.getHoraInicio())) {
                     throw new UserDataInvalidException("La hora de inicio no puede ser posterior a la hora final");
              }


       }

       /**
        * 
        * @param codigoCine
        * @param inicio
        * @return
        * @throws DataBaseException
        * @throws EntityNotFoundException 
        */
       public List<ProyeccionResponse> obtenerProyeccionesPorRango(String codigoCine, int inicio) throws DataBaseException, EntityNotFoundException {
              ProyeccionDB proyeccionDB = new ProyeccionDB();
              HerramientaDB herramientaDB = new HerramientaDB();
              CreadorProyeccionesResponse creador = new CreadorProyeccionesResponse();
              if (!herramientaDB.existeEntidad(codigoCine, PeticionAdminSistema.OBTENER_CINE_POR_CODIGO.get())) {
                     throw new EntityNotFoundException("El cine ingresado no existe");
              }
              int fin = inicio + RangoBusquedaElemento.PROYECCIONES.getRango();
              List<Proyeccion> proyecciones = proyeccionDB.obtenerProyeccionesPorRango(codigoCine, inicio, fin);
              return creador.crearListaResponse(proyecciones);
       }

       public ProyeccionResponse obtenerProyeccion(String codigoProyeccion) throws EntityNotFoundException, DataBaseException {
              ProyeccionDB proyeccionDB = new ProyeccionDB();
              CreadorProyeccionesResponse creador = new CreadorProyeccionesResponse();

              Optional<Proyeccion> proyeccion = proyeccionDB.obtenerProyeccion(codigoProyeccion);

              if (proyeccion.isEmpty()) {
                     throw new EntityNotFoundException("No se encontro ninguna proyeccion con codigo: " + codigoProyeccion);
              }

              return creador.crearResponse(proyeccion.get());
       }

       @Override
       public EntidadResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              ProyeccionDB proyeccionDB = new ProyeccionDB();
              Proyeccion proyeccion = (Proyeccion) extraer(entidadRequest);
              CreadorProyeccionesResponse creador = new CreadorProyeccionesResponse();
              HerramientaDB herramientaDB = new HerramientaDB();
              //se hacen las verificaciones en la db
              if (!herramientaDB.existeEntidad(proyeccion.getCodigo(), PeticionesAdminCine.OBTENER_PROYECCION.get())) {
                     throw new EntityNotFoundException(
                             "Proyeccion inexistente, verifica"
                     );
              }
              //se hacen demas verificaciones
              hacerVerificaciones(proyeccion);

              //se verifica disponibilidad de la sala, excluyendo la que tiene esta sala actualmente
              if (proyeccionDB.existeProyeccionEnSalonActualizacion(proyeccion)) {
                     throw new UserDataInvalidException("La sala ya tiene una proyeccion a esa hora");
              }
              proyeccionDB.actualizar(proyeccion);
              return creador.crearResponse(proyeccion);
       }
       
       @Override
       public void eliminar(String codigo) throws DataBaseException, EntityNotFoundException, UserDataInvalidException {
              ProyeccionDB proyeccionDB = new ProyeccionDB();
              HerramientaDB herramientaDB = new HerramientaDB();
              if (!herramientaDB.existeEntidad(codigo, PeticionesAdminCine.OBTENER_PROYECCION_POR_CODIGO.get())) {
                     throw new EntityNotFoundException("No existe la proyeccion indidcada");
              }
              proyeccionDB.eliminar(codigo);
       }
       
}
