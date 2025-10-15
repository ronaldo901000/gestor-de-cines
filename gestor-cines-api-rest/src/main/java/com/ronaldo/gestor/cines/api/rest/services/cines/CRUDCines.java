package com.ronaldo.gestor.cines.api.rest.services.cines;

import com.ronaldo.gestor.cines.api.rest.db.cines.CinesDB;
import com.ronaldo.gestor.cines.api.rest.db.general.HerramientaDB;
import com.ronaldo.gestor.cines.api.rest.dtos.cines.CineResponse;
import com.ronaldo.gestor.cines.api.rest.dtos.cines.CineRequest;
import com.ronaldo.gestor.cines.api.rest.enums.query.PeticionAdminSistema;
import com.ronaldo.gestor.cines.api.rest.enums.query.RangoBusquedaElemento;
import com.ronaldo.gestor.cines.api.rest.exceptions.DataBaseException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityAlreadyExistsException;
import com.ronaldo.gestor.cines.api.rest.exceptions.EntityNotFoundException;
import com.ronaldo.gestor.cines.api.rest.exceptions.UserDataInvalidException;
import com.ronaldo.gestor.cines.api.rest.models.cines.Cine;
import com.ronaldo.gestor.cines.api.rest.models.herencia.EntidadRequest;
import com.ronaldo.gestor.cines.api.rest.services.CRUD;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ronaldo
 */
public class CRUDCines extends CRUD {

       /**
        *
        * @param entidadRequest
        * @return
        * @throws UserDataInvalidException
        * @throws EntityAlreadyExistsException
        * @throws DataBaseException
        */
       @Override
       public Cine crear(EntidadRequest entidadRequest) throws UserDataInvalidException,
               EntityAlreadyExistsException,
               DataBaseException {
              CineRequest cineRequest = (CineRequest) entidadRequest;
              CinesDB cinesDB = new CinesDB();
              HerramientaDB herramienta = new HerramientaDB();
              Cine cine = extraer(cineRequest);

              if (herramienta.existeEntidad(cineRequest.getCodigo(), PeticionAdminSistema.BUSCAR_CINE.getPeticion())) {
                     throw new EntityAlreadyExistsException(
                             String.format("El Cine con codigo %s ya existe", cine.getCodigo()));
              }
              cinesDB.crearCine(cine);
              System.out.println("si se creo");
              return cine;
       }

       /**
        *
        * @param entidadRequest
        * @return
        * @throws UserDataInvalidException
        */
       @Override
       protected Cine extraer(EntidadRequest entidadRequest) throws UserDataInvalidException {
              CineRequest cineRequest = (CineRequest) entidadRequest;
              try {
                     Cine cine = new Cine(
                             cineRequest.getCodigo(),
                             cineRequest.getNombre(),
                             cineRequest.getUbicacion(),
                             cineRequest.getFechaCreacion()
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
        * @throws UserDataInvalidException
        */
       public List<CineResponse> obtenerCines(int inicio) throws DataBaseException, UserDataInvalidException {
              List<CineResponse> cinesDTO = new ArrayList<>();
              try {
                     int inicio1 = inicio;
              } catch (NumberFormatException e) {
                     throw new UserDataInvalidException();
              }
              CinesDB cinesDB = new CinesDB();
              List<Cine> cines = cinesDB.obtenerCinesPaginacion(inicio, inicio + RangoBusquedaElemento.CINES.getRango());
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

       /**
        *
        * @param palabra
        * @return
        * @throws DataBaseException
        * @throws UserDataInvalidException
        */
       public List<CineResponse> obtenerCinesPorNombreOCodigo(String palabra) throws DataBaseException, UserDataInvalidException {
              List<CineResponse> cinesDTO = new ArrayList<>();
              CinesDB cinesDB = new CinesDB();

              List<Cine> cines = cinesDB.obtenerCinePorCodigoONombre(palabra);
              cinesDTO = cargarDTOs(cines, cinesDTO);
              
              return cinesDTO;
       }

       /**
        *
        * @param entidadRequest
        * @return
        * @throws DataBaseException
        * @throws EntityNotFoundException
        * @throws UserDataInvalidException
        */
       @Override
       public CineResponse actualizar(EntidadRequest entidadRequest) throws DataBaseException,
               EntityNotFoundException, UserDataInvalidException {
              CineRequest cineRequest = (CineRequest) entidadRequest;
              CinesDB cinesDB = new CinesDB();

              Optional<Cine> cineActual = cinesDB.obtenerCinePorCodigo(cineRequest.getCodigo());
              if (cineActual.isEmpty()) {
                     throw new EntityNotFoundException("Cine no encontrado en la base de datos");
              }
              Cine cineUpdate = setearCine(cineRequest);
              cinesDB.updateCine(cineUpdate);
              return new CineResponse(cineRequest);
       }

       private Cine setearCine(CineRequest cineRequest) throws UserDataInvalidException {
              Cine cineUpdate = new Cine(
                      cineRequest.getCodigo(),
                      cineRequest.getNombre(),
                      cineRequest.getUbicacion(),
                      cineRequest.getFechaCreacion()
              );
              if (!cineUpdate.esValidoEnCampos()) {
                     throw new UserDataInvalidException("Error en los datos enviados");
              }
              return cineUpdate;
       }
}
