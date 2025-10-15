package com.ronaldo.gestor.cines.api.rest.services.cines;

import com.ronaldo.gestor.cines.api.rest.db.cines.CinesDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.cines.NuevoCineRequest;
import com.ronaldo.gestor.cines.api.rest.enums.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;

/**
 *
 * @author ronaldo
 */
public class CreadorCines {

       /**
        *
        * @param nuevoCineRequest
        * @return
        * @throws UserDataInvalidException
        * @throws EntityAlreadyExistsException
        * @throws DataBaseException
        */
       public Cine crearCine(NuevoCineRequest nuevoCineRequest) throws UserDataInvalidException,
               EntityAlreadyExistsException,
               DataBaseException {
              CinesDB cinesDB = new CinesDB();
              HerramientaDB herramienta = new HerramientaDB();
              Cine cine = extraerCine(nuevoCineRequest);

              if (herramienta.existeEntidad(nuevoCineRequest.getCodigo(), PeticionAdminSistema.BUSCAR_CINE.getPeticion())) {
                     throw new EntityAlreadyExistsException(
                             String.format("El Cine con codigo %s ya existe", cine.getCodigo()));
              }
              cinesDB.crearCine(cine);
              System.out.println("si se creo");
              return cine;
       }

       /**
        *
        * @param newEventRequest
        * @return
        * @throws UserDataInvalidException
        */
       private Cine extraerCine(NuevoCineRequest newEventRequest) throws UserDataInvalidException {
              try {
                     Cine cine = new Cine(
                             newEventRequest.getCodigo(),
                             newEventRequest.getNombre(),
                             newEventRequest.getUbicacion(),
                             newEventRequest.getFechaCreacion()
                     );

                     if (!cine.esValidoEnCampos()) {
                            throw new UserDataInvalidException("Error en los datos enviados");
                     }
                     return cine;
              } catch (IllegalArgumentException | NullPointerException e) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
       }
}
