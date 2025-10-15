package com.ronaldo.gestor.cines.api.rest.services.cines;

import com.ronaldo.gestor.cines.api.rest.db.cines.CinesDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.cines.CineResponse;
import com.ronaldo.gestor.cines.api.rest.dtos.cines.NuevoCineRequest;
import com.ronaldo.gestor.cines.api.rest.enums.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.RangoElemento;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ronaldo
 */
public class CRUDCines {

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

       
       /**
        *
        * @param inicio
        * @return
        * @throws DataBaseException
        */
       public List<CineResponse> obtenerCines(int inicio) throws DataBaseException, UserDataInvalidException {
              List<CineResponse> cinesDTO = new ArrayList<>();
              try {
                     int inicio1=inicio;
              } catch (NumberFormatException e) {
                     throw new UserDataInvalidException();
              }
              CinesDB cinesDB = new CinesDB();
              List<Cine> cines = cinesDB.obtenerCinesPaginacion(inicio, inicio+RangoElemento.CINES.getRango());
              cinesDTO = cargarDTOs(cines, cinesDTO);
              return cinesDTO;
       }

       private List<CineResponse> cargarDTOs(List<Cine> cines, List<CineResponse> cinesDTO) {
              for (int i = 0; i < cines.size(); i++) {
                     CineResponse dto = new CineResponse();
                     dto.setCodigo(cines.get(i).getCodigo());
                     dto.setNombre(cines.get(i).getNombre());
                     dto.setUbicacion(cines.get(i).getUbicacion());
                     dto.setFechaCreacion(cines.get(i).getFechaCreacion());
                     cinesDTO.add(dto);
              }
              return cinesDTO;
       }

       public List<CineResponse> obtenerCinesPorNombreOCodigo(String palabra) throws DataBaseException, UserDataInvalidException {
              List<CineResponse> cinesDTO = new ArrayList<>();
              CinesDB cinesDB = new CinesDB();
              
              List<Cine> cines = cinesDB.obtenerCinePorCodigoONombre(palabra);
              cinesDTO = cargarDTOs(cines, cinesDTO);
              return cinesDTO;
       }
}
